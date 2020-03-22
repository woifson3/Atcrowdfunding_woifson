package Atcrowdfunding.controller;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import Atcrowdfunding.bean.TMenu;
import Atcrowdfunding.consts.AppConsts;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jdk.internal.org.objectweb.asm.commons.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import Atcrowdfunding.bean.TAdmin;
import Atcrowdfunding.service.TAdminService;

import java.util.List;


@Controller
public class TAdminController {

    @Autowired
    TAdminService adminService;
    @Autowired
    TMenuService menuService;


    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 登陆页面的操作
     *
     * @param loginacct
     * @param userpswd
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/doLogin")
    public String doLogin(String loginacct, String userpswd
            , HttpServletRequest request, HttpSession session) {

        //1.对页面传来的两个参数进行业务判断
        TAdmin admin = adminService.doLogin(loginacct, userpswd);

        //2.准备跳转页面需要的数据
        //2。1.请求失败跳转到登录页面继续登陆
        if (admin == null) {
            String errorMsg = "账号或密码错误";
            request.setAttribute("errorMsg", errorMsg);
            return "login";
        }
        //2.2.成功跳转到main后台页面
        session.setAttribute("admin", admin);

        //3.业务处理的结果返回给前端
        return "redirect:/main.html";
    }

    /**
     * 用户的注销
     *
     * @param session
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        //销毁掉session中的admin对象，就是注销
        session.invalidate();
        return "redirect:/index";
    }

    /**
     * 登陆后的首页
     *
     * @return
     */
    @RequestMapping("/main.html")
    public String toMainPage(HttpSession session) {
        //1.取出左边展示栏的菜单集合
        List<TMenu> parentMenus = menuService.listMenus();
        logger.info("查询到的菜单集合", parentMenus);
        //2.将获得的数据放入域中
        session.setAttribute("parentMenus", parentMenus);
        return "admin/main";
    }



     /**
      * 展示用户列表
     *     参数里面的Integer PageNum 是点击页面分页按钮传过来的参数，根据它进行每一页的数据筛选
     * @return
     */

    @RequestMapping("static/admin/index.html")
    public String toUserPage(@RequestParam(value="pageNum",defaultValue = "1",required = false)Integer pageNum,
                             Model model,HttpSession session,
                             @RequestParam(value="condition",defaultValue = "",required = false)String condition) {
        //开启每页展示的数据（第几页，展示几条内容）
        PageHelper.startPage(pageNum, 3);

        //1.查询出所有的用户列表数据
        List<TAdmin> admins = adminService.listAdmin(condition);

       //分页导航栏设置（分页的总数据，分页导航栏展示数字几）
        PageInfo<TAdmin> PageInfo = new PageInfo<>(admins, 3);
        session.setAttribute(AppConsts.PAGES_COUNT, PageInfo.getPages());
        //2.从数据库里面拿到的数据塞入到域中
        model.addAttribute("PageInfo", PageInfo);
        return "admin/user";
    }

    /**
     * 通过ID删除管理员
     * @param id
     * @return
     */
    @RequestMapping("/admin/deleteAdmin")
    public String deleteAdmin(Integer id,@RequestHeader("referer") String referer){
        adminService.deleteAdminById(id);
        return "redirect:"+referer ;//因为上一页是一个带有协议的绝对路径，所以不用加/
    }




}
