package com.punuo.sys.app.Manager;

import com.punuo.sys.app.i.IManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Author chenhan
 * Date 2017/8/30
 */

public class ManagerFather {
    HashMap<String, ArrayList<IManager>> managerMap = new HashMap<>();
    static ManagerFather instance;
    private String mCurrentGroup;

    public static ManagerFather getInstance() {
        if (instance == null) {
            instance = new ManagerFather();
        }
        return instance;
    }

    public void setCurrentGroup(String mCurrentGroup) {
        this.mCurrentGroup = mCurrentGroup;
    }

    public void addManager(IManager iManager) {
        if (managerMap.containsKey(mCurrentGroup)) {
            managerMap.get(mCurrentGroup).add(iManager);
        } else {
            ArrayList<IManager> iManagers = new ArrayList<>();
            iManagers.add(iManager);
            managerMap.put(mCurrentGroup, iManagers);
        }
    }

    public void destroy(String group) {
        List<IManager> managerList = managerMap.get(group);
        for (int i = 0; i < managerList.size(); i++) {
            IManager iManager = managerList.get(i);
            iManager.destroy();
        }
        managerMap.remove(group);
    }

}
