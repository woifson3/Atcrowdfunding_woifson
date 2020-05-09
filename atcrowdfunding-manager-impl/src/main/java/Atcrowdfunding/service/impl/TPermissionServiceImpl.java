package Atcrowdfunding.service.impl;

import Atcrowdfunding.bean.TPermission;
import Atcrowdfunding.mapper.TPermissionMapper;
import Atcrowdfunding.service.TPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TPermissionServiceImpl implements TPermissionService {
    @Autowired
    TPermissionMapper permissionMapper;

    @Override
    public List<Integer> getHavePermissions(Integer id) {
       List<Integer> perIds = permissionMapper.selectHaveByIds(id);
        return perIds;
    }

    @Override
    public List<TPermission> getListPerisson() {
        List<TPermission> tPermissions = permissionMapper.selectByExample(null);
        return tPermissions;
    }
}
