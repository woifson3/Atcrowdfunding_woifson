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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


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
     * 参数里面的Integer PageNum 是点击页面分页按钮传过来的参数，根据它进行每一页的数据筛选
     *
     * @return
     */

    @RequestMapping("static/admin/index.html")
    public String toUserPage(@RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
                             Model model, HttpSession session,
                             @RequestParam(value = "condition", defaultValue = "", required = false) String condition) {
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
     *
     * @param id
     * @return
     */
    @RequestMapping("/admin/deleteAdmin")
    public String deleteAdmin(Integer id, @RequestHeader("referer") String referer) {
        adminService.deleteAdminById(id);
        return "redirect:" + referer;//因为上一页是一个带有协议的绝对路径，所以不用加/
    }

    /**
     * 跳转到添加用户的页面
     *
     * @return
     */
    @RequestMapping("admin/add.html")
    public String toAdminpage() {
        return "admin/add";
    }

    /**
     * 添加管理员
     *
     * @param admin
     * @return
     */
    @RequestMapping(value = "admin/addAdmin", method = RequestMethod.POST)
    public String addAdmin(HttpSession sesion, Model model, TAdmin admin) {
        //要对表单输入的内容进行判断是否重复
        try {
            adminService.addAdmin(admin);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("mess", e.getMessage());//此处获取的mess就是service层中抛出的异常中写上的
            return "admin/add";//要是信息填写不正确，跳转到add.jsp页面      *****转发是进入进入jsp页面的名字，重定向是指向controller中的映射地址
        }

        //保存页面新增管理员信息
        adminService.addAdmin(admin);
        //保存完跳转到表单项的最后一页，最后一页可以在PageInfo中获取
        Integer pages = (Integer) sesion.getAttribute(AppConsts.PAGES_COUNT);//拿到列表的最后一页
        return "redirect:/static/admin/index.html？pageNum=" + (pages + 1);//对应的页面是add.jsp的登录账号那边
    }

    /**
     * 到修改管理员信息页面
     *
     * @param myId：来源于user.jsp192行的定义，代表修改按钮这一行数据admin的唯一标识
     * @return
     */
    @RequestMapping(value = "admin/edit.html")
    public String toEditPage(Integer myId,
                             HttpServletRequest request,
                             HttpSession session,
                             @RequestHeader("referer") String referer) {
        //通过ID获取被修改的信息,组装成一个admin对象。回显到页面上
        TAdmin admin = adminService.getAdminById(myId);
        //把referer地址放入，给下面的方法跳转使用
        session.setAttribute("ref", referer);

        request.setAttribute("editadmin", admin);
        //跳转到修改信息的页面
        return "admin/edit";
    }

    /**
     * 修改功能
     * 这里的adminService.updateAdminById(admin);没有用返回值。是因为页面调用这个方法不需要返回对象
     * 到页面中，只是在这里计算，而上面方法需要有TAdmin admin是因为页面中需要用到admin对象来进行回显
     *
     * @param admin
     * @return
     */
    @RequestMapping("admin/updateAdmin")
    public String updataAdmin(TAdmin admin, HttpSession session) {
        //对数据进行修改
        adminService.updateAdminById(admin);
        //跳转到最开始的用户维护页面
        String ref=(String)session.getAttribute("ref");
        //session中的ref没有用了，就清除掉
        session.removeAttribute(ref);
        //跳转到toEditPage方法的前一个页面
        return "redirect:"+ ref;
    }

    /**
     * 批量删除管理员
     * @param is
     * @param ref
     * @return
     */
    @RequestMapping("/ admin/deleteAdmins")
    public String deleteAdmins(@RequestParam("ids") String is,@RequestHeader("referer") String ref){
       // try {
            String[] ids = is.split(",");
            ArrayList<Integer> inte = new ArrayList<>();
            if(null !=ids){
               /* Stream<String> id = Arrays.stream(ids);
                int num = Integer.parseInt(id.toString());
                inte.add(num);*/
               for(String id:ids){
                   int num = Integer.parseInt(id);
                   inte.add(num);
               }
            }
            adminService.deleteAdmin(inte);
       // } catch (NumberFormatException e) {
         //   logger.info("批量删除出现错误"+e.getMessage());
       // }
        return "redirect:"+ref;
    }

}
