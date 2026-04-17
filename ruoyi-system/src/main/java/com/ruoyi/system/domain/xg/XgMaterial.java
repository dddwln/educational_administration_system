package com.ruoyi.system.domain.xg;

import java.util.Date;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 学管平台上传材料对象 xg_material
 */
public class XgMaterial extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long materialId;
    private Long ownerUserId;
    private String ownerRole;
    private Long deptId;
    private String materialCategory;
    private String materialType;
    private String title;
    private String projectNo;
    private String relatedBizId;
    private String materialData;
    private BigDecimal amount;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date materialDate;

    private String fileUrl;
    private String fileName;
    private String originalFilename;
    private String auditStatus;
    private String auditBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    private String rejectReason;
    private String remarks;

    public Long getMaterialId() { return materialId; }
    public void setMaterialId(Long materialId) { this.materialId = materialId; }
    public Long getOwnerUserId() { return ownerUserId; }
    public void setOwnerUserId(Long ownerUserId) { this.ownerUserId = ownerUserId; }
    public String getOwnerRole() { return ownerRole; }
    public void setOwnerRole(String ownerRole) { this.ownerRole = ownerRole; }
    public Long getDeptId() { return deptId; }
    public void setDeptId(Long deptId) { this.deptId = deptId; }
    public String getMaterialCategory() { return materialCategory; }
    public void setMaterialCategory(String materialCategory) { this.materialCategory = materialCategory; }
    public String getMaterialType() { return materialType; }
    public void setMaterialType(String materialType) { this.materialType = materialType; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getProjectNo() { return projectNo; }
    public void setProjectNo(String projectNo) { this.projectNo = projectNo; }
    public String getRelatedBizId() { return relatedBizId; }
    public void setRelatedBizId(String relatedBizId) { this.relatedBizId = relatedBizId; }
    public String getMaterialData() { return materialData; }
    public void setMaterialData(String materialData) { this.materialData = materialData; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public Date getMaterialDate() { return materialDate; }
    public void setMaterialDate(Date materialDate) { this.materialDate = materialDate; }
    public String getFileUrl() { return fileUrl; }
    public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public String getOriginalFilename() { return originalFilename; }
    public void setOriginalFilename(String originalFilename) { this.originalFilename = originalFilename; }
    public String getAuditStatus() { return auditStatus; }
    public void setAuditStatus(String auditStatus) { this.auditStatus = auditStatus; }
    public String getAuditBy() { return auditBy; }
    public void setAuditBy(String auditBy) { this.auditBy = auditBy; }
    public Date getAuditTime() { return auditTime; }
    public void setAuditTime(Date auditTime) { this.auditTime = auditTime; }
    public String getRejectReason() { return rejectReason; }
    public void setRejectReason(String rejectReason) { this.rejectReason = rejectReason; }
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}
