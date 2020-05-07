package Atcrowdfunding.service.impl;

import Atcrowdfunding.bean.TRole;
import Atcrowdfunding.bean.TRoleExample;
import Atcrowdfunding.controller.TRoleService;
import Atcrowdfunding.mapper.TRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class TRoleServiceImpl implements TRoleService {
    @Autowired
    TRoleMapper troleMap;

    /**
     * 点击角色维护按钮呈现的异步页面数据
     *
     * @return
     */
    @Override
    public List<TRole> listRole(String condition) {
        TRoleExample e = null;
        if (!StringUtils.isEmpty(condition)) {
            e = new TRoleExample();
            e.createCriteria().andNameLike("%" + condition + "%");
        }
        return troleMap.selectByExample(e);
    }
}
