# 通联网关支付订单及支付
```
    通联下订单并且去支付
```

## 1.注册

### 请求方法
`post`
### 请求路径
`/pay/ordersubmit`


### 请求说明
字段 | 含义 |  可空 | 类型 |  备注 |
| --- | --- | --- |--- |--- |
| unionUserId | 通联用户ID | 不为空|string | 通联注册过的用户|
|orderAmount  |支付金额| 不为空 | int| 正整数 |
|APPKEY  |调用接口的APPKEY| 可空 | String| 长度不超过8位，字母数字开头 |
orderNo| 您APP的订单号 | 可空 | String | 长度不超过50位；如果orderNo为空，我们将根据APPKEY生成一个|
orderExpireDatetime | 订单有效时间，单位分钟|可空| int| 默认十分钟，十分钟后失效
productName |商品名称|可空| String |长度限制在256个字符
pan |付款人支付号|可空|String |长度19个字符以内
productId |商品ID| 可空|String|长度20个字符以内
payerName |支付人姓名| 可空| String |长度32个字符以内
payerEmail|支付人邮箱|  可空| String |  长度 50个字符以内
payerTelephone |支付人电话号码  |可空| String |  长度16个字符以内
payerIDCard   |支付人身份证号码|  可空| String |   长度22个字符以内



### 响应
 通联支付页面