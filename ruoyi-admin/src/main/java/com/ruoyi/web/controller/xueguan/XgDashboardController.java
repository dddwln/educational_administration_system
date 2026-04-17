package com.ruoyi.web.controller.xueguan;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.service.xg.IXgMaterialService;

@RestController
@RequestMapping("/xg/dashboard")
public class XgDashboardController extends BaseController
{
    @Autowired
    private IXgMaterialService materialService;

    @PreAuthorize("@ss.hasPermi('xg:dashboard:view')")
    @GetMapping("/overview")
    public AjaxResult overview(@RequestParam(required = false) Long deptId,
                               @RequestParam(required = false) String beginTime,
                               @RequestParam(required = false) String endTime,
                               @RequestParam(required = false) String ownerRole)
    {
        Map<String, Object> params = new HashMap<>();
        params.put("deptId", deptId);
        params.put("beginTime", beginTime);
        params.put("endTime", endTime);
        params.put("ownerRole", ownerRole);
        return success(materialService.selectDashboard(params));
    }
}

