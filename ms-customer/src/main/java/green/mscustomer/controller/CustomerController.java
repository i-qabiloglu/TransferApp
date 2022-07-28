package green.mscustomer.controller;

import green.mscustomer.model.dto.CustomerDto;
import green.mscustomer.model.view.CustomerView;
import green.mscustomer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @GetMapping("/{id}")
    public CustomerView getCustomer(@PathVariable Long id) {
        return service.getCustomer(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createCustomer(@Valid @RequestBody CustomerDto dto) {
        service.createCustomer(dto);
    }
}
