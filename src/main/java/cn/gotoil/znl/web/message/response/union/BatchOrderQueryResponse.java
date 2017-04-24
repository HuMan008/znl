package cn.gotoil.znl.web.message.response.union;

import lombok.*;

import java.util.List;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/4/21.15:06
 */
@Getter
@Setter
@ToString
public class BatchOrderQueryResponse {
    private String merchantNo;
    private int currentPageRows;
    private int pages;
    private boolean hasNext;
    private List<OrderMsg> list ;
    private String sign;



    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public class OrderMsg {
        private String merchantNo;
        private String tlOrderNo;
        private String orderNo;
        private String orderDateTime;
        private long orderAmount;
        private String payDateTime;
        private long orderRealPayAmount;
        private String ext1;
        private String ext2;
        private String result;

    }



}

