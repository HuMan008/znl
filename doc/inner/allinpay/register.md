# 通联用户注册
```
    通联网关下订单、支付、订单查询都需要是通联注册用户
```

## 1.SDK注册


### 请求方法
`get`
### 请求路径
`/pay/unionregister/{puid:^[0-9a-z]{1,32}$}`

### 是否需要请求验证
   `需要`

### 请求路径说明
`XXX表示由第三方传过来的唯一ID；当不传递此参数时，`
`XXX长度限制为32个字符,有字母的话必须是小写`
### 响应

```
{
    "userId": "170418804556088",   ;//生成的通联用户ID
    "resultCode": "0000",   //返回的代码
    "partnerUserId": "d487ffe024c4ad725c3a12db6ebc3984"
}
```
### 响应描述
 字段 | 含义 | 类型 |  备注 |
| --- | --- | --- |--- |
| userId | 通联用户ID | string | 根据partnerUserId生成或取得的通联用户id|
| resultCode | 返回的代码 | string | 0000：成功；0006：已注册； |
| partnerUserId | 生成依据 | string |  访问路径中puId值；|

## 2.网关注册



