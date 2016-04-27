package com.flirtchat.app.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.flirtchat.app.R;
import com.flirtchat.app.models.MessageDataModel;

import java.util.ArrayList;
/**
 * Created by Shivam on 4/22/2016.
 */
public class ChatAdapter extends BaseAdapter {

    private ArrayList<MessageDataModel> messages;
    private Context context;
    private LayoutInflater layoutInflater;

    public ChatAdapter(Context context, ArrayList<MessageDataModel> messages) {
        this.context = context;
        this.messages = messages;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MessageDataModel msg = messages.get(position);

        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.chat_row, null);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            viewHolder.tvMsg = (TextView) convertView.findViewById(R.id.tvMsg);
            viewHolder.lLayout = (LinearLayout) convertView.findViewById(R.id.lLayout);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        LayoutParams lp1 = (LayoutParams) viewHolder.tvName.getLayoutParams();
        LayoutParams lp2 = (LayoutParams) viewHolder.tvMsg.getLayoutParams();

        if (msg.getName().equals("Shivaraj")) {
            viewHolder.lLayout.setBackgroundResource(R.drawable.speech_bubble_green);
            lp1.gravity = Gravity.END;
            lp2.gravity = Gravity.END;
        } else {
            viewHolder.lLayout.setBackgroundResource(R.drawable.speech_bubble_orange);
            lp1.gravity = Gravity.START;
            lp2.gravity = Gravity.START;
        }

        viewHolder.tvName.setText(messages.get(position).getName());
        viewHolder.tvMsg.setText(messages.get(position).getMessage());
        return convertView;
    }

    public class ViewHolder {

        TextView tvName, tvMsg;
        LinearLayout lLayout;

        public ViewHolder() {

        }
    }
}
