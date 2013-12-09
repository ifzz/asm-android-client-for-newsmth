package com.zfdang.asm.Adapter;

import java.io.File;

import org.apache.commons.io.FileUtils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.zfdang.asm.AttachUploadActivity;
import com.zfdang.asm.R;

public class AttachListAdapter extends BaseAdapter implements OnClickListener {

    private AttachUploadActivity activity;
    private LayoutInflater inflater;

    public AttachListAdapter(AttachUploadActivity activity, LayoutInflater inflater) {
        this.activity = activity;
        this.inflater = inflater;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View layout = null;
        if (convertView != null) {
            layout = convertView;
        } else {
            layout = inflater.inflate(R.layout.attach_list_item, null);
        }

        File file = activity.m_attachArrayList.get(position);
        TextView titleTextView = (TextView) layout.findViewById(R.id.attach_title);
        titleTextView.setText(file.getName());

        TextView sizeTextView = (TextView) layout.findViewById(R.id.attach_size);
        sizeTextView.setText("文件大小:" + FileUtils.byteCountToDisplaySize(file.length()));

        Button deleteButton = (Button) layout.findViewById(R.id.btn_delete_attach);
        deleteButton.setOnClickListener(this);
        deleteButton.setTag(position);

        return layout;
    }

    @Override
    public int getCount() {
        return activity.m_attachArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return activity.m_attachArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        activity.m_attachArrayList.remove(position);

        this.notifyDataSetChanged();
    }
}