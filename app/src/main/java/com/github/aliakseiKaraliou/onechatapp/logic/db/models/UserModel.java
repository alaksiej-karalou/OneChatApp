package com.github.aliakseiKaraliou.onechatapp.logic.db.models;

import android.content.ContentValues;
import android.database.Cursor;

import com.github.aliakseiKaraliou.onechatapp.logic.common.IUser;
import com.github.aliakseiKaraliou.onechatapp.logic.common.enums.SocialNetwork;
import com.github.aliakseiKaraliou.onechatapp.logic.db.annotations.DbColumnName;
import com.github.aliakseiKaraliou.onechatapp.logic.db.annotations.DbPrimaryKey;
import com.github.aliakseiKaraliou.onechatapp.logic.db.annotations.DbTableName;
import com.github.aliakseiKaraliou.onechatapp.logic.db.annotations.DbType;
import com.github.aliakseiKaraliou.onechatapp.logic.vk.Constants;
import com.github.aliakseiKaraliou.onechatapp.logic.vk.models.VkUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@DbTableName(name = Constants.Db.USERS)
public final class UserModel implements AbstractModel<UserModel> {

    private static final String UID = "uid";
    private static final String FIRST_NAME = "firstname";
    private static final String LAST_NAME = "lastname";
    private static final String PHOTO50 = "photo50";
    private static final String PHOTO100 = "photo100";
    private static final String SOCIAL_NETWORK = "social_network";

    @DbType(type = DbType.Type.INTEGER)
    @DbPrimaryKey
    @DbColumnName(name = UID)
    private long id;

    @DbType(type = DbType.Type.TEXT)
    @DbColumnName(name = FIRST_NAME)
    private String firstName;

    @DbType(type = DbType.Type.TEXT)
    @DbColumnName(name = LAST_NAME)
    private String lastName;

    @DbType(type = DbType.Type.TEXT)
    @DbColumnName(name = PHOTO50)
    private String photo50Url;

    @DbType(type = DbType.Type.TEXT)
    @DbColumnName(name = PHOTO100)
    private String photo100Url;

    @DbType(type = DbType.Type.TEXT)
    @DbColumnName(name = SOCIAL_NETWORK)
    @DbPrimaryKey
    private String socialNetwork;

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoto50Url() {
        return photo50Url;
    }

    public String getSocialNetwork() {
        return socialNetwork;
    }

    @Override
    public ContentValues convertToContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UID, id);
        contentValues.put(FIRST_NAME, firstName);
        contentValues.put(LAST_NAME, lastName);
        contentValues.put(PHOTO50, photo50Url);
        contentValues.put(PHOTO100, photo100Url);
        contentValues.put(SOCIAL_NETWORK, socialNetwork);
        return contentValues;
    }


    @Override
    public UserModel convertToModel(Cursor cursor) {
        String firstName = cursor.getString(0);
        long uid = cursor.getLong(1);
        String lastname = cursor.getString(2);
        String photo100 = cursor.getString(3);
        String photo50 = cursor.getString(4);
        String socialNetwork = cursor.getString(5);
        return new UserModel(uid, firstName, lastname, photo50, photo100, SocialNetwork.valueOf(socialNetwork));
    }

    private UserModel(long id, String firstName, String lastName, String photo50Url, String photo100Url, SocialNetwork socialNetwork) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.photo50Url = photo50Url;
        this.photo100Url = photo100Url;
        this.socialNetwork = socialNetwork.toString();
    }

    private UserModel(IUser user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.photo50Url = user.getPhoto50Url();
        this.photo100Url = user.getPhoto100Url();
        this.socialNetwork = user.getSocialNetwork().toString();
    }

    private UserModel() {
    }

    public static UserModel getInstance(){
        return new UserModel();
    }

    public static List<UserModel> convertTo(Collection<IUser> users) {
        List<UserModel> userModelList = new ArrayList<>();
        for (IUser user : users) {
            userModelList.add(new UserModel(user));
        }
        return userModelList;
    }

    public static List<IUser> convertFrom(Collection<UserModel> userModels) {
        List<IUser> userList = new ArrayList<>();
        IUser user;
        for (UserModel userModel : userModels) {
            user = new VkUser(userModel.id, userModel.firstName, userModel.lastName, userModel.photo50Url, userModel.photo100Url);
            userList.add(user);
        }
        return userList;
    }
}
