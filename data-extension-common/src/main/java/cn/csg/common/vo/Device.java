package cn.csg.common.vo;


import cn.csg.core.BaseVo;
import org.springframework.data.elasticsearch.annotations.Document;


@Document(indexName="device",type="default")
public class Device extends BaseVo {

    private String agentId;
    private String flag;
    private String addTime;
    private String ipFetch;
    private String belongEdmajor;
    private String remark;
    private String systemVersion;
    private String zdlx;
    private String mac;
    private String agentIp;
    private String principal;
    private String areaorgId;
    private String sysBelong;
    private String propertyType;
    private String model;
    private String state;
    private String stationUID;
    private String phyAddress;
    private String siteType;
    private String area;
    private String owner;
    private String factory;
    private String showName;
    private String corpId;
    private String test;
    private String ip;
    private String deviceCode;
    private String version;
    private String areapm;
    private String voltageLevel;
    private String phone;
    private String name;
    private String guid;
    private String user;
    private String bussysId;

    public Device() {
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getIpFetch() {
        return ipFetch;
    }

    public void setIpFetch(String ipFetch) {
        this.ipFetch = ipFetch;
    }

    public String getBelongEdmajor() {
        return belongEdmajor;
    }

    public void setBelongEdmajor(String belongEdmajor) {
        this.belongEdmajor = belongEdmajor;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public String getZdlx() {
        return zdlx;
    }

    public void setZdlx(String zdlx) {
        this.zdlx = zdlx;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getAgentIp() {
        return agentIp;
    }

    public void setAgentIp(String agentIp) {
        this.agentIp = agentIp;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getAreaorgId() {
        return areaorgId;
    }

    public void setAreaorgId(String areaorgId) {
        this.areaorgId = areaorgId;
    }

    public String getSysBelong() {
        return sysBelong;
    }

    public void setSysBelong(String sysBelong) {
        this.sysBelong = sysBelong;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStationUID() {
        return stationUID;
    }

    public void setStationUID(String stationUID) {
        this.stationUID = stationUID;
    }

    public String getPhyAddress() {
        return phyAddress;
    }

    public void setPhyAddress(String phyAddress) {
        this.phyAddress = phyAddress;
    }

    public String getSiteType() {
        return siteType;
    }

    public void setSiteType(String siteType) {
        this.siteType = siteType;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAreapm() {
        return areapm;
    }

    public void setAreapm(String areapm) {
        this.areapm = areapm;
    }

    public String getVoltageLevel() {
        return voltageLevel;
    }

    public void setVoltageLevel(String voltageLevel) {
        this.voltageLevel = voltageLevel;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getBussysId() {
        return bussysId;
    }

    public void setBussysId(String bussysId) {
        this.bussysId = bussysId;
    }
}
