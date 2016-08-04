package com.zulip.android.viewholders;

import android.support.annotation.ColorInt;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zulip.android.models.MessageType;
import com.zulip.android.models.Person;
import com.zulip.android.R;
import com.zulip.android.ZulipApp;
import com.zulip.android.util.OnItemClickListener;
import com.zulip.android.util.ZLog;

public class MessageHeaderParent {
    public String stream;
    public String subject;
    public String id;
    private boolean isMute;
    private MessageType messageType;
    private String displayRecipent;

    public MessageHeaderParent(String stream, String subject, String id) {
        this.stream = stream;
        this.subject = subject;
        this.id = id;
    }


    @ColorInt
    public int color;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isMute() {
        return isMute;
    }

    public void setMute(boolean mute) {
        isMute = mute;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getDisplayRecipent() {
        return displayRecipent;
    }

    public void setDisplayRecipent(String displayRecipent) {
        this.displayRecipent = displayRecipent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStream() {
        return stream;
    }

    public String getSubject() {
        return subject;
    }

    public Person[] getRecipients(ZulipApp app) {
        Person[] recipientsCache;
        String[] ids = TextUtils.split(this.getDisplayRecipent(), ",");
        recipientsCache = new Person[ids.length];
        for (int i = 0; i < ids.length; i++) {
            try {
                recipientsCache[i] = Person.getById(app,
                        Integer.parseInt(ids[i]));
            } catch (NumberFormatException e) {
                ZLog.logException(e);
            }
        }
        return recipientsCache;
    }
    public static class MessageHeaderHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView streamTextView;
        public TextView topicTextView;
        public ImageView muteMessageImage;
        public TextView arrowHead;
        private OnItemClickListener onItemClickListener;

        public MessageHeaderHolder(View itemView) {
            super(itemView);
            streamTextView = (TextView) itemView.findViewById(R.id.displayRecipient);
            topicTextView = (TextView) itemView.findViewById(R.id.instance);
            arrowHead = (TextView) itemView.findViewById(R.id.sep);
            muteMessageImage = (ImageView) itemView.findViewById(R.id.muteMessageImage);
            streamTextView.setOnClickListener(this);
            topicTextView.setOnClickListener(this);
        }


        public void setOnItemClickListener(OnItemClickListener contentHolder) {
            this.onItemClickListener = contentHolder;
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v.getId(), getAdapterPosition());
        }
    }
}
