package Atcrowdfunding.service.impl;

import java.util.ArrayList;
import java.util.List;

import Atcrowdfunding.utils.AppDateUtils;
import Atcrowdfunding.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import Atcrowdfunding.bean.TAdmin;
import Atcrowdfunding.bean.TAdminExample;
import Atcrowdfunding.mapper.TAdminMapper;
import Atcrowdfunding.service.TAdminService;
import org.springframework.util.StringUtils;

@Service
public class TAdminServiceImpl implements TAdminService {

    @Autowired
    TAdminMapper adminMapper;

    /**
     * 判断loginacct和userpswd是否存在，用户能否登陆的
     *
     * @param loginacct
     * @param userpswd
     * @return
     */
    @Override
    public TAdmin doLogin(String loginacct, String userpswd) {
        //判断loginacct和usrpswd是否在存在
        String userPswd = MD5Util.digest(userpswd);
        //creatCriteria是mybatis逆向工程中的条件组装方法，此处是判断登陆名和密码是否符合，然后把条件放到，mapper查询语句中
        TAdminExample example = new TAdminExample();
        example.createCriteria().andLoginacctEqualTo(loginacct).andUserpswdEqualTo(userPswd);
        List<TAdmin> admins = adminMapper.selectByExample(example);

        if (CollectionUtils.isEmpty(admins) || admins.size() != 1) {
            return null;
        }
        return admins.get(0);//能够获到该用户，就把它返回给前端
    }

    /**
     * 展示用户列表
     *
     * @return
     */
    @Override
    public List<TAdmin> listAdmin(String condition) {

        TAdminExample example = null;
        //要是有筛选条件
        if (!StringUtils.isEmpty(condition)) {
            example = new TAdminExample();
            example.createCriteria().andLoginacctLike("%" + condition + "%");
            TAdminExample.Criteria criteria = example.createCriteria().andUserpswdEqualTo("%" + condition + "%");
            TAdminExample.Criteria criteria1 = example.createCriteria().andEmailLike("%" + condition + "%");
            example.or(criteria);
            example.or(criteria1);
        }
        //要是没有筛选条件，就查询所有
        List<TAdmin> admins = adminMapper.selectByExample(example);

        return admins;
    }

    /**
     * 根据ID删除admin
     *
     * @param id
     */
    @Override
    public void deleteAdminById(Integer id) {
        adminMapper.deleteByPrimaryKey(id);
    }

    /**
     * 保存新增管理员信息
     * @param admin
     */
    @Override
    public void addAdmin(TAdmin admin) {
        //在插入数据之前要对页面提交的资料进行验证是否有重复
        TAdminExample e = new TAdminExample();
        e.createCriteria().andLoginacctEqualTo(admin.getLoginacct());
        long num = adminMapper.countByExample(e);
        if(num>1){
            throw new RuntimeException("登陆名重复");//如果查到有重复抛一个异常出来
        }
        e.clear();
        e.createCriteria().andEmailEqualTo(admin.getEmail());
        long l = adminMapper.countByExample(e);
        if(l>1){
            throw new RuntimeException("邮箱重复");//如果查到有重复抛一个异常出来
        }

        //密码保存到数据库需要加密
        admin.setUserpswd(MD5Util.digest(admin.getUserpswd()));
        //需要保存数据进入数据库的时间
        admin.setCreatetime(AppDateUtils.getFormatTime());
        adminMapper.insertSelective(admin);
    }

    /**
     * 管理员修改页面
     * @param myId
     */
    @Override
    public TAdmin getAdminById(Integer myId) {
        TAdmin admin = adminMapper.selectByPrimaryKey(myId);
        return admin;
    }

    /**
     * 管理员信息修改
     * @param admin
     * @return
     */
    @Override
    public void updateAdminById(TAdmin admin) {
        adminMapper.updateByPrimaryKeySelective(admin);//admin必须包含id
    }

    /**
     * 批量删除管理员
     * @param inte
     */
    @Override
    public void deleteAdmin(ArrayList<Integer> inte) {
        TAdminExample example=new TAdminExample();
        example.createCriteria().andIdIn(inte);
        adminMapper.deleteByExample(example);

    }


}
