package Atcrowdfunding.controller;

import Atcrowdfunding.bean.TRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 角色维护功能
 */

@Controller
public class TRoleController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    TRoleService tRoleService;

    /**
     * 它和下面一个方法是一组的
     * 这里是找到jsp页面，下面是异步刷新对应$.get(映射地址，function（）{})的方法
     *
     * @return
     */
    @RequestMapping("/static/role/index.html")
    public String toRolePage(){

        return "role/role";
    }

    /**
     * 使用异步的方式进行
     * @return
     */
    @ResponseBody
    @RequestMapping("role/rolesList")
    public List<TRole> roleList(){
        List<TRole> roles=tRoleService.listRole(null);

        return roles;
    }
}
