package com.example.korol.onechatapp.logic.vk.entities;

import com.example.korol.onechatapp.logic.common.IMessage;
import com.example.korol.onechatapp.logic.common.ISender;

import java.util.Date;

public class VkMessage implements IMessage {
    private ISender sender;

    @Override
    public ISender getSender() {
        return sender;
    }

    private String text;

    @Override
    public String getText() {
        if (text != null)
            return text;
        else
            return "Some group";
    }

    private Date date;

    @Override
    public Date getDate() {
        return date;
    }

    private VkMessage(ISender sender, String text, Date date, boolean isRead) {
        this.sender = sender;
        this.text = text;
        this.date = date;
        this.isRead = isRead;
    }

    private VkMessage() {
    }

    public boolean isRead() {
        return isRead;
    }

    public IMessage read() {
        isRead = true;
        return this;
    }

    private boolean isRead = false;

    public static class Builder {

        private Date date;
        private String text;
        private ISender sender;
        private boolean read;

        public Builder setRead(boolean read) {
            this.read = read;
            return this;
        }

        public Builder setText(String text) {
            if (text != null)
                this.text = text;
            else
                this.text = "";
            return this;
        }

        public Builder setSender(ISender sender) {
            this.sender = sender;
            return this;
        }

        public Builder setDate(Date date) {
            this.date = date;
            return this;
        }

        public VkMessage build() {
            return new VkMessage(sender, text, date, read);
        }
    }
}