package com.ruoyi.xueguan.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * ?????????????????????????对象 xg_project
 * 
 * @author ruoyi
 * @date 2026-04-03
 */
public class Project extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ????ID */
    private Long id;

    /** ?????? */
    @Excel(name = "??????")
    private String projectCode;

    /** ??????? */
    @Excel(name = "???????")
    private String projectName;

    /** ????????????/???? */
    @Excel(name = "????????????/????")
    private String projectType;

    /** ?????????ID??sys_user.user_id?? */
    @Excel(name = "?????????ID??sys_user.user_id??")
    private Long leaderId;

    /** ??????? */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Excel(name = "???????", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startDate;

    /** ???????? */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Excel(name = "????????", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endDate;

    /** ??????? */
    @Excel(name = "???????")
    private BigDecimal totalBudget;

    /** ????????/??????/????? */
    @Excel(name = "????????/??????/?????")
    private String projectStatus;

    /**  */
    private String delFlag;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setProjectCode(String projectCode) 
    {
        this.projectCode = projectCode;
    }

    public String getProjectCode() 
    {
        return projectCode;
    }

    public void setProjectName(String projectName) 
    {
        this.projectName = projectName;
    }

    public String getProjectName() 
    {
        return projectName;
    }

    public void setProjectType(String projectType) 
    {
        this.projectType = projectType;
    }

    public String getProjectType() 
    {
        return projectType;
    }

    public void setLeaderId(Long leaderId) 
    {
        this.leaderId = leaderId;
    }

    public Long getLeaderId() 
    {
        return leaderId;
    }

    public void setStartDate(Date startDate) 
    {
        this.startDate = startDate;
    }

    public Date getStartDate() 
    {
        return startDate;
    }

    public void setEndDate(Date endDate) 
    {
        this.endDate = endDate;
    }

    public Date getEndDate() 
    {
        return endDate;
    }

    public void setTotalBudget(BigDecimal totalBudget) 
    {
        this.totalBudget = totalBudget;
    }

    public BigDecimal getTotalBudget() 
    {
        return totalBudget;
    }

    public void setProjectStatus(String projectStatus) 
    {
        this.projectStatus = projectStatus;
    }

    public String getProjectStatus() 
    {
        return projectStatus;
    }

    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("projectCode", getProjectCode())
            .append("projectName", getProjectName())
            .append("projectType", getProjectType())
            .append("leaderId", getLeaderId())
            .append("startDate", getStartDate())
            .append("endDate", getEndDate())
            .append("totalBudget", getTotalBudget())
            .append("projectStatus", getProjectStatus())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
