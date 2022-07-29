package green.mscustomer.service;

import green.mscustomer.dao.entity.CustomerEntity;
import green.mscustomer.dao.repository.CustomerRepository;
import green.mscustomer.mapper.CustomerMapper;
import green.mscustomer.model.dto.CustomerDto;
import green.mscustomer.model.exception.NotFoundException;
import green.mscustomer.model.view.CustomerView;
import green.mscustomer.model.view.EmailView;
import green.mscustomer.queue.sender.EmailSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static green.mscustomer.model.constant.ExceptionConstants.CUSTOMER_NOT_FOUND_CODE;
import static green.mscustomer.model.constant.ExceptionConstants.CUSTOMER_NOT_FOUND_MESSAGE;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository repository;
    private final EmailSender sender;

    public CustomerView getCustomer(Long id) {
        return CustomerMapper.INSTANCE.mapEntityToView(fetchUserIfExist(id));

    }

    public CustomerView createCustomer(CustomerDto dto) {

        log.info("CustomerService.createCustomer.start");
        if (repository.findByEmail(dto.getEmail()) != null) {
            log.error("CustomerService.createCustomer.error customer email :{}", dto.getEmail());
            throw new RuntimeException("this customer already have");
        }
        log.info("CustomerService.createCustomer.end");
        return CustomerMapper.INSTANCE.mapEntityToView(repository.save(CustomerMapper.INSTANCE.mapDtoToEntity(dto)));

    }

    public List<CustomerView> getcustomers() {
        if (repository.findAllByIsDeletedFalse().isEmpty()) {
            throw new NotFoundException("CUSTOMERS NOT FOUND", "There are not any customers");
        }
        return repository.findAllByIsDeletedFalse().stream().map(CustomerMapper.INSTANCE::mapEntityToView)
                         .collect(Collectors.toList());
    }

    public CustomerView updateCustomer(Long id, CustomerDto dto) {

        log.info("CustomerService.updateCustomer.start id: {}", id);
        var customer = fetchUserIfExist(id);

        if (!customer.getFirstname().equals(dto.getFirstname()) && dto.getFirstname() != null) {
            customer.setFirstname(dto.getFirstname());
        }

        if (!customer.getLastname().equals(dto.getLastname())) {
            customer.setLastname(dto.getLastname());
        }

        if (!customer.getEmail().equals(dto.getEmail()) && dto.getEmail() != null) {
            customer.setEmail(dto.getEmail());
        }

        if (!customer.getPhone().equals(dto.getPhone()) && dto.getPhone() != null) {
            customer.setPhone(dto.getPhone());
        }

        if (!customer.getAddress().equals(dto.getAddress())) {
            customer.setAddress(dto.getAddress());
        }

        log.info("CustomerService.updateCustomer.end id: {}", id);
        return CustomerMapper.INSTANCE.mapEntityToView(repository.save(customer));

    }

    public void deleteCustomer(Long id) {
        var customer = fetchUserIfExist(id);
        customer.setIsDeleted(true);
        repository.save(customer);
        EmailView email = new EmailView();
        email.setId(id);
        email.setMessage(String.format("Customer %s %s is deleted", customer.getFirstname(), customer.getLastname()));
        sender.sendNotification(email);

    }

    private CustomerEntity fetchUserIfExist(Long id) {
        return repository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> {
            log.error("CustomerService.fetchUserIfExist.error id:{}", id);
            throw new NotFoundException(CUSTOMER_NOT_FOUND_CODE, String.format(CUSTOMER_NOT_FOUND_MESSAGE, id));
        });
    }
}
