package cn.csg.common.vo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Author: tide
 * @Description: 主站Redis-资产临时表bean对象
 * @Date: Created in 16:52 2019/4/4
 * @Modified By:
 */
public class DbDeviceTmpBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 资产临时lid
	 */
	private String lid;
	
	/**
	 * 主机名
	 */
	private String hostname;
	/**
	 * 设备管理IP
	 */
	private String ip;
	
	/**
	 * 设备名
	 */
	private String devicename;
	/**
	 * 调度分区
	 */
	private String partition;
	
	/**
	 * 设备类型
	 */
	private String devicecode;
	
	/**
	 * 物理位置
	 */
	private String position;
	
	/**
	 * 厂商信息
	 */
	private String factory;
	
	/**
	 * 厂商信息ID
	 */
	private String factoryid;
	
	/**
	 * 型号
	 */
	private String model;
	
	/**
	 * 软件版本
	 */
	private String softversion;
	
	/**
	 * 所属业务系统
	 */
	private String businesssystem;
	
	/**
	 * 所属业务系统ID
	 */
	private String businesssystemid;
	
	/**
	 * 设备用途
	 */
	private String deviceuses;
	
	/**
	 * 负责人
	 */
	private String principle;
	
	/**
	 * 联系电话
	 */
	private String telephone;
	
	/**
	 * 状态
	 */
	private String status;
	
	/**
	 * 数据来源
	 */
	private String resource;
	
	/**
	 * 是否检修
	 */
	private String isservice;
	
	/**
	 * 是否关联
	 */
	private String isref;
	
	
	/**
	 * 内部名称
	 */
	private String innername;
	
	/**
	 * 电压等级
	 */
	private String voltagecalss;
	
	/**
	 * 地域组织机构id
	 */
	private String corpid;
	
	/**
	 * 站点类型
	 */
	private String sitetype;
	
	/**
	 * 添加时间
	 */
	private Timestamp addtime;
	
	/**
	 * 修改时间
	 */
	private Timestamp updatetime;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 采集机id
	 */
	private String agent_id;
	
	/**
	 * 采集机ip
	 */
	private String agent_ip;
	/**
	 * 1、主属；2、副属类型
	 */
	private int stationType;
	
	/**
	 * 操作状态  0 没操作 1：已注册 2：已删除
	 */
	private int operatestatus;
	
	/**
	 * 在离线
	 */
	private String  state;
	
	/**
	 * 操作系统版本
	 */
	private String systemVersion;
	
	/**
	 * 归属专业
	 */
	private String belongedMajor;
	
	/**
     * nmap扫描系统名
     */
    private String nmapSystem;
    
    private String deviceRegisterGuid;

	@Override
	public String toString() {
		return "DbDeviceTmpBean{" +
				"lid='" + lid + '\'' +
				", hostname='" + hostname + '\'' +
				", ip='" + ip + '\'' +
				", devicename='" + devicename + '\'' +
				", partition='" + partition + '\'' +
				", devicecode='" + devicecode + '\'' +
				", position='" + position + '\'' +
				", factory='" + factory + '\'' +
				", factoryid='" + factoryid + '\'' +
				", model='" + model + '\'' +
				", softversion='" + softversion + '\'' +
				", businesssystem='" + businesssystem + '\'' +
				", businesssystemid='" + businesssystemid + '\'' +
				", deviceuses='" + deviceuses + '\'' +
				", principle='" + principle + '\'' +
				", telephone='" + telephone + '\'' +
				", status='" + status + '\'' +
				", resource='" + resource + '\'' +
				", isservice='" + isservice + '\'' +
				", isref='" + isref + '\'' +
				", innername='" + innername + '\'' +
				", voltagecalss='" + voltagecalss + '\'' +
				", corpid='" + corpid + '\'' +
				", sitetype='" + sitetype + '\'' +
				", addtime=" + addtime +
				", updatetime=" + updatetime +
				", remark='" + remark + '\'' +
				", agent_id='" + agent_id + '\'' +
				", agent_ip='" + agent_ip + '\'' +
				", stationType=" + stationType +
				", operatestatus=" + operatestatus +
				", state='" + state + '\'' +
				", systemVersion='" + systemVersion + '\'' +
				", belongedMajor='" + belongedMajor + '\'' +
				", nmapSystem='" + nmapSystem + '\'' +
				", deviceRegisterGuid='" + deviceRegisterGuid + '\'' +
				'}';
	}

	public String getDeviceRegisterGuid() {
		return deviceRegisterGuid;
	}

	public void setDeviceRegisterGuid(String deviceRegisterGuid) {
		this.deviceRegisterGuid = deviceRegisterGuid;
	}

	public String getSystemVersion() {
		return systemVersion;
	}

	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
	}

	public String getBelongedMajor() {
		return belongedMajor;
	}

	public void setBelongedMajor(String belongedMajor) {
		this.belongedMajor = belongedMajor;
	}

	public String getNmapSystem() {
		return nmapSystem;
	}

	public void setNmapSystem(String nmapSystem) {
		this.nmapSystem = nmapSystem;
	}

	public String getLid() {
		return lid;
	}

	public void setLid(String lid) {
		this.lid = lid;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getDevicename() {
		return devicename;
	}

	public void setDevicename(String devicename) {
		this.devicename = devicename;
	}

	public String getPartition() {
		return partition;
	}

	public void setPartition(String partition) {
		this.partition = partition;
	}

	public String getDevicecode() {
		return devicecode;
	}

	public void setDevicecode(String devicecode) {
		this.devicecode = devicecode;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getFactory() {
		return factory;
	}

	public void setFactory(String factory) {
		this.factory = factory;
	}

	public String getFactoryid() {
		return factoryid;
	}

	public void setFactoryid(String factoryid) {
		this.factoryid = factoryid;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSoftversion() {
		return softversion;
	}

	public void setSoftversion(String softversion) {
		this.softversion = softversion;
	}

	public String getBusinesssystem() {
		return businesssystem;
	}

	public void setBusinesssystem(String businesssystem) {
		this.businesssystem = businesssystem;
	}

	public String getBusinesssystemid() {
		return businesssystemid;
	}

	public void setBusinesssystemid(String businesssystemid) {
		this.businesssystemid = businesssystemid;
	}

	public String getDeviceuses() {
		return deviceuses;
	}

	public void setDeviceuses(String deviceuses) {
		this.deviceuses = deviceuses;
	}

	public String getPrinciple() {
		return principle;
	}

	public void setPrinciple(String principle) {
		this.principle = principle;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getIsservice() {
		return isservice;
	}

	public void setIsservice(String isservice) {
		this.isservice = isservice;
	}

	public String getIsref() {
		return isref;
	}

	public void setIsref(String isref) {
		this.isref = isref;
	}

	public String getInnername() {
		return innername;
	}

	public void setInnername(String innername) {
		this.innername = innername;
	}

	public String getVoltagecalss() {
		return voltagecalss;
	}

	public void setVoltagecalss(String voltagecalss) {
		this.voltagecalss = voltagecalss;
	}



	public String getCorpid() {
		return corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	public String getSitetype() {
		return sitetype;
	}

	public void setSitetype(String sitetype) {
		this.sitetype = sitetype;
	}



	public Timestamp getAddtime() {
		return addtime;
	}

	public void setAddtime(Timestamp addtime) {
		this.addtime = addtime;
	}

	public Timestamp getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAgent_id() {
		return agent_id;
	}

	public void setAgent_id(String agent_id) {
		this.agent_id = agent_id;
	}

	public String getAgent_ip() {
		return agent_ip;
	}

	public void setAgent_ip(String agent_ip) {
		this.agent_ip = agent_ip;
	}

	public int getOperatestatus() {
		return operatestatus;
	}

	public void setOperatestatus(int operatestatus) {
		this.operatestatus = operatestatus;
	}

	public int getStationType() {
		return stationType;
	}

	public void setStationType(int stationType) {
		this.stationType = stationType;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	
	
}
