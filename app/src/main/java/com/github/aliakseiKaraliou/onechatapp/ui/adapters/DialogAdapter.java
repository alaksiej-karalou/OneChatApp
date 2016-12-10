package com.github.aliakseiKaraliou.onechatapp.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aliakseiKaraliou.onechatapp.App;
import com.github.aliakseiKaraliou.onechatapp.R;
import com.github.aliakseiKaraliou.onechatapp.logic.common.IMessage;
import com.github.aliakseiKaraliou.onechatapp.logic.utils.imageLoader.LazyImageLoaderManager;

import java.util.List;

public class DialogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<IMessage> messageList;
    private Context context;
    private Bitmap defaultBitmap;

    public DialogAdapter(Context context, List<IMessage> messageList) {
        this.context = context;
        this.messageList = messageList;
        this.defaultBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.camera_50);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_item, parent, false);
        return new DialogAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final IMessage currentMessage = messageList.get(position);
        final LazyImageLoaderManager loaderManager = ((App) context.getApplicationContext()).getImageLoaderManager();

        final DialogAdapterViewHolder dialogAdapterViewHolder = (DialogAdapterViewHolder) holder;
        loaderManager.load(context, dialogAdapterViewHolder.photo, currentMessage.getReceiver().getPhotoUrl(), defaultBitmap);
        dialogAdapterViewHolder.messageTextView.setText(currentMessage.getText());
        dialogAdapterViewHolder.photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, currentMessage.getSender().getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    private class DialogAdapterViewHolder extends RecyclerView.ViewHolder {

        private final TextView messageTextView;
        private final ImageView photo;

        public DialogAdapterViewHolder(View itemView) {
            super(itemView);
            messageTextView = (TextView) itemView.findViewById(R.id.dialog_item_message);
            photo = (ImageView) itemView.findViewById(R.id.dialog_item_primary_photo);
        }
    }
}
