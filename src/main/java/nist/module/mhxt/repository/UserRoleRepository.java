package nist.module.mhxt.repository;

import nist.module.mhxt.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface UserRoleRepository extends CrudRepository<UserRoleEntity,String> {
    @Modifying
    @Transactional
    @Query(value = "delete from s_user_role where RoleId = :roleId",nativeQuery = true)
    void del(@Param("roleId") String roleId);
}
