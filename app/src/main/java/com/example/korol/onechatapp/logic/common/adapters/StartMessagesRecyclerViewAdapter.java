package com.example.korol.onechatapp.logic.common.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.korol.onechatapp.R;
import com.example.korol.onechatapp.logic.common.IMessage;
import com.example.korol.onechatapp.logic.common.ISender;
import com.example.korol.onechatapp.logic.utils.imageLoader.ImageLoader;

import java.util.List;

public class StartMessagesRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public void setOnMessageClick(OnMessageClick onMessageClick) {
        this.onMessageClick = onMessageClick;
    }

    private OnMessageClick onMessageClick;

    public StartMessagesRecyclerViewAdapter(List<IMessage> messageList) {
        this.messageList = messageList;
    }

    private List<IMessage> messageList;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.start_screen_message_item, parent, false);
        return new StartMessagesRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final IMessage currentMessage = messageList.get(position);

        ImageLoader loader = new ImageLoader();

        StartMessagesRecyclerViewHolder viewHolder = ((StartMessagesRecyclerViewHolder) holder);

        viewHolder.avatarImageView.setImageBitmap(loader.getBitmapFromUrl(currentMessage.getSender().getPhotoUrl()));
        viewHolder.messageTextView.setText(currentMessage.getText());
        viewHolder.nameTextView.setText(currentMessage.getSender().getName());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMessageClick.onClick(currentMessage.getSender());
            }
        });
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }



    private class StartMessagesRecyclerViewHolder extends RecyclerView.ViewHolder {

        private final TextView nameTextView;
        private final TextView messageTextView;
        private final ImageView avatarImageView;

        public StartMessagesRecyclerViewHolder(View itemView) {
            super(itemView);
            nameTextView = ((TextView) itemView.findViewById(R.id.start_screen_message_name));
            messageTextView = ((TextView) itemView.findViewById(R.id.start_screen_message_message));
            avatarImageView = ((ImageView) itemView.findViewById(R.id.start_screen_message_avatar));
        }
    }

    public interface OnMessageClick {
        public void onClick(ISender sender);
    }
}
