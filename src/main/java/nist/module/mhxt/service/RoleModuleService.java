package nist.module.mhxt.service;

import nist.module.mhxt.entity.RoleModuleEntity;
import nist.module.mhxt.repository.RoleModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleModuleService {
    @Autowired
    private RoleModuleRepository roleModuleRepository;

    public RoleModuleEntity save(RoleModuleEntity roleModuleEntity){
        return roleModuleRepository.save(roleModuleEntity);
    }

    public void del(String roleId){
        roleModuleRepository.del(roleId);
    }
}
