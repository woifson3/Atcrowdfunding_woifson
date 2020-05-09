package Atcrowdfunding.service;

import Atcrowdfunding.bean.TPermission;

import java.util.List;

public interface TPermissionService {
    public List<Integer> getHavePermissions(Integer id);

    List<TPermission> getListPerisson();
}
