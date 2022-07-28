package green.mscustomer.model.view;

import lombok.Data;

@Data
public class CustomerView {

    private Long id;
    private String fullname;
    private String email;
    private String phone;
    private String address;
}
