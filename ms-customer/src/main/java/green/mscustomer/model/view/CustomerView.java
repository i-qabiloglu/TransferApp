package green.mscustomer.model.view;

import lombok.Data;

@Data
public class CustomerView {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String address;
}
