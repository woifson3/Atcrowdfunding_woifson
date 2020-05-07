package Atcrowdfunding.controller;

import Atcrowdfunding.bean.TRole;

import java.util.List;

public interface TRoleService {
    List<TRole> listRole(String condition);
}
