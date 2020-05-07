package Atcrowdfunding.service.impl;

import Atcrowdfunding.bean.TCert;
import Atcrowdfunding.bean.TMenu;
import Atcrowdfunding.controller.TMenuService;
import Atcrowdfunding.mapper.TMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class TMenuServiceImpl implements TMenuService {
    @Autowired
    TMenuMapper menuMapper;

    /**
     * 处理首页左边菜单展示
     * 逻辑：数据库中的菜单表是一张自关联表，a.pid=b.id就是b就是a的父菜单
     * 先遍历得到父菜单，然后遍历pid，只要X.pid=某一个id的就是x设置为某一个的子菜单
     *
     * @return
     */
    @Override
    public List<TMenu> listMenus() {
        //1.拿到菜单表里面的所有数据
        List<TMenu> tMenus = menuMapper.selectByExample(null);//拿到所有的TMenu中数据
        //2.找出父菜单
        HashMap<Integer, TMenu> pMenuHash = new HashMap<>();
        //遍历判断数据库中pid为0的行就是父菜单，装入Map中，（id，参数）这个id就是为了让子菜单的pid能够对应找到对应父菜单的
        for (TMenu menu : tMenus) {
            if (menu.getPid() == 0) {
                pMenuHash.put(menu.getId(), menu);
            }
        }
        //3.找出子菜单，并联系上自己的父菜单
        for (TMenu menu : tMenus) {
            //遍历    通过子.pid=父.id  得到子菜单对应的一个个父菜单
            TMenu pMenus = pMenuHash.get(menu.getPid());
            if (pMenus != null) {
                pMenus.getChildren().add(menu);
            }

        }
        return new ArrayList<TMenu>(pMenuHash.values());
    }
}
