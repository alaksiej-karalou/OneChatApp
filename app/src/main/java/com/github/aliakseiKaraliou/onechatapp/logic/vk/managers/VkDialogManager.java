package com.github.aliakseiKaraliou.onechatapp.logic.vk.managers;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.util.LongSparseArray;
import android.util.Pair;

import com.github.aliakseiKaraliou.onechatapp.logic.common.IMessage;
import com.github.aliakseiKaraliou.onechatapp.logic.common.IReciever;
import com.github.aliakseiKaraliou.onechatapp.logic.utils.asyncOperation.AsyncOperation;
import com.github.aliakseiKaraliou.onechatapp.logic.vk.VkConstants;
import com.github.aliakseiKaraliou.onechatapp.logic.vk.VkReceiverStorage;
import com.github.aliakseiKaraliou.onechatapp.logic.vk.VkRequester;
import com.github.aliakseiKaraliou.onechatapp.logic.vk.parsers.VkDialogFinalParser;
import com.github.aliakseiKaraliou.onechatapp.logic.vk.parsers.VkDialogStartParser;
import com.github.aliakseiKaraliou.onechatapp.logic.vk.parsers.VkReceiverDataParser;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public final class VkDialogManager {
    private AsyncOperation<Integer, List<IMessage>> asyncOperation;

    public void startLoading(final Context context, final IReciever reciever, final int offset) {
        if (asyncOperation == null) {
            asyncOperation = new AsyncOperation<Integer, List<IMessage>>() {
                @Override
                protected List<IMessage> doInBackground(Integer integer) {
                    try {
                        String id = Long.toString(reciever.getId());
                        //TODO to delete
                        //id="2000000016";
                        Pair<String, String> peerId = new Pair<>("peer_id", id);
                        final String json;
                        if (offset > 0) {
                            Pair<String, String> offsetPair = new Pair<>("offset", Integer.toString(offset));
                            json = new VkRequester().doRequest(VkConstants.Method.MESSAGES_GETHISTORY, peerId, offsetPair);
                        } else {
                            json = new VkRequester().doRequest(VkConstants.Method.MESSAGES_GETHISTORY, peerId);
                        }

                        final Set<Long> parse = new VkDialogStartParser().parse(json);
                        final LongSparseArray<IReciever> longSparseArray = new VkReceiverDataParser().parse(parse);
                        VkReceiverStorage.putAll(longSparseArray);
                        return new VkDialogFinalParser().parse(context, json, longSparseArray);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            }.startLoading(offset);
        }
    }

    @Nullable
    public List<IMessage> getResult() {
        if (asyncOperation != null) {
            final List<IMessage> result = asyncOperation.getResult();
            asyncOperation = null;
            return result;
        }
        return null;
    }
}
