package cn.csg.common.vo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Author: tide
 * @Description: 主站Redis-组织bean对象
 * @Date: Created in 16:52 2019/4/4
 * @Modified By:
 */
public class CorporationBean implements Serializable{

	private static final long serialVersionUID = 1390741768658491557L;
	/**
	 * 地域ID
	 */
	private String corpid;

	/**
	 * 地域名称（显示名称）
	 */
	private String corpname;

	/**
	 * 排序编号
	 */
	private Integer sort;

	/**
	 * 平台网省名称（索引名称，含MP）
	 */
	private String platformname;
	/**
	 * 最后更新时间
	 */
	private Timestamp modifytime;

	/**
	 * 公司名称
	 */
	private String orgName;

	/**
	 * 调管单位
	 */
	private String dgCorpName;
	/**
	 * 归属单位
	 */
	private String gsCorpName;
	/**
	 * 单位
	 */
	private String unitAbbreviation;

	public String getCorpid() {
		return corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	public String getCorpname() {
		return corpname;
	}

	public void setCorpname(String corpname) {
		this.corpname = corpname;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getPlatformname() {
		return platformname;
	}

	public void setPlatformname(String platformname) {
		this.platformname = platformname;
	}

	public Timestamp getModifytime() {
		return modifytime;
	}

	public void setModifytime(Timestamp modifytime) {
		this.modifytime = modifytime;
	}

	@Override
	public String toString() {
		return "CorporationBean{" +
				"corpid='" + corpid + '\'' +
				", corpname='" + corpname + '\'' +
				", sort=" + sort +
				", platformname='" + platformname + '\'' +
				", modifytime=" + modifytime +
				", orgName='" + orgName + '\'' +
				", dgCorpName='" + dgCorpName + '\'' +
				", gsCorpName='" + gsCorpName + '\'' +
				", unitAbbreviation='" + unitAbbreviation + '\'' +
				'}';
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getDgCorpName() {
		return dgCorpName;
	}

	public void setDgCorpName(String dgCorpName) {
		this.dgCorpName = dgCorpName;
	}

	public String getGsCorpName() {
		return gsCorpName;
	}

	public void setGsCorpName(String gsCorpName) {
		this.gsCorpName = gsCorpName;
	}

	public String getUnitAbbreviation() {
		return unitAbbreviation;
	}

	public void setUnitAbbreviation(String unitAbbreviation) {
		this.unitAbbreviation = unitAbbreviation;
	}
}