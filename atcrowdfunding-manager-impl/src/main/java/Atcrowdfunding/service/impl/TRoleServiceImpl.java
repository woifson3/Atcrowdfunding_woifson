package Atcrowdfunding.service.impl;

import Atcrowdfunding.bean.TRole;
import Atcrowdfunding.controller.TRoleService;
import Atcrowdfunding.mapper.TRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TRoleServiceImpl implements TRoleService {
    @Autowired
    TRoleMapper troleMap;

    /**
     * 点击角色维护按钮呈现的异步页面数据
     * @return
     */
    @Override
    public List<TRole> listRole() {
        List<TRole> tRoles = troleMap.selectByExample(null);
        return tRoles;
    }
}
