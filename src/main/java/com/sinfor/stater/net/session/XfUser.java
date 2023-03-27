package com.sinfor.stater.net.session;

import lombok.Data;

/**
 * @author fengwen
 * @date 2022/5/17
 * @description TODO
 **/
@Data
public class XfUser {

    /**
     * 网点编号
     */
    private String siteId;
    /**
     * 网点网点
     */
    private String siteName;
    /**
     * 财务网点
     */
    private String paySiteId;
    /**
     * 上级财务中心
     */
    private String payCenterId;
    /**
     * 所属中心
     */
    private String centerId;
    /**
     * 用户帐号
     */
    private String userId;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 是否启用
     */
    private int enabled;
    /**
     * 所属分拔中心
     */
    private String ofSiteId;
    /**
     * 所属呼叫中心
     */
    private String custservid;
    /**
     * 所属承包区
     */
    private String contdept;
    /**
     * 所属承包区名称
     */
    private String contdeptName;
    /**
     * 所属承包区级别
     */
    private Integer levelid;
    /**
     * 上级承包区
     */
    private String parentContdept;
    /**
     * 上级承包区名称
     */
    private String parentContdeptName;
    /**
     * 大业务网点可查询天数
     */
    private int siteQDay;
    /**
     * 网点级别
     */
    private Integer siteTypeId;
    /**
     * 部门编号
     */
    private String deptid;
    /**
     * 部门名称
     */
    private String deptname;
    /**
     * 限制网点
     */
    private boolean limitSite;
    /**
     * 巴枪用户(app用户)
     */
    private int isbaruser;
    /**
     * 硬件绑定
     */
    private Integer hardbind;

}
