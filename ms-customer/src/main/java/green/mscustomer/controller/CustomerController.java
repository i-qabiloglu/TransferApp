package green.mscustomer.controller;

import green.mscustomer.model.dto.CustomerDto;
import green.mscustomer.model.view.CustomerView;
import green.mscustomer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @GetMapping("/{id}")
    public CustomerView getCustomer(@PathVariable Long id) {
        return service.getCustomer(id);
    }

    @GetMapping()
    public List<CustomerView> getCustomers() {
        return service.getcustomers();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerView createCustomer(@Valid @RequestBody CustomerDto dto) {
        return service.createCustomer(dto);
    }

    @PutMapping("/{id}")
    public CustomerView updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerDto dto) {
        return service.updateCustomer(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable Long id) {
        service.deleteCustomer(id);
    }
}
