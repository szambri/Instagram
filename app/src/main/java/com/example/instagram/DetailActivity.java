package com.example.instagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.instagram.models.Post;
import com.parse.ParseFile;
import com.parse.SaveCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    private Post post;
    private TextView tvUsername;
    private TextView tvUserCap;
    private TextView tvCaption;
    private ImageView ivPhoto;
    private ImageView ivProfilePic;
    private TextView tvDate;
    private ImageView ivLike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        tvUsername = findViewById(R.id.tvUsername);
        tvCaption = findViewById(R.id.tvCaption);
        tvUserCap = findViewById(R.id.tvUserCap);
        ivPhoto = findViewById(R.id.ivPhoto);
        tvDate = findViewById(R.id.tvDate);
        ivProfilePic = findViewById(R.id.ivProfilePic);
        ivLike = findViewById(R.id.ivLike);

        post = (Post) getIntent().getExtras().get("post");

        tvUsername.setText(post.getUser().getUsername());
        tvCaption.setText(post.getCaption());
        tvUserCap.setText(post.getUser().getUsername());
        tvDate.setText(getRelativeTimeAgo(post.getCreatedAt().toString()));
        ivLike.setActivated(post.getLiked());
        ivLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setActivated(!v.isActivated());
                post.setLiked(v.isActivated());
                post.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(com.parse.ParseException e) {
                    }
                });

            }
        });
        ParseFile image = post.getImage();
        ParseFile prof = post.getUser().getParseFile("profilePic");
        if (prof!=null) {
            Glide.with(this)
                    .load(prof.getUrl())
                    .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(100)))
                    .into(ivProfilePic);
        }
        else{
            Glide.with(this)
                    .load(R.drawable.instagram_user_filled_24)
                    .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(100)))
                    .into(ivProfilePic);
        }
        Glide.with(this)
                .load(image.getUrl())
                .apply(new RequestOptions().override(300, 300))
                .into(ivPhoto);
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
