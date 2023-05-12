package com.test.testcollapseandexpand;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

	private ArrayList<LocationObj> notesModalArrayList;
	private Context context;

	public LocationAdapter(ArrayList<LocationObj> notesModalArrayList, Context context) {
		this.notesModalArrayList = notesModalArrayList;
		this.context = context;
	}

	@SuppressLint("MissingInflatedId")
	@NonNull
	@Override
	public LocationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_locationslist, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull LocationAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {


		LocationObj modal = notesModalArrayList.get(position);
		holder.loc_name.setText(modal.getName());
		holder.iv_image.setImageResource(modal.getImage());



	}

	@Override
	public int getItemCount() {
		return notesModalArrayList.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {


		ImageView iv_image;
		TextView loc_name;

		public ViewHolder(@NonNull View itemView) {
			super(itemView);

			iv_image = itemView.findViewById(R.id.iv_image);
			loc_name = itemView.findViewById(R.id.loc_name);

		}
	}
}
