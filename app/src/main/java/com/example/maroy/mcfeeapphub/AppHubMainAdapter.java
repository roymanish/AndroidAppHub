package com.example.maroy.mcfeeapphub;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.maroy.data.AppData;

import java.util.List;

/**
 * Created by MaRoy on 4/18/2015.
 */
public class AppHubMainAdapter extends ArrayAdapter<AppData>{

    // List context
    private final Context context;
    // List values
    private final List<AppData> appDataList;

    static class ViewHolder{
        public TextView appNameView;
        public TextView appPriceView;
    }

    public AppHubMainAdapter(Context context, List<AppData> appDataList) {
        super(context, R.layout.app_data_list_item, appDataList);
        this.context = context;
        this.appDataList = appDataList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        if(rowView == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            rowView = inflater.inflate(R.layout.app_data_list_item, parent, false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.appNameView = (TextView) rowView.findViewById(R.id.appNameView);
            viewHolder.appPriceView = (TextView) rowView.findViewById(R.id.appPriceView);
            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder)rowView.getTag();

        AppData appData = this.getAppDataList().get(position);

        holder.appNameView.setText(appData.getName());
        holder.appPriceView.setText("$"+appData.getPrice());
        return rowView;
    }

    public List<AppData> getAppDataList() {
        return appDataList;
    }
}
