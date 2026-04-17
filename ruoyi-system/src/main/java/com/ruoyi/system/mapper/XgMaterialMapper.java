package com.ruoyi.system.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.system.domain.xg.XgMaterial;

public interface XgMaterialMapper
{
    XgMaterial selectXgMaterialById(Long materialId);

    List<XgMaterial> selectXgMaterialList(XgMaterial material);

    int insertXgMaterial(XgMaterial material);

    int updateXgMaterial(XgMaterial material);

    int deleteXgMaterialByIds(Long[] materialIds);

    int auditMaterial(@Param("materialId") Long materialId,
                      @Param("auditStatus") String auditStatus,
                      @Param("auditBy") String auditBy,
                      @Param("rejectReason") String rejectReason);

    int insertAuditRecord(@Param("materialId") Long materialId,
                          @Param("auditAction") String auditAction,
                          @Param("auditStatus") String auditStatus,
                          @Param("auditOpinion") String auditOpinion,
                          @Param("auditBy") String auditBy);

    Map<String, Object> selectDashboardSummary(Map<String, Object> params);

    List<Map<String, Object>> selectCategoryStats(Map<String, Object> params);

    List<Map<String, Object>> selectDeptStats(Map<String, Object> params);

    List<Map<String, Object>> selectAuditStatusStats(Map<String, Object> params);

    List<Map<String, Object>> selectTrendStats(Map<String, Object> params);
}
