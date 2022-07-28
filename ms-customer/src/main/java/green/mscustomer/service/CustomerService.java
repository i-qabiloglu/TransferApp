package green.mscustomer.service;

import green.mscustomer.dao.entity.CustomerEntity;
import green.mscustomer.dao.repository.CustomerRepository;
import green.mscustomer.mapper.CustomerMapper;
import green.mscustomer.model.dto.CustomerDto;
import green.mscustomer.model.view.CustomerView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerView getCustomer(Long id) {
        return CustomerMapper.INSTANCE.mapEntityToView(fetchUserIfExist(id));

    }

    private CustomerEntity fetchUserIfExist(Long id) {
        return repository.findByIdAndIsDeletedFalse(id)
                         .orElseThrow(() -> {
                             log.error("CustomerService.fetchUserIfExist.error id:{}", id);
                             throw new RuntimeException("Customer not found with id :" + id);
                         });
    }

    public void createCustomer(CustomerDto dto) {
        log.info("CustomerService.createCustomer.start");
        if (repository.findByEmail(dto.getEmail()) != null) {
            log.error("CustomerService.createCustomer.error customer email :{}", dto.getEmail());
            throw new RuntimeException("this customer already have");
        }
        repository.save(CustomerMapper.INSTANCE.mapDtoToEntity(dto));
        log.info("CustomerService.createCustomer.end");
    }
}
