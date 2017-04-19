package cn.gotoil.znl.web.message.response;

import java.util.List;

/**
 * Created by Suyj on 2016/12/6.
 */
public class PageListResponse {


    private boolean hasNextPage;
    private List list;

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
