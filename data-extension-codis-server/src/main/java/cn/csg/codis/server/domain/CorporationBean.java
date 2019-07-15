//*********************************************************************
//系统名称：dsmp2.0-cluster-consumer
//Copyright(C)2000-2017 NARI Information and Communication Technology //Branch. All rights reserved.
//版本信息：dsmp2.0-cluster-consumer
//#作者：刘刚$权重：40%$手机：13182986519#
//版本                     日期              作者       变更记录
//dsmp2.0-cluster-consumer         2015/10/20       刘刚　     新建
//*********************************************************************
package cn.csg.codis.server.domain;

import cn.csg.codis.server.domain.common.JedisCollectDomain;
import lombok.Data;


/**
 * 类{@code CorporationBean}组织机构表
 *
 * @author Alex Han
 * @since 1.0
 * @version 1.2
 */

@Data
public class CorporationBean extends JedisCollectDomain{
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
	private String modifytime;

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


}