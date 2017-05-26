package cn.gotoil.znl.web.controller;

import cn.gotoil.znl.service.MessageProducerService;
import cn.gotoil.znl.service.MqRabbitServer;
import cn.gotoil.znl.web.message.request.union.WxPayRequest;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by think on 2017/5/22.
 */
@RestController
@RequestMapping("/mq")
public class MqRabbitController  {

    @Autowired
    private MessageProducerService messageProducerService;
//    private MqRabbitServiceImpl mqRabbitService;
/*
    @Autowired
    private Send send;*/


    @RequestMapping("/mq/send/{gameType:^.*$}/{appid:^\\d$}")
    @ResponseBody
    public Object send(@PathVariable String gameType,@PathVariable String appid){

        WxPayRequest wxPayRequest = new WxPayRequest();
        messageProducerService.delayMsg(JSONObject.parseObject(JSONObject.toJSONString(wxPayRequest)),0);
 /*int x=0;

try {


        ExecutorService pool = Executors.newFixedThreadPool(5);
        List<Future> list = new ArrayList<Future>();
        for(int i=0;i<5;i++){
            Callable callable = new Callable() {
                int x=0;

                @Override
                public Object call() throws Exception {
                    for(int i=0;i<10000000;i++){
                        x++;
                        WxPayRequest wxPayRequest = new WxPayRequest() ;
                        mqRabbitServer.delayMsg(JSONObject.parseObject(JSONObject.toJSONString(wxPayRequest)),1);
                    }
                    return x;
                }
            };
           Future f= pool.submit(callable);
            list.add(f);
        }
        pool.shutdown();

        for (Future f : list) {
            x=x+Integer.parseInt((String)f.get());
            // 从Future对象上获取任务的返回值，并输出到控制台

        }  System.out.println("--------------------程序执行完了，一共发了"+x);
}catch (Exception e){
    e.printStackTrace();
}
*/

        return null;
    }



}
