package jwg.eliteinventory.mainactivities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jwg.eliteinventory.R;

/** Created by John **/
public class CustomListAdapter extends ArrayAdapter<ListItems> {

    private ArrayList<ListItems> listData;
    private LayoutInflater layoutInflater;

    public CustomListAdapter(Context aContext, ArrayList<ListItems> listData) {
        super(aContext,0, listData);
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public ListItems getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();

            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            holder.barcodeView = (TextView) convertView.findViewById(R.id.barcode);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.imageView.setImageResource(listData.get(position).getImgId());
        holder.barcodeView.setText(listData.get(position).getBarcode());

        return convertView;
    }

    public void remove(int positionToRemove) {
        listData.remove(positionToRemove);
    }

    static class ViewHolder {
        ImageView imageView;
        TextView barcodeView;

    }
}
