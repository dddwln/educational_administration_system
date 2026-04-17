package com.ruoyi.web.controller.xueguan;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.framework.web.service.PermissionService;
import com.ruoyi.system.domain.xg.XgMaterial;
import com.ruoyi.system.service.xg.IXgMaterialService;

@RestController
@RequestMapping("/xg/material")
public class XgMaterialController extends BaseController
{
    @Autowired
    private IXgMaterialService materialService;

    @Autowired
    private PermissionService permissionService;

    @PreAuthorize("@ss.hasAnyPermi('xg:material:list,xg:material:audit')")
    @GetMapping("/list")
    public TableDataInfo list(XgMaterial material)
    {
        startPage();
        if (!permissionService.hasRole("admin") && !permissionService.hasPermi("xg:material:audit"))
        {
            material.setOwnerUserId(getUserId());
        }
        List<XgMaterial> list = materialService.selectXgMaterialList(material);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('xg:material:query')")
    @GetMapping("/{materialId}")
    public AjaxResult getInfo(@PathVariable Long materialId)
    {
        return success(materialService.selectXgMaterialById(materialId));
    }

    @PreAuthorize("@ss.hasPermi('xg:material:add')")
    @Log(title = "学管材料", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody XgMaterial material)
    {
        material.setOwnerUserId(getUserId());
        material.setDeptId(getDeptId());
        material.setCreateBy(getUsername());
        if (permissionService.hasRole("teacher"))
        {
            material.setOwnerRole("teacher");
        }
        else if (permissionService.hasRole("student"))
        {
            material.setOwnerRole("student");
        }
        else
        {
            material.setOwnerRole("admin");
        }
        return toAjax(materialService.insertXgMaterial(material));
    }

    @PreAuthorize("@ss.hasPermi('xg:material:edit')")
    @Log(title = "学管材料", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody XgMaterial material)
    {
        material.setUpdateBy(getUsername());
        return toAjax(materialService.updateXgMaterial(material));
    }

    @PreAuthorize("@ss.hasPermi('xg:material:remove')")
    @Log(title = "学管材料", businessType = BusinessType.DELETE)
    @DeleteMapping("/{materialIds}")
    public AjaxResult remove(@PathVariable Long[] materialIds)
    {
        return toAjax(materialService.deleteXgMaterialByIds(materialIds));
    }

    @PreAuthorize("@ss.hasPermi('xg:material:audit')")
    @Log(title = "学管材料审核", businessType = BusinessType.UPDATE)
    @PutMapping("/{materialId}/audit")
    public AjaxResult audit(@PathVariable Long materialId,
                            @RequestParam String auditStatus,
                            @RequestParam(required = false) String rejectReason)
    {
        if (!"1".equals(auditStatus) && !"2".equals(auditStatus))
        {
            return error("审核状态只能为通过或驳回");
        }
        return toAjax(materialService.auditMaterial(materialId, auditStatus, getUsername(), rejectReason));
    }
}

