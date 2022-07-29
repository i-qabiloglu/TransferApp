package green.mscustomer.model.view;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class EmailView {

    private String message;
    private Long id;

}
