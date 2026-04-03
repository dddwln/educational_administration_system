package com.ruoyi.xueguan.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * ???��????????????+????????对象 xg_material
 * 
 * @author ruoyi
 * @date 2026-04-03
 */
public class Material extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ????ID */
    private Long id;

    /** ???????ID???????? */
    @Excel(name = "???????ID????????")
    private Long projectId;

    /** ?????????????/????/?????? */
    @Excel(name = "?????????????/????/??????")
    private String materialType;

    /** ??????? */
    @Excel(name = "???????")
    private String title;

    /** ???????��?????? *_file ??��????????????????????? */
    @Excel(name = "???????��?????? *_file ??��?????????????????????")
    private String materialFile;

    /** ?��?? */
    @Excel(name = "?��??")
    private String version;

    /** ?????ID??sys_user.user_id?? */
    @Excel(name = "?????ID??sys_user.user_id??")
    private Long uploaderId;

    /** ?????? */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Excel(name = "??????", width = 30, dateFormat = "yyyy-MM-dd")
    private Date uploadTime;

    /** ????????????/???/????/??�r?? */
    @Excel(name = "????????????/???/????/??�r??")
    private String auditStatus;

    /** ??????/??????? */
    @Excel(name = "??????/???????")
    private String auditComment;

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

    public void setProjectId(Long projectId) 
    {
        this.projectId = projectId;
    }

    public Long getProjectId() 
    {
        return projectId;
    }

    public void setMaterialType(String materialType) 
    {
        this.materialType = materialType;
    }

    public String getMaterialType() 
    {
        return materialType;
    }

    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }

    public void setMaterialFile(String materialFile) 
    {
        this.materialFile = materialFile;
    }

    public String getMaterialFile() 
    {
        return materialFile;
    }

    public void setVersion(String version) 
    {
        this.version = version;
    }

    public String getVersion() 
    {
        return version;
    }

    public void setUploaderId(Long uploaderId) 
    {
        this.uploaderId = uploaderId;
    }

    public Long getUploaderId() 
    {
        return uploaderId;
    }

    public void setUploadTime(Date uploadTime) 
    {
        this.uploadTime = uploadTime;
    }

    public Date getUploadTime() 
    {
        return uploadTime;
    }

    public void setAuditStatus(String auditStatus) 
    {
        this.auditStatus = auditStatus;
    }

    public String getAuditStatus() 
    {
        return auditStatus;
    }

    public void setAuditComment(String auditComment) 
    {
        this.auditComment = auditComment;
    }

    public String getAuditComment() 
    {
        return auditComment;
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
            .append("projectId", getProjectId())
            .append("materialType", getMaterialType())
            .append("title", getTitle())
            .append("materialFile", getMaterialFile())
            .append("version", getVersion())
            .append("uploaderId", getUploaderId())
            .append("uploadTime", getUploadTime())
            .append("auditStatus", getAuditStatus())
            .append("auditComment", getAuditComment())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
