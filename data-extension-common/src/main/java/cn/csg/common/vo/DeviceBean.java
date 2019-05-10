package cn.csg.common.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @Author: tide
 * @Description: 主站Redis-设备信息表bean对象
 * @Date: Created in 16:52 2019/4/4
 * @Modified By:
 */
public class DeviceBean implements Serializable{
	private static final long serialVersionUID = 1390741768658491557L;
	/**
	 * 设备ID
	 */
	private int id;
	/**
	 * 资产唯一标识 sctang
	 */
	private String guid;	
	/**
	 * 设备IP
	 */
	private String ip;
	/**
	 * 区域（I区、II区）
	 */
	private String area;
	/**
	 * 设备名称
	 */
	private String name;
	/**
	 * 设备归属者
	 */
	private String owner;
	/**
	 * 设备添加时间
	 */
	private Date addtime;
	/**
	 * 最新修改时间
	 */
	private String modifytime;
	/**
	 * 设备类型code
	 */
	private String devicecode;
	/**
	 * 地域ID(关联4位corpid)
	 */
	private String corpid;
	/**
	 * 厂商名称（从t_evice_factory获取）
	 */
	private String factory;
	/**
	 * 硬件型号
	 */
	private String model;
	/**
	 * 软件版本
	 */
	private String version;
	/**
	 * 设备标识（备注说明）
	 */
	private String remark;
	/**
	 * 设备显示名称（拼接“地域_平面_安全区_设备类型_自增数字”）
	 */
	private String showname;
	/**
	 * 采集机IP地址
	 */
	private String ipfetch;
	/**
	 * 日志解析代理ID
	 */
	private String agentid;
	/**
	 * 接入状态（0：未接入 1：接入）
	 */
	private int state;
	/**
	 * 设备电压等级（子属性）
	 */
	private String voltagelevel;
	/**
	 * 站点类型（父属性）
	 */
	private String zdlx;
	/**
	 * 最近操作用户帐号
	 */
	private String user;
	/**
	 * 所属平面信息（一平面、二平面）
	 */
	private String areapm;
	
	/**
	 * Mac地址
	 */
	private String mac;
	
	/**
	 * 分组编号
	 */
	private int groupid;
	/**
	 * 是否已被同步(0或空已经被同步,1需要同步)
	 */
	private boolean isneedsync;
	/**
	 * 最后更新时间
	 */
	private Timestamp lastupdatetime;
	/**
	 * 推送标志:0为都不推,1为推本地,2为推上级,3为本地和上级都推
	 */
	private int sendflag;
	
	/**
	 * 采集机ip
	 */
	private String agent_ip;
	
	/**
	 * 所属业务系统
	 */
	private String sys_belong;
	
	/**
	 * 联系电话
	 */
	private String phone;
	
	/**
	 * 负责人
	 */
	private String principal;
	
	/**
	 * 厂商唯一标识
	 */
	private String station_uid;
	
	/**
	 * 站点类型
	 */
	private String sitetype;
	
	/**
	 * 地域组织编号
	 */
	private String areaorg_id;
	
	/**
	 * 业务系统id
	 */
	private String bussys_id;
	
	private String phyaddress;
	
	/**
	 * 归属专业
	 */
	private String belongedMajor;

	/**
	 * 操作系统版本
	 */
	private String systemVersion;
	
	private Date findtime;
	
	 /**
     * 设备父级类型
     */
    private String parent_devicecode;
    
    /**
     * nmap扫描系统名
     */
    private String nmapSystem;
    
    /**
	 * 1、主属；2、副属类型
	 */
	private int stationType;
	
	public String getBelongedMajor() {
		return belongedMajor;
	}

	public void setBelongedMajor(String belongedMajor) {
		this.belongedMajor = belongedMajor;
	}

