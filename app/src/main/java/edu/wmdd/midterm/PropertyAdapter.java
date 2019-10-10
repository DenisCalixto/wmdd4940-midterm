package edu.wmdd.midterm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//import com.bumptech.glide.Glide;
//import com.bumptech.glide.request.RequestOptions;

public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.ViewHolder> {

    private Context context;
    private List<Property> list;

    public PropertyAdapter(Context context, List<Property> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Property property = list.get(position);

        holder.textName.setText(property.getName());
        holder.textId.setText(String.valueOf(property.getId()));

//        String url = String.valueOf(property.getImage_url());
//        ImageLoader imageLoader;
//
//        imageLoader = CustomVolleyRequest.getInstance(this.context)
//                .getImageLoader();
//        imageLoader.get(url, ImageLoader.getImageListener(holder.imageThumbnail,
//                R.drawable.loading_shape, android.R.drawable
//                        .ic_dialog_alert));
//        holder.imageThumbnail.setImageUrl(url, imageLoader);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textName, textRating, textId;
//        public NetworkImageView imageThumbnail;

        public ViewHolder(View itemView) {
            super(itemView);

            textName = itemView.findViewById(R.id.main_name);
            textId = itemView.findViewById(R.id.main_id);
//            imageThumbnail = itemView.findViewById(R.id.thumbnail);
        }
    }

}