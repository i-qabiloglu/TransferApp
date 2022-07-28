package green.mscustomer.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    @NotBlank(message = "Firstname must not be null and must contain at least one non-whitespace character")
    @Size(min = 3, max = 100, message = "Firstname size must be min:3 and max:100 character")
    private String firstname;

    private String lastname;

    @Email
    private String email;

    @NotBlank(message = "Phone number must not be null and must contain at least one non-whitespace character")
    @Pattern(regexp = "$[0-9]{2}-[0-9]{3}-[0-9]{2}-[0-9]{2}")
    private String phone;
    private String address;
}
