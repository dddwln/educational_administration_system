package com.ruoyi.system.service.xg;

import java.util.List;
import java.util.Map;
import com.ruoyi.system.domain.xg.XgMaterial;

public interface IXgMaterialService
{
    XgMaterial selectXgMaterialById(Long materialId);

    List<XgMaterial> selectXgMaterialList(XgMaterial material);

    int insertXgMaterial(XgMaterial material);

    int updateXgMaterial(XgMaterial material);

    int deleteXgMaterialByIds(Long[] materialIds);

    int auditMaterial(Long materialId, String auditStatus, String auditBy, String rejectReason);

    Map<String, Object> selectDashboard(Map<String, Object> params);
}

