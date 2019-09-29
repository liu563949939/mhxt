package nist.module.mhxt.entity.po;

import nist.module.mhxt.entity.ModuleEntity;

import javax.validation.constraints.Null;
import java.util.List;

public class UtilEntity {
    private String checkArr;
    private List<ModuleEntity> children;
    public String getCheckArr() {
        return checkArr;
    }

    public void setCheckArr(String checkArr) {
        this.checkArr = checkArr;
    }

    public List<ModuleEntity> getChildren() {
        return children;
    }

    public void setChildren(List<ModuleEntity> children) {
        this.children = children;
    }
}
