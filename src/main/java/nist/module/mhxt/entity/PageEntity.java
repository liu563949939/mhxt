package nist.module.mhxt.entity;

import nist.module.mhxt.entity.po.UtilEntity;

public class PageEntity extends UtilEntity {
    private Integer page; //页数
    private Integer limit; //每页数量

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
