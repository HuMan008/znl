package cn.gotoil.znl.web.controller.api.v1;

import cn.gotoil.bill.web.message.BillApiResponse;
import cn.gotoil.znl.common.tools.ObjectHelper;
import cn.gotoil.znl.common.tools.date.DateUtils;
import cn.gotoil.znl.service.UnionService;
import cn.gotoil.znl.web.controller.BaseController;
import cn.gotoil.znl.web.message.request.union.AppPayRequest;
import cn.gotoil.znl.web.message.request.union.BatchOrderQueryRequest;
import cn.gotoil.znl.web.message.request.union.OrderQueryRequest;
import cn.gotoil.znl.web.message.response.union.OrderQueryResponse;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.Map;

/**
 * Created by Suyj <suyajiang@gotoil.cn> on 2017/4/7.10:48
 */
@SuppressWarnings("all")
@RestController(value = "apiUnionController")
@Api(value = "通联支付API", description = "通联支付API")
public class UnionController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(UnionController.class);

    @Autowired
    private UnionService unionService;

    /**
     * 通联用户注册
     * @param puid
     * @return
     */
    @RequestMapping(value = "unionregister/{puid:^[0-9a-z]{1,32}$}", method = RequestMethod.GET)
    public Object unionRegister4SdkAction(@PathVariable String puid) {

        try {
            return  unionService.unionRegister(puid)  ;
        } catch (Exception e){
            return new BillApiResponse(e);
        }
    }





    /**
     * 订单查询
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "orderquery/{orderDate:^\\d{14}$}/{orderNo:^.*$}", method = RequestMethod.GET)
    public Object orderQueryAction(HttpServletRequest request, HttpServletResponse response, Model model,
                                   @PathVariable String orderDate,
                                   @PathVariable String orderNo
    ) throws Exception {
        OrderQueryRequest orderQueryRequest = new OrderQueryRequest();
//        PayResultResponse{merchantId='008500189990304', version='v1.0', language='1', signType='0', issuerId='', paymentOrderId='201704111646298209', orderNo='order_001', orderDatetime='20170411164619', orderAmount=1, payDatetime='20170411164636', payAmount=1, ext1='<USER>170410792118546</USER>', ext2='', payResult='1', returnDatetime='20170411164642'}
        orderQueryRequest.setOrderDatetime(orderDate);
        orderQueryRequest.setOrderNo(orderNo);

        orderQueryRequest.doSign();

        String reStr = unionService.orderQuery(orderQueryRequest);

        Map map2 = ObjectHelper.stringToMap(reStr);
        OrderQueryResponse orderQueryResponse = (OrderQueryResponse) ObjectHelper.mapToObject(map2, OrderQueryResponse.class);
        return orderQueryResponse;
    }

    /**
     * 订单查询（批量）
     *
     * @param request
     * @param response
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "batchorderquery/{pageNo:^\\d+$}", method = RequestMethod.GET)
    @ResponseBody
    public Object batchOrderQueryAction(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable int pageNo) throws Exception {
        BatchOrderQueryRequest batchOrderQueryRequest = new BatchOrderQueryRequest();
        batchOrderQueryRequest.setPageNo(pageNo);
        batchOrderQueryRequest.setBeginDateTime(DateUtils.simpleDateFormatter().format(new Date()).replace("-", "") + "00");
        batchOrderQueryRequest.setEndDateTime(DateUtils.simpleDateFormatter().format(new Date()).replace("-", "") + "23");
        batchOrderQueryRequest.doSign();
        String result = unionService.batchOrderQuery(batchOrderQueryRequest);
        String x = new String(Base64.decode(result), "UTF-8");
        System.out.println(x);
        response.setCharacterEncoding("UTF-8");
        Object o = unionService.parseBatchOrderStr(x);
        return o;
    }

    /**
     * SDK支付需要的串
     * @param appPayRequest
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/sdk/paydata")
    public Object payDataAction(@Valid AppPayRequest appPayRequest,HttpServletRequest request,HttpServletResponse response )throws Exception{
        if(StringUtils.isEmpty(appPayRequest.getOrderNo())){
            appPayRequest.setOrderNo(orderHelper.createOrder(request));
        }
        if(StringUtils.isEmpty(appPayRequest.getProductName())){
            appPayRequest.setProductName(sysConfig.getDefaultProductName());
        }
        appPayRequest.doSign();
        Map<String,String> map = ObjectHelper.introspectStringValueMapValueNotEmpty(appPayRequest) ;
        return map;
    }





}
