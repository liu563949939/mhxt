package nist.module.mhxt.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_customer")
public class CustomerEntity extends PageEntity {
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    @Id
    /** 房东记录编号 */
    private String jlbh ;
    /** 房东姓名 */
    private String name ;
    /** 房东身份证号 */
    private String card ;
    /** 房东电话 */
    private String phone ;
    /** 房屋地址 */
    private String address ;
    /** 房屋面积 */
    private Integer area ;
    /** 房屋租售状态 */
    private String saleStatus ;
    /** 房屋价格 */
    private Integer price ;
    /** 房屋厅室 */
    private Integer room ;
    /** 房屋装修状态 */
    private String decorateStatus ;
    /** 房屋楼层 */
    private Integer floor ;
    /** 房屋状态 */
    private String status ;
    /** 房东备注 */
    private String memo ;
    /** 房东登记时间 */
    private String createtime ;
    /** 房东修改时间 */
    private String modifytime ;
    /** 登记人员 */
    private String createuser ;

    public String getJlbh() {
        return jlbh;
    }

    public void setJlbh(String jlbh) {
        this.jlbh = jlbh;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getArea() {
        return area;
    }

    public String getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(String saleStatus) {
        this.saleStatus = saleStatus;
    }

    public String getDecorateStatus() {
        return decorateStatus;
    }

    public void setDecorateStatus(String decorateStatus) {
        this.decorateStatus = decorateStatus;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getRoom() {
        return room;
    }

    public void setRoom(Integer room) {
        this.room = room;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getModifytime() {
        return modifytime;
    }

    public void setModifytime(String modifytime) {
        this.modifytime = modifytime;
    }

    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser;
    }
}
