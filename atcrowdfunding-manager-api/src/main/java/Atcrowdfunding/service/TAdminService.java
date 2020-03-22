package Atcrowdfunding.service;

import Atcrowdfunding.bean.TAdmin;

import java.util.List;

public interface TAdminService {

	TAdmin doLogin(String loginacct, String userpswd);


    List<TAdmin> listAdmin(String condition);

    void deleteAdminById(Integer id);
}
