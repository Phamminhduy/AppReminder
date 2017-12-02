package com.example.android.remider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by phamm on 11/12/2017.
 */

public class CustomAdapter extends BaseAdapter {
    Context context;
    int layout;
    ArrayList<Reminder> list;
    private ColorGenerator mColorGenerator = ColorGenerator.DEFAULT;
    private TextDrawable mDrawableBuilder;
    public CustomAdapter(Context context, int layout, ArrayList<Reminder> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            viewHolder = new ViewHolder();
            viewHolder.imgRepeat = (ImageView) convertView.findViewById(R.id.active_image);
            viewHolder.ImgTitel = (ImageView) convertView.findViewById(R.id.thumbnail_image);
            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.title);
            viewHolder.txtDateAndTime = (TextView)convertView.findViewById(R.id.date_time);
            viewHolder.txtRepeat = (TextView)convertView.findViewById(R.id.repeat_info);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
            Reminder reminder = list.get(position);
        /////
            viewHolder.txtTitle.setText(reminder.getTitle());
            ///set ImageView Title
            String letter = "A";
            if(reminder.getTitle() != null && !reminder.getTitle().isEmpty()) {
                letter = reminder.getTitle().substring(0, 1);
            }
            int color = mColorGenerator.getRandomColor();
            // Create a circular icon consisting of  a random background colour and first letter of title
            mDrawableBuilder = TextDrawable.builder()
                    .buildRound(letter, color);
            viewHolder.ImgTitel.setImageDrawable(mDrawableBuilder);
            ////set TextView Repeat
            if(reminder.getRepeat().equals("true")){
                viewHolder.txtRepeat.setText("Every " + reminder.getRepeatNo() + " " + reminder.getRepeatType() + "(s)");
            }else if (reminder.getRepeat().equals("false")) {
                viewHolder.txtRepeat.setText("Repeat Off");
            }
            /////set ImageView Active Alarm
            if(reminder.getActive().equals("true")){
                viewHolder.imgRepeat.setImageResource(R.drawable.ic_notifications_on_white_24dp);
            }else if (reminder.getActive().equals("false")) {
                viewHolder.imgRepeat.setImageResource(R.drawable.ic_notifications_off_grey600_24dp);
            }
            //////
            viewHolder.txtDateAndTime.setText(reminder.getDate() + "  "+ reminder.getTime());

        return convertView;
    }
    public class ViewHolder{
        ImageView ImgTitel,imgRepeat;
        TextView txtTitle,txtDateAndTime,txtRepeat;
    }
}
