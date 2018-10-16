package com.parse.starter.adapters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseUser;
import com.parse.starter.ViewRiderLocation;
import com.parse.starter.mUtil.ImageLoader;
import com.parse.starter.SingleItemView;
import com.parse.starter.models.vrData;

public class ListViewAdapter extends BaseAdapter {
	String them;
	String me;
	// Declare Variables
	Context context;
	LayoutInflater inflater;
	ImageLoader imageLoader;
	private List<vrData> openRequestslist = null;
	private ArrayList<vrData> arraylist;

	public ListViewAdapter(Context context,
			List<vrData> openRequestslist) {
		this.context = context;
		this.openRequestslist = openRequestslist;
		inflater = LayoutInflater.from(context);
		this.arraylist = new ArrayList<vrData>();
		this.arraylist.addAll(openRequestslist);
		imageLoader = new ImageLoader(context);
	}

	public class ViewHolder {
		TextView username;
		TextView message;
		TextView location;
		ImageView imageurl;
	}

	@Override
	public int getCount() {
		return openRequestslist.size();
	}

	@Override
	public Object getItem(int position) {
		return openRequestslist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;}

	public View getView(final int position, View view, ViewGroup parent) {
		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.listview_item, null);
			// Locate the TextViews in listview_item.xml
			holder.username = (TextView) view.findViewById(R.id.username);
			holder.message = (TextView) view.findViewById(R.id.message);
			//holder.population = (TextView) view.findViewById(R.id.population);
			// Locate the ImageView in listview_item.xml
			holder.imageurl = (ImageView) view.findViewById(R.id.image);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		// Set the results into TextViews
		holder.username.setText("UserName is " +openRequestslist.get(position).getusername());
		holder.message.setText("Request "+openRequestslist.get(position).getMessage()+" Distance from you "+openRequestslist.get(position).getParseGeo());
		// Set the results into ImageView
		imageLoader.DisplayImage(openRequestslist.get(position).getImageUrl(),
				holder.imageurl);
		// Listen for ListView Item Click
		/*view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String me=ParseUser.getCurrentUser().getUsername();
				String them=openRequestslist.get(position).getusername();
				Log.i("them",openRequestslist.get(position).getusername());
				Log.i("me",me);
				Intent intent;
				// Send single item click data to SingleItemView Class
				if(!String.valueOf(me).equals(them)){
				redirect(position);}
				if(String.valueOf(them).equals(me)){otherRedirect(position);}
					//intent = new Intent(context, SingleItemView.class);
					// Pass all data rank
				}
});*/
		return view;}


public void redirect(final int position){
Intent intent=new Intent(context,ViewRiderLocation.class);
	intent.putExtra("rank",
			(openRequestslist.get(position).getusername()));
	// Pass all data country
	intent.putExtra("country",
			(openRequestslist.get(position).getMessage()));
	// Pass all data population
	intent.putExtra("timestamp",
			(openRequestslist.get(position).getTimeStamp()));
	// Pass all data flag
	intent.putExtra("flag",
			(openRequestslist.get(position).getImageUrl()));
	// Start SingleItemView Class
	intent.putExtra("longitude",  openRequestslist.get(position).getOtherLong());
	intent.putExtra("latitude", openRequestslist.get(position).getOtherLat());
	intent.putExtra("userLongitude",  openRequestslist.get(position).getMyLong());
	intent.putExtra("userLatitude",  openRequestslist.get(position).getMyLat());
	intent.putExtra("objid",  openRequestslist.get(position).getobjID());
	intent.putExtra("username",
			(openRequestslist.get(position).getusername()));
	intent.putExtra("type",
			(openRequestslist.get(position).getType()));
	context.startActivity(intent);

}
public void otherRedirect(final int position){
	Intent intent=new Intent(context,SingleItemView.class);
	intent.putExtra("objid",openRequestslist.get(position).getobjID());
	Log.i(openRequestslist.get(position).getobjID(),openRequestslist.get(position).getobjID());
	intent.putExtra("rank",
			(openRequestslist.get(position).getusername()));
	// Pass all data country
	intent.putExtra("country",
			(openRequestslist.get(position).getMessage()));
	// Pass all data population
	intent.putExtra("timestamp",
			(openRequestslist.get(position).getTimeStamp()));
	// Pass all data flag
	intent.putExtra("flag",
			(openRequestslist.get(position).getImageUrl()));
	// Start SingleItemView Class
	intent.putExtra("longitude",  openRequestslist.get(position).getOtherLong());
	intent.putExtra("latitude", openRequestslist.get(position).getOtherLat());
	intent.putExtra("userLongitude",  openRequestslist.get(position).getMyLong());
	intent.putExtra("userLatitude",  openRequestslist.get(position).getMyLat());
	intent.putExtra("username",
			(openRequestslist.get(position).getusername()));
	intent.putExtra("type",
			(openRequestslist.get(position).getType()));
	context.startActivity(intent);
}
}
