package com.example.instagram;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
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
import com.parse.SaveCallback;

import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class InstagramAdapter extends RecyclerView.Adapter<InstagramAdapter.ViewHolder>{

    private Context context;
    private List<Post> posts;
    private boolean isGrid;

    public InstagramAdapter(Context context, List<Post> posts, boolean isGrid) {
        this.context = context;
        this.posts = posts;
        this.isGrid = isGrid;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        if(isGrid) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_grid, parent, false);
            return new ViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
            return new ViewHolder(view);
        }

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
        private ImageView ivMyPhoto;
        private ImageView ivProfilePic;
        private TextView tvDate;
        private ImageView ivLike;

        public ViewHolder(View itemView) {
            super(itemView);
            if(!isGrid) {
                tvUsername = itemView.findViewById(R.id.tvUsername);
                tvCaption = itemView.findViewById(R.id.tvCaption);
                tvUserCap = itemView.findViewById(R.id.tvUserCap);
                ivPhoto = itemView.findViewById(R.id.ivPhoto);
                ivProfilePic = itemView.findViewById(R.id.ivProfilePic);
                ivMyPhoto = itemView.findViewById(R.id.ivMyPhoto);
                tvDate = itemView.findViewById(R.id.tvDate);
                ivLike = itemView.findViewById(R.id.ivLike);
                ivLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setActivated(!v.isActivated());
                    }
                });
                ivPhoto.setOnClickListener(this);
            }
            else {
                ivMyPhoto = itemView.findViewById(R.id.ivMyPhoto);
                itemView.setOnClickListener(this);
            }
        }

        public void bind(Post post) {
            if(!isGrid)
            {
                tvUserCap.setText(post.getUser().getUsername());
                tvUsername.setText(post.getUser().getUsername());
                tvDate.setText(getRelativeTimeAgo(post.getCreatedAt().toString()));
                if(ivLike.isActivated()) {
                    post.setLiked(true);
                    post.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(com.parse.ParseException e) {

                        }
                    });
                }
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
            } else {
                ParseFile image = post.getImage();
                if (image!=null) {
                    Glide.with(context)
                            .load(image.getUrl())
                            .apply(new RequestOptions().override(300, 300))
                            .into(ivMyPhoto);
                }
            }
            //TODO

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
    public String getRelativeTimeAgo(String myDate) {

        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(myDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_TIME).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }


}
