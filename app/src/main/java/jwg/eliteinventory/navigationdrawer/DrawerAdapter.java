package jwg.eliteinventory.navigationdrawer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import jwg.eliteinventory.R;

/** Created by John **/
public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ViewHolder> {

    /**
     * TYPE_HEADER refers to the Header view.
     * TYPE_ITEM refers to all of the items in the drawer
     */
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    /**
     * @param mNavTitles String Array to store the passed titles Value from MainActivity.java
     * @param mIcons Int Array to store the passed icons resource value from MainActivity.java
     * @param name String Resource for header View Name
     * @param profile int Resource for header view profile picture
     * @param email String Resource for header view email
     */
    private String mNavTitles[];
    private int mIcons[];
    private String name;
    private int profile;
    private String email;
    Context context;

    /**
     * The ViewHolder is used to store the inflated layout.xml files to
     * then be able to recycle them when drawer is not apparent
     */

    public static class ViewHolder extends RecyclerView.ViewHolder {
        int Holderid;
        TextView textView;
        ImageView imageView;
        ImageView profile;
        TextView Name;
        TextView email;
        Context contxt;

        public ViewHolder(View itemView,int ViewType,Context c) {
            super(itemView);
            contxt = c;
            itemView.setClickable(true);

            /** This is where I am assigning the data to the holders to then be displayed **/
            if(ViewType == TYPE_ITEM) {
                textView = (TextView) itemView.findViewById(R.id.rowText);
                imageView = (ImageView) itemView.findViewById(R.id.rowIcon);
                Holderid = 1;
            }
            else{
                Name = (TextView) itemView.findViewById(R.id.header_name);
                email = (TextView) itemView.findViewById(R.id.header_email);
                profile = (ImageView) itemView.findViewById(R.id.header_imageview);
                Holderid = 0;
            }
        }
    }

    /**
     * @param Titles Passing the Titles of each row into navigation drawer
     * @param Icons The icons next to the titles.
     * @param Name Name that is displayed in the header of the navigation drawer
     * @param Email Email that is displayed in the header of the navigation drawer
     * @param Profile This is the profile picture that is in the header. In my case the App Icon
     * @param passedContext The current context
     */
    public DrawerAdapter(String Titles[], int Icons[], String Name, String Email, int Profile, Context passedContext){
        mNavTitles = Titles;
        mIcons = Icons;
        name = Name;
        email = Email;
        profile = Profile;
        this.context = passedContext;
    }

    /** Does the actual inflating of the layouts **/
    @Override
    public DrawerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row,parent,false);
            ViewHolder vhItem = new ViewHolder(v,viewType,context);
            return vhItem;
        }
        else if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header,parent,false);
            ViewHolder vhHeader = new ViewHolder(v,viewType,context);
            return vhHeader;
        }
        return null;
    }

    /** This is where our titles and images get placed on the navigation drawer
     * Header holder is id 0
     * Item holder is id 1
     * **/
    @Override
    public void onBindViewHolder(DrawerAdapter.ViewHolder holder, int position) {
        if(holder.Holderid ==1) {
            holder.textView.setText(mNavTitles[position - 1]);
            holder.imageView.setImageResource(mIcons[position -1]);
        }
        else{
            holder.profile.setImageResource(profile);
            holder.Name.setText(name);
            holder.email.setText(email);
        }
    }

    /** returns the number of items present in the list plus the header view **/
    @Override
    public int getItemCount() {
        return mNavTitles.length+1;
    }

    /** Positioning of the header and item view**/
    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }
}