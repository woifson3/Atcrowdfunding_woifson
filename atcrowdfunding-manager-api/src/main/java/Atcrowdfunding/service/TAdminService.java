package Atcrowdfunding.service;

import Atcrowdfunding.bean.TAdmin;

import java.util.ArrayList;
import java.util.List;

public interface TAdminService {

	TAdmin doLogin(String loginacct, String userpswd);


    List<TAdmin> listAdmin(String condition);

    void deleteAdminById(Integer id);

    void addAdmin(TAdmin admin);


    TAdmin getAdminById(Integer myId);

    void updateAdminById(TAdmin admin);

    void deleteAdmin(ArrayList<Integer> inte);

    List<Integer> listAdminRoleIds(Integer roleId);
}
