# Codis 服务 RESTFul API

## 查询接口

- Http Method:Post
- Url:/codis/find/{collect}
- Request Params:
  - collect:
    - type: enum
    - values:[corporation,device,devicetmp,dcdDeviceIpbd,evilIp,evilDns]
  - key:
    - type: String
 
## 更新或新增接口

- Http Method:Post
- Url:/codis/update/{collect}
- Request Params:
  - collect:
    - type: enum
    - values:[corporation,device,devicetmp,dcdDeviceIpbd,evilIp,evilDns]
  - list:
    - type: Json String | Json Array

> list 可以传入序列话为json 或者 json array 的字符串,
当传入 json 时只会更新或新增一个key, 传入json array 可以更新或新增多个key. 

## 删除接口

 - Http Method:Post
 - Url:/codis/delete/{collect}
 - Request Params:
   - collect:
     - type: enum
     - values:[corporation,device,devicetmp,dcdDeviceIpbd,evilIp,evilDns]
   - key:
     - type: String
     
## collect 的取值解释
- corporation: 组织机构
- device: 已知资产
- devicetmp: 未知资产
- dcdDeviceIpbd: 装置IP
- evilIp: 恶意IP
- evilDns: 恶意域名