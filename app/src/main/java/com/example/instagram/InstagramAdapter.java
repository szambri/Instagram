package com.example.instagram;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.instagram.models.Post;
import com.parse.ParseFile;

import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class InstagramAdapter extends RecyclerView.Adapter<InstagramAdapter.ViewHolder>{

    private Context context;
    private List<Post> posts;

    public InstagramAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tvUsername;
        private TextView tvUserCap;
        private TextView tvCaption;
        private ImageView ivPhoto;
        private ImageView ivProfilePic;

        public ViewHolder(View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvCaption = itemView.findViewById(R.id.tvCaption);
            tvUserCap = itemView.findViewById(R.id.tvUserCap);
            ivPhoto = itemView.findViewById(R.id.ivPhoto);
            ivProfilePic = itemView.findViewById(R.id.ivProfilePic);
            itemView.setOnClickListener(this);
        }

        public void bind(Post post) {
            //TODO
            tvUserCap.setText(post.getUser().getUsername());
            tvUsername.setText(post.getUser().getUsername());
            ParseFile image = post.getImage();
            ParseFile prof = post.getUser().getParseFile("profilePic");
            if (prof!=null) {
                Glide.with(context)
                        .load(prof.getUrl())
                        .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(100)))
                        .into(ivProfilePic);
            }
            else{
                Glide.with(context)
                        .load(R.drawable.instagram_user_filled_24)
                        .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(100)))
                        .into(ivProfilePic);
            }
            if (image!=null) {
                Glide.with(context)
                        .load(image.getUrl())
                        .apply(new RequestOptions().override(300, 300))
                        .into(ivPhoto);
            }
            tvCaption.setText(post.getCaption());
        }
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Post post = posts.get(position);
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("post", post);
                context.startActivity(intent);
            }

        }

    }

    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }


}
