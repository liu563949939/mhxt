package nist.module.mhxt.repository;

import nist.module.mhxt.entity.UploadEntity;
import org.springframework.data.repository.CrudRepository;

public interface UploadRepository extends CrudRepository<UploadEntity, String> {
}
