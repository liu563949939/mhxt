package nist.module.mhxt.entity.po;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "v_roleMudulePo")
public class RoleModulePoEntity {
    @Id
    private String jlbh;
    private String roleId;
    private String moduleId;
    private String createTime;
    private String name;
    private String url;

    public String getJlbh() {
        return jlbh;
    }

    public void setJlbh(String jlbh) {
        this.jlbh = jlbh;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModuleId() { return moduleId; }

    public void setModuleId(String moduleId) { this.moduleId = moduleId; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
