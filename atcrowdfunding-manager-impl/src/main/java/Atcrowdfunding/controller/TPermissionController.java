package Atcrowdfunding.controller;

import Atcrowdfunding.bean.TPermission;
import Atcrowdfunding.service.TPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TPermissionController {
    @Autowired
    TPermissionService permissionService;

    /**
     * 获取角色有的权限
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/role.html/getRolePermissionIds")
    public List<Integer> getRolePermissionIds(@RequestParam("id") Integer id) {  //参数看role到的{"id": roleId}的key值
        List<Integer> perssIds = permissionService.getHavePermissions(id);
        return perssIds;
    }

    @ResponseBody
    @RequestMapping("/permission/getPer")
    public List<TPermission> getPer() {
        List<TPermission> permissionsss = permissionService.getListPerisson();
        return permissionsss;
    }
}
