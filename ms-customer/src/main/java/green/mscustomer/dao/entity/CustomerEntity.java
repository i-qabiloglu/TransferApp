package green.mscustomer.dao.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "customers")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String address;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CustomerEntity that = (CustomerEntity) o;
        return getId().longValue() == that.getId()
                                          .longValue() && getEmail().equalsIgnoreCase(that.getEmail());
    }


    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail());
    }
}
