package com.example.rocketmq.producer.bean;

/**
 * @author jackie
 * @Title: BaseBean
 * @ProjectName sprintboot-junit
 * @Description: 基础的通用bean，例如分页参数，版本号；
 * @date 2018/11/19 15:29
 */
public class BaseBean {
    /** 版本号 */
    private Integer version;
    /** 页码 */
    private Integer pageNo;
    /** 页列表大小 */
    private Integer pageSize;
    /** 业务类型 */
    private String bizType;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }



}
