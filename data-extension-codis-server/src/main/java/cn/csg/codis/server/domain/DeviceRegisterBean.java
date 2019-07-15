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

import java.util.Date;


/**
 * 类{@code DeviceRegisterBean}装置ip
 *
 * @author Alex Han
 * @since 1.0
 * @version 1.2
 */

@Data
public class DeviceRegisterBean extends JedisCollectDomain {

	private int id;
	/**
	 * 资产唯一标识
	 */
	private String guid;
	
	/**
	 * 设备名称
	 */
	private String deviceName;
	
	/**
	 * 设备管理IP
	 */
	private String ip;
	
	/**
	 * 设备类型code
	 */
	private String deviceCode;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 更新时间
	 */
	private Date updateTime;
	
	/**
	 * 装置状态：  0:初始状态  1:注册成功    2:注册失败
	 */
	private String state;
	
	/**
	 * 注册开始时间
	 */
	private Date registerTime;
	
	private String flag;

	/**
	 * 地域ID
	 */
	private String corpid;
	
	/**
	 * 调管单位名
	 */
	private String dgCorpName;
	
	/**
	 * 调管单位编码
	 */
	private String dgCorpId;
	
	/**
	 * 归属单位名
	 */
	private String gsCorpName;
	
	/**
	 * 归属单位编码
	 */
	private String gsCorpId;
	
	/**
	 * 安全分区
	 */
	private String area;
	
	/**
	 * 版本
	 */
	private String curVersion;
	
	/**
	 * 主副资产：0主，1副
	 */
	private String stationType;


}
