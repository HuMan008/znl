package cn.gotoil.znl.web.message.request;




import cn.gotoil.znl.config.SysConsts;

import javax.validation.constraints.NotNull;

public class PageListRequest {

    @NotNull
    private Integer pageNum = 1;   //当前页码 默认从第一页
    @NotNull
    private Integer pageSize = SysConsts.PageConfig.pageSize;//  每页显示数量


    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }


}
