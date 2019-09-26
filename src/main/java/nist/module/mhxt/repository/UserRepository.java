package nist.module.mhxt.repository;

import nist.module.mhxt.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends CrudRepository<UserEntity, String> {
    //结果集映射成实体
    @Query(value = "select * from s_user where username=:#{#entity.username} and password =:#{#entity.password}", nativeQuery = true)
    List<UserEntity> findByName(@Param("entity") UserEntity userEntity);
}
