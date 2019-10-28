package nist.module.mhxt.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_upload")
public class UploadEntity extends PageEntity {
    /** 附件记录编号 */
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    @Id
    private String jlbh ;
    private String glbh ; /** 附件关联编号 */
    private String name; //附件名称
    private String path ; /** 附件路径 */
    private String memo ; /** 资料备注 */
    private String createTime ; /** 资料登记时间 */

    public String getJlbh() {
        return jlbh;
    }

    public void setJlbh(String jlbh) {
        this.jlbh = jlbh;
    }

    public String getGlbh() {
        return glbh;
    }

    public void setGlbh(String glbh) {
        this.glbh = glbh;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
