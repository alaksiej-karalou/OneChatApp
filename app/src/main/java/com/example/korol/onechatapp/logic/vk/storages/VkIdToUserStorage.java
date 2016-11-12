package com.example.korol.onechatapp.logic.vk.storages;

import com.example.korol.onechatapp.logic.common.IUser;
import com.example.korol.onechatapp.logic.vk.json.VkBasicUserJsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public abstract class VkIdToUserStorage {

    private static Map<Long, IUser> idToUserStorage = new HashMap<>();

    public static IUser getUser(Long id) {
        if (idToUserStorage.containsKey(id))
            return idToUserStorage.get(id);
        else {
            IUser user = null;
            try {
                user = new VkBasicUserJsonParser().execute(id.toString()).get(0);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            idToUserStorage.put(id, user);
            return user;
        }
    }

    public static Map<Long, IUser> getUsers(Long... ids) {
        Map<Long, IUser> result = new HashMap<>();

        List<Long> requestId = new ArrayList<>();

        for (long id : ids) {
            if (!idToUserStorage.containsKey(id))
                requestId.add(id);
        }
        if (requestId.size()>0) {
            StringBuilder builder = new StringBuilder();

            // add new users
            for (long id : requestId)
                if (!idToUserStorage.containsKey(id) && id > 0)
                    builder.append(id).append(",");
            String params = builder.toString();
            List<IUser> userList = null;
            try {
                userList = new VkBasicUserJsonParser().execute(params);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            for (IUser user : userList) {
                idToUserStorage.put(user.getId(), user);
            }
        }
        for (long id : ids)
            if (id > 0)
                result.put(id, idToUserStorage.get(id));
        return result;
    }

    public static Map<Long, IUser> getUsers(List<Long> ids) {
        return getUsers(ids.toArray(new Long[0]));
    }

    public static boolean contains(long id) {
        return idToUserStorage.containsKey(id);
    }
}
