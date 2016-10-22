package com.example.korol.onechatapp.logic.common;

import java.util.Date;

public interface IMessage extends Cloneable {
    ISender getSender();

    String getText();

    Date getDate();

    boolean isRead();

    IMessage read();
}
