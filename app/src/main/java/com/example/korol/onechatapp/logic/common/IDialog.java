package com.example.korol.onechatapp.logic.common;

import java.util.List;

public interface IDialog {
    ISender getInterlocutor();

    void setInterlocutor(ISender interlocutor);

    List<IMessage> getMessages();

    void add(IMessage message);
}