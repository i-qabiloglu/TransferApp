package green.mscustomer.service;

import green.mscustomer.dao.entity.CustomerEntity;
import green.mscustomer.dao.repository.CustomerRepository;
import green.mscustomer.mapper.CustomerMapper;
import green.mscustomer.model.dto.CustomerDto;
import green.mscustomer.model.view.CustomerView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository repository;

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
        return repository.findAllByIsDeletedFalse()
                         .orElseThrow(() -> {throw new RuntimeException("There are not any customers!");})
                         .stream()
                         .map(CustomerMapper.INSTANCE::mapEntityToView)
                         .collect(Collectors.toList());
    }

    public CustomerView updateCustomer(Long id, CustomerDto dto) {

        log.info("CustomerService.updateCustomer.start id: {}", id);
        var customer = fetchUserIfExist(id);

        if (!customer.getFirstname()
                     .equals(dto.getFirstname()) && dto.getFirstname() != null) {
            customer.setFirstname(dto.getFirstname());
        }

        if (!customer.getLastname()
                     .equals(dto.getLastname())) {
            customer.setLastname(dto.getLastname());
        }

        if (!customer.getEmail()
                     .equals(dto.getEmail()) && dto.getEmail() != null) {
            customer.setEmail(dto.getEmail());
        }

        if (!customer.getPhone()
                     .equals(dto.getPhone()) && dto.getPhone() != null) {
            customer.setPhone(dto.getPhone());
        }

        if (!customer.getAddress()
                     .equals(dto.getAddress())) {
            customer.setAddress(dto.getAddress());
        }

        log.info("CustomerService.updateCustomer.end id: {}", id);
        return CustomerMapper.INSTANCE.mapEntityToView(repository.save(customer));

    }

    public void deleteCustomer(Long id) {
        var customer = fetchUserIfExist(id);
        customer.setIsDeleted(true);
        repository.save(customer);
    }

    private CustomerEntity fetchUserIfExist(Long id) {
        return repository.findByIdAndIsDeletedFalse(id)
                         .orElseThrow(() -> {
                             log.error("CustomerService.fetchUserIfExist.error id:{}", id);
                             throw new RuntimeException("Customer not found with id :" + id);
                         });
    }
}
