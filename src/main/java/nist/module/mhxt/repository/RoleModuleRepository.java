package nist.module.mhxt.repository;

import nist.module.mhxt.entity.RoleModuleEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface RoleModuleRepository extends CrudRepository<RoleModuleEntity,String> {
    @Modifying
    @Transactional
    @Query(value = "delete from s_role_module where roleId = :roleId",nativeQuery = true)
    void del(@Param("roleId") String roleId);
}
