package Atcrowdfunding.service.impl;

import java.util.List;

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
	 * @param loginacct
	 * @param userpswd
	 * @return
	 */
	@Override
	public TAdmin doLogin(String loginacct, String userpswd) {
		//判断loginacct和usrpswd是否在存在
		String userPswd = MD5Util.digest(userpswd);
		//creatCriteria是mybatis逆向工程中的条件组装方法，此处是判断登陆名和密码是否符合，然后把条件放到，mapper查询语句中
		TAdminExample example=new TAdminExample();
		example.createCriteria().andLoginacctEqualTo(loginacct).andUserpswdEqualTo(userPswd);
		List<TAdmin> admins = adminMapper.selectByExample(example);

		if (CollectionUtils.isEmpty(admins)||admins.size()!=1){
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

        TAdminExample example=null;
        //要是有筛选条件
        if(!StringUtils.isEmpty(condition)){
            example=new TAdminExample();
            example.createCriteria().andLoginacctLike("%"+condition+"%");
            TAdminExample.Criteria criteria = example.createCriteria().andUserpswdEqualTo("%" +condition+ "%");
            TAdminExample.Criteria criteria1 = example.createCriteria().andEmailLike("%" +condition+ "%");
            example.or(criteria);
            example.or(criteria1);
        }
		//要是没有筛选条件，就查询所有
		List<TAdmin> admins = adminMapper.selectByExample(example);

		return admins;
	}

    /**
     * 根据ID删除admin
     * @param id
     */
    @Override
    public void deleteAdminById(Integer id) {
        adminMapper.deleteByPrimaryKey(id);
    }


}
