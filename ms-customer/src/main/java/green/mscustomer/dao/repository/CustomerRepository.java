package green.mscustomer.dao.repository;

import green.mscustomer.dao.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    public Optional<CustomerEntity> findByIdAndIsDeletedFalse(Long id);

    public CustomerEntity findByEmail(String email);
}
