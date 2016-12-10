package com.github.aliakseiKaraliou.onechatapp.logic.db;

import com.github.aliakseiKaraliou.onechatapp.logic.db.annotations.DbColumnName;
import com.github.aliakseiKaraliou.onechatapp.logic.db.annotations.DbPrimaryKey;
import com.github.aliakseiKaraliou.onechatapp.logic.db.annotations.DbTableName;
import com.github.aliakseiKaraliou.onechatapp.logic.db.annotations.DbType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class QueryGenerator {

    private static final String CREATE_IF_NOT_EXISTS_QUERY = "CREATE TABLE IF NOT EXISTS '%s' (%s, PRIMARY KEY(%s))";
    private static final String SELECT_QUERY = "SELECT * FROM '%s'";
    private static final String DROP_QUERY = "DROP TABLE IF EXISTS '%s'";

    public String getCreateTableQuery(Class<?> clazz) {

        //get all colums
        final Field[] declaredFields = clazz.getDeclaredFields();
        List<Field> columns = new ArrayList<>();
        for (Field field : declaredFields) {
            if (field.getAnnotation(DbType.class) != null) {
                columns.add(field);
            }
        }

        StringBuilder builder = new StringBuilder();

        for (Field column : columns) {
            builder.append(column.getAnnotation(DbColumnName.class).name()).append(' ').append(column.getAnnotation(DbType.class).type()).append(",");
        }

        StringBuilder primaryKeyBuilder = new StringBuilder();
        for (Field column : columns) {
            if (column.getAnnotation(DbPrimaryKey.class) != null) {
                primaryKeyBuilder.append(column.getAnnotation(DbColumnName.class).name()).append(",");
            }
        }

        final String format = String.format(Locale.US, CREATE_IF_NOT_EXISTS_QUERY, clazz.getAnnotation(DbTableName.class).name(), builder.substring(0, builder.length() - 1), primaryKeyBuilder.substring(0, primaryKeyBuilder.length() - 1));

        return format;
    }

    public String getSelectAllQuery(String name) {
        return String.format(Locale.US, SELECT_QUERY, name);
    }

    public String getDropQuery(String name) {
        return String.format(Locale.US, DROP_QUERY, name);
    }

}
