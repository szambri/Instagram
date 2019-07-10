package com.example.instagram;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.instagram.models.Post;
import com.parse.ParseFile;

import java.util.List;

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

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvUsername;
        private TextView tvUserCap;
        private TextView tvCaption;
        private ImageView ivPhoto;
        private ImageView ivProfilePic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvCaption = itemView.findViewById(R.id.tvCaption);
            tvUserCap = itemView.findViewById(R.id.tvUserCap);
            ivPhoto = itemView.findViewById(R.id.ivPhoto);
            ivProfilePic = itemView.findViewById(R.id.ivProfilePic);
        }

        public void bind(Post post) {
            //TODO
            tvUserCap.setText(post.getUser().getUsername());
            tvUsername.setText(post.getUser().getUsername());
            ParseFile image = post.getImage();
            if (image!=null) {
                Glide.with(context)
                        .load(image.getUrl())
                        .into(ivPhoto);
            }
            tvCaption.setText(post.getCaption());

        }

    }

}
