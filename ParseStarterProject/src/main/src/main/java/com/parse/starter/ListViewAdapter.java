package com.parse.starter;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {

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
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Send single item click data to SingleItemView Class
				Intent intent = new Intent(context, SingleItemView.class);
				// Pass all data rank
				intent.putExtra("rank",
						(openRequestslist.get(position).getusername()));
				// Pass all data country
				intent.putExtra("country",
						(openRequestslist.get(position).getMessage()));
				// Pass all data population

				// Pass all data flag
				intent.putExtra("flag",
						(openRequestslist.get(position).getImageUrl()));
				// Start SingleItemView Class
				context.startActivity(intent);
			}
		});
		return view;
	}

}