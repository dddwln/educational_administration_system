package com.ruoyi.xueguan.controller;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.xueguan.domain.Project;
import com.ruoyi.xueguan.service.IProjectService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * ?????????????????????????Controller
 * 
 * @author ruoyi
 * @date 2026-04-03
 */
@Controller
@RequestMapping("/xueguan/project")
public class ProjectController extends BaseController
{
    private String prefix = "xueguan/project";

    @Autowired
    private IProjectService projectService;

    @RequiresPermissions("xueguan:project:view")
    @GetMapping()
    public String project()
    {
        return prefix + "/project";
    }

    /**
     * 查询?????????????????????????列表
     */
    @RequiresPermissions("xueguan:project:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Project project)
    {
        startPage();
        List<Project> list = projectService.selectProjectList(project);
        return getDataTable(list);
    }

    /**
     * 导出?????????????????????????列表
     */
    @RequiresPermissions("xueguan:project:export")
    @Log(title = "?????????????????????????", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Project project)
    {
        List<Project> list = projectService.selectProjectList(project);
        ExcelUtil<Project> util = new ExcelUtil<Project>(Project.class);
        return util.exportExcel(list, "?????????????????????????数据");
    }

    /**
     * 新增?????????????????????????
     */
    @RequiresPermissions("xueguan:project:add")
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存?????????????????????????
     */
    @RequiresPermissions("xueguan:project:add")
    @Log(title = "?????????????????????????", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Project project)
    {
        return toAjax(projectService.insertProject(project));
    }

    /**
     * 修改?????????????????????????
     */
    @RequiresPermissions("xueguan:project:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        Project project = projectService.selectProjectById(id);
        mmap.put("project", project);
        return prefix + "/edit";
    }

    /**
     * 修改保存?????????????????????????
     */
    @RequiresPermissions("xueguan:project:edit")
    @Log(title = "?????????????????????????", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Project project)
    {
        return toAjax(projectService.updateProject(project));
    }

    /**
     * 删除?????????????????????????
     */
    @RequiresPermissions("xueguan:project:remove")
    @Log(title = "?????????????????????????", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(projectService.deleteProjectByIds(ids));
    }
}
