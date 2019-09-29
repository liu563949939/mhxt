package nist.module.mhxt.entity.po;

import nist.module.mhxt.entity.ModuleEntity;

import java.util.List;


public class ModulePoEntity {
    private List<ModuleEntity> children;

    public List<ModuleEntity> getChildren() {
        return children;
    }

    public void setChildren(List<ModuleEntity> children) {
        this.children = children;
    }
}