	public String getSystemVersion() {
		return systemVersion;
	}

	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
	}

	public Date getFindtime() {
		return findtime;
	}

	public void setFindtime(Date findtime) {
		this.findtime = findtime;
	}

	public String getParent_devicecode() {
		return parent_devicecode;
	}

	public void setParent_devicecode(String parent_devicecode) {
		this.parent_devicecode = parent_devicecode;
	}

	public String getNmapSystem() {
		return nmapSystem;
	}

	public void setNmapSystem(String nmapSystem) {
		this.nmapSystem = nmapSystem;
	}

	public int getStationType() {
		return stationType;
	}

	public void setStationType(int stationType) {
		this.stationType = stationType;
	}

	public String getPhyaddress() {
		return phyaddress;
	}

	public void setPhyaddress(String phyaddress) {
		this.phyaddress = phyaddress;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}	
	
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public String getModifytime() {
		return modifytime;
	}

	public void setModifytime(String modifytime) {
		this.modifytime = modifytime;
	}

	public String getDevicecode() {
		return devicecode;
	}

	public void setDevicecode(String devicecode) {
		this.devicecode = devicecode;
	}

	public String getCorpid() {
		return corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	public String getFactory() {
		return factory;
	}

	public void setFactory(String factory) {
		this.factory = factory;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getShowname() {
		return showname;
	}

	public void setShowname(String showname) {
		this.showname = showname;
	}

	public String getIpfetch() {
		return ipfetch;
	}

	public void setIpfetch(String ipfetch) {
		this.ipfetch = ipfetch;
	}

	public String getAgentid() {
		return agentid;
	}

	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getVoltagelevel() {
		return voltagelevel;
	}

	public void setVoltagelevel(String voltagelevel) {
		this.voltagelevel = voltagelevel;
	}

	public String getZdlx() {
		return zdlx;
	}

	public void setZdlx(String zdlx) {
		this.zdlx = zdlx;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getAreapm() {
		return areapm;
	}

	public void setAreapm(String areapm) {
		this.areapm = areapm;
	}

	public boolean isIsneedsync() {
		return isneedsync;
	}

	public void setIsneedsync(boolean isneedsync) {
		this.isneedsync = isneedsync;
	}

	
	public Timestamp getLastupdatetime() {
		return lastupdatetime;
	}

	public void setLastupdatetime(Timestamp lastupdatetime) {
		this.lastupdatetime = lastupdatetime;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addtime == null) ? 0 : addtime.hashCode());
		result = prime * result + ((agent_ip == null) ? 0 : agent_ip.hashCode());
		result = prime * result + ((agentid == null) ? 0 : agentid.hashCode());
		result = prime * result + ((area == null) ? 0 : area.hashCode());
		result = prime * result + ((areaorg_id == null) ? 0 : areaorg_id.hashCode());
		result = prime * result + ((areapm == null) ? 0 : areapm.hashCode());
		result = prime * result + ((belongedMajor == null) ? 0 : belongedMajor.hashCode());
		result = prime * result + ((bussys_id == null) ? 0 : bussys_id.hashCode());
		result = prime * result + ((corpid == null) ? 0 : corpid.hashCode());
		result = prime * result + ((devicecode == null) ? 0 : devicecode.hashCode());
		result = prime * result + ((factory == null) ? 0 : factory.hashCode());
		result = prime * result + ((findtime == null) ? 0 : findtime.hashCode());
		result = prime * result + groupid;
		result = prime * result + ((guid == null) ? 0 : guid.hashCode());
		result = prime * result + id;
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + ((ipfetch == null) ? 0 : ipfetch.hashCode());
		result = prime * result + (isneedsync ? 1231 : 1237);
		result = prime * result + ((lastupdatetime == null) ? 0 : lastupdatetime.hashCode());
		result = prime * result + ((mac == null) ? 0 : mac.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + ((modifytime == null) ? 0 : modifytime.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((nmapSystem == null) ? 0 : nmapSystem.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result + ((parent_devicecode == null) ? 0 : parent_devicecode.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((phyaddress == null) ? 0 : phyaddress.hashCode());
		result = prime * result + ((principal == null) ? 0 : principal.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + sendflag;
		result = prime * result + ((showname == null) ? 0 : showname.hashCode());
		result = prime * result + ((sitetype == null) ? 0 : sitetype.hashCode());
		result = prime * result + state;
		result = prime * result + stationType;
		result = prime * result + ((station_uid == null) ? 0 : station_uid.hashCode());
		result = prime * result + ((sys_belong == null) ? 0 : sys_belong.hashCode());
		result = prime * result + ((systemVersion == null) ? 0 : systemVersion.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		result = prime * result + ((voltagelevel == null) ? 0 : voltagelevel.hashCode());
		result = prime * result + ((zdlx == null) ? 0 : zdlx.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DeviceBean other = (DeviceBean) obj;
		if (addtime == null) {
			if (other.addtime != null)
				return false;
		} else if (!addtime.equals(other.addtime))
			return false;
		if (agent_ip == null) {
			if (other.agent_ip != null)
				return false;
		} else if (!agent_ip.equals(other.agent_ip))
			return false;
		if (agentid == null) {
			if (other.agentid != null)
				return false;
		} else if (!agentid.equals(other.agentid))
			return false;
		if (area == null) {
			if (other.area != null)
				return false;
		} else if (!area.equals(other.area))
			return false;
		if (areaorg_id == null) {
			if (other.areaorg_id != null)
				return false;
		} else if (!areaorg_id.equals(other.areaorg_id))
			return false;
		if (areapm == null) {
			if (other.areapm != null)
				return false;
		} else if (!areapm.equals(other.areapm))
			return false;
		if (belongedMajor == null) {
			if (other.belongedMajor != null)
				return false;
		} else if (!belongedMajor.equals(other.belongedMajor))
			return false;
		if (bussys_id == null) {
			if (other.bussys_id != null)
				return false;
		} else if (!bussys_id.equals(other.bussys_id))
			return false;
		if (corpid == null) {
			if (other.corpid != null)
				return false;
		} else if (!corpid.equals(other.corpid))
			return false;
		if (devicecode == null) {
			if (other.devicecode != null)
				return false;
		} else if (!devicecode.equals(other.devicecode))
			return false;
		if (factory == null) {
			if (other.factory != null)
				return false;
		} else if (!factory.equals(other.factory))
			return false;
		if (findtime == null) {
			if (other.findtime != null)
				return false;
		} else if (!findtime.equals(other.findtime))
			return false;
		if (groupid != other.groupid)
			return false;
		if (guid == null) {
			if (other.guid != null)
				return false;
		} else if (!guid.equals(other.guid))
			return false;
		if (id != other.id)
			return false;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (ipfetch == null) {
			if (other.ipfetch != null)
				return false;
		} else if (!ipfetch.equals(other.ipfetch))
			return false;
		if (isneedsync != other.isneedsync)
			return false;
		if (lastupdatetime == null) {
			if (other.lastupdatetime != null)
				return false;
		} else if (!lastupdatetime.equals(other.lastupdatetime))
			return false;
		if (mac == null) {
			if (other.mac != null)
				return false;
		} else if (!mac.equals(other.mac))
			return false;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (modifytime == null) {
			if (other.modifytime != null)
				return false;
		} else if (!modifytime.equals(other.modifytime))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (nmapSystem == null) {
			if (other.nmapSystem != null)
				return false;
		} else if (!nmapSystem.equals(other.nmapSystem))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		if (parent_devicecode == null) {
			if (other.parent_devicecode != null)
				return false;
		} else if (!parent_devicecode.equals(other.parent_devicecode))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (phyaddress == null) {
			if (other.phyaddress != null)
				return false;
		} else if (!phyaddress.equals(other.phyaddress))
			return false;
		if (principal == null) {
			if (other.principal != null)
				return false;
		} else if (!principal.equals(other.principal))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (sendflag != other.sendflag)
			return false;
		if (showname == null) {
			if (other.showname != null)
				return false;
		} else if (!showname.equals(other.showname))
			return false;
		if (sitetype == null) {
			if (other.sitetype != null)
				return false;
		} else if (!sitetype.equals(other.sitetype))
			return false;
		if (state != other.state)
			return false;
		if (stationType != other.stationType)
			return false;
		if (station_uid == null) {
			if (other.station_uid != null)
				return false;
		} else if (!station_uid.equals(other.station_uid))
			return false;
		if (sys_belong == null) {
			if (other.sys_belong != null)
				return false;
		} else if (!sys_belong.equals(other.sys_belong))
			return false;
		if (systemVersion == null) {
			if (other.systemVersion != null)
				return false;
		} else if (!systemVersion.equals(other.systemVersion))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		if (voltagelevel == null) {
			if (other.voltagelevel != null)
				return false;
		} else if (!voltagelevel.equals(other.voltagelevel))
			return false;
		if (zdlx == null) {
			if (other.zdlx != null)
				return false;
		} else if (!zdlx.equals(other.zdlx))
			return false;
		return true;
	}

	public int getSendflag() {
		return sendflag;
	}

	public void setSendflag(int sendflag) {
		this.sendflag = sendflag;
	}
	
	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public int getGroupid() {
		return groupid;
	}

	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}

	public String getAgent_ip() {
		return agent_ip;
	}

	public void setAgent_ip(String agent_ip) {
		this.agent_ip = agent_ip;
	}

	public String getSys_belong() {
		return sys_belong;
	}

	public void setSys_belong(String sys_belong) {
		this.sys_belong = sys_belong;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getStation_uid() {
		return station_uid;
	}

	public void setStation_uid(String station_uid) {
		this.station_uid = station_uid;
	}

	public String getSitetype() {
		return sitetype;
	}

	public void setSitetype(String sitetype) {
		this.sitetype = sitetype;
	}

	public String getAreaorg_id() {
		return areaorg_id;
	}

	public void setAreaorg_id(String areaorg_id) {
		this.areaorg_id = areaorg_id;
	}
	
	

	public String getBussys_id() {
		return bussys_id;
	}

	public void setBussys_id(String bussys_id) {
		this.bussys_id = bussys_id;
	}

	@Override
	public String toString() {
		return "DeviceBean [id=" + id + ", ip=" + ip + ", area=" + area + ", name=" + name + ", owner=" + owner
				+ ", addtime=" + addtime + ", modifytime=" + modifytime + ", devicecode=" + devicecode + ", corpid="
				+ corpid + ", factory=" + factory + ", model=" + model + ", version=" + version + ", remark=" + remark
				+ ", showname=" + showname + ", ipfetch=" + ipfetch + ", agentid=" + agentid + ", state=" + state
				+ ", voltagelevel=" + voltagelevel + ", zdlx=" + zdlx + ", user=" + user + ", areapm=" + areapm
				+ ", isneedsync=" + isneedsync + ", lastupdatetime=" + lastupdatetime+ ", sendflag=" + sendflag + "]";
	}
}
