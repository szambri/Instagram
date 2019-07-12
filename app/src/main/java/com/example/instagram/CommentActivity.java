package com.example.instagram;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.instagram.models.Comment;
import com.example.instagram.models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends AppCompatActivity {

    public static final int EDIT_REQUEST_CODE = 20;
    public static final String ITEM_TEXT = "itemText";
    public static final String ITEM_POSITION = "itemPosition";

    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView lvComments;
    private Button btnSend;
    private Comment comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        items = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        btnSend = findViewById(R.id.btnSend);
        lvComments = findViewById(R.id.lvComments);
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        lvComments.setAdapter(itemsAdapter);
        queryComments();
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etComment = (EditText) findViewById(R.id.etComment);
                String itemText = etComment.getText().toString();
                Post post = (Post) getIntent().getExtras().get("post");
                itemsAdapter.add(itemText);
                itemsAdapter.notifyDataSetChanged();
                Comment myComment = new Comment();
                myComment.setComment(itemText);
                myComment.setUser(ParseUser.getCurrentUser());
                myComment.setPost(post.getObjectId());
                myComment.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
                            Toast.makeText(CommentActivity.this, "YAY", Toast.LENGTH_LONG).show();
                        }else{
                            e.printStackTrace();
                        }

                    }
                });
                etComment.setText("");
                Toast.makeText(getApplicationContext(), "Item added to list", Toast.LENGTH_SHORT).show();
                hideKeyboard(CommentActivity.this);
            }
        });

    }

    @Override
    // modify items with toast
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == EDIT_REQUEST_CODE) {
            String updatedItem = data.getExtras().getString(ITEM_TEXT);
            int position = data.getExtras().getInt(ITEM_POSITION, 0);
            items.set(position, updatedItem);
            itemsAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Item updated", Toast.LENGTH_SHORT).show();
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void queryComments() {
        ParseQuery<Comment> commentQuery = new ParseQuery<>(Comment.class);
        Post post = (Post) getIntent().getExtras().get("post");
        commentQuery.include(Comment.KEY_USER);
        commentQuery.setLimit(20).orderByDescending("createdAt");
        commentQuery.whereEqualTo(Comment.KEY_POST, post.getObjectId());
        commentQuery.findInBackground(new FindCallback<Comment>() {
            @Override
            public void done(List<Comment> comments, ParseException e) {
                if (e== null) {
                    if(comments!=null)
                    {
                        for(int i=0; i<comments.size(); i++)
                        {
                            items.add(comments.get(i).getComment());
                        }
                        itemsAdapter.notifyDataSetChanged();
                    }
                else {
                        Log.e("ProfFrag", "Error with query");
                        e.printStackTrace();
                    }
                }

            }
        });

    }
}
