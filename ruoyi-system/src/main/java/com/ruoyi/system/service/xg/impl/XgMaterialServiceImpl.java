package com.ruoyi.system.service.xg.impl;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.xg.XgMaterial;
import com.ruoyi.system.mapper.XgMaterialMapper;
import com.ruoyi.system.service.xg.IXgMaterialService;

@Service
public class XgMaterialServiceImpl implements IXgMaterialService
{
    @Autowired
    private XgMaterialMapper materialMapper;

    @Override
    public XgMaterial selectXgMaterialById(Long materialId)
    {
        return materialMapper.selectXgMaterialById(materialId);
    }

    @Override
    public java.util.List<XgMaterial> selectXgMaterialList(XgMaterial material)
    {
        return materialMapper.selectXgMaterialList(material);
    }

    @Override
    public int insertXgMaterial(XgMaterial material)
    {
        material.setCreateTime(DateUtils.getNowDate());
        if (material.getAuditStatus() == null)
        {
            material.setAuditStatus("0");
        }
        return materialMapper.insertXgMaterial(material);
    }

    @Override
    public int updateXgMaterial(XgMaterial material)
    {
        material.setUpdateTime(DateUtils.getNowDate());
        return materialMapper.updateXgMaterial(material);
    }

    @Override
    public int deleteXgMaterialByIds(Long[] materialIds)
    {
        return materialMapper.deleteXgMaterialByIds(materialIds);
    }

    @Override
    @Transactional
    public int auditMaterial(Long materialId, String auditStatus, String auditBy, String rejectReason)
    {
        String action = "1".equals(auditStatus) ? "pass" : "reject";
        materialMapper.insertAuditRecord(materialId, action, auditStatus, rejectReason, auditBy);
        return materialMapper.auditMaterial(materialId, auditStatus, auditBy, rejectReason);
    }

    @Override
    public Map<String, Object> selectDashboard(Map<String, Object> params)
    {
        Map<String, Object> data = new HashMap<>();
        data.put("summary", materialMapper.selectDashboardSummary(params));
        data.put("categoryStats", materialMapper.selectCategoryStats(params));
        data.put("deptStats", materialMapper.selectDeptStats(params));
        data.put("auditStatusStats", materialMapper.selectAuditStatusStats(params));
        data.put("trendStats", materialMapper.selectTrendStats(params));
        return data;
    }
}
