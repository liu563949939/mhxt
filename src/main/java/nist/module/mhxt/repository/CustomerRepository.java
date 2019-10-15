package nist.module.mhxt.repository;

import nist.module.mhxt.entity.CustomerEntity;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<CustomerEntity, String> {
}
