package com.rahmaniaaas.popotoan;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rahmaniaaas.popotoan.Model.Post;
import com.rahmaniaaas.popotoan.Model.User;

public class AddPostActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;

    EditText mJudulPost, mPost;
    ImageView mGambar;
    Button mChooseImage;
    //our database reference object
    DatabaseReference databaseFood;
    FirebaseAuth mAuth;

    private Uri imageUri;

    private StorageReference mStorage;
    Query databaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        imageUri = null;

        mAuth = FirebaseAuth.getInstance();

        mStorage = FirebaseStorage.getInstance().getReference().child("images");

        databaseFood = FirebaseDatabase.getInstance().getReference(HomeActivity.table1);

        databaseUser = FirebaseDatabase.getInstance().getReference(HomeActivity.table3);

        mJudulPost = (EditText) findViewById(R.id.title_post);
        mPost = (EditText) findViewById(R.id.desc_post);
        mGambar = findViewById(R.id.tambah_foto);

        mChooseImage = findViewById(R.id.btn_choose_image);
        mChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });
    }

    public void Post(View view) {

        databaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final User user = dataSnapshot.child(mAuth.getUid()).getValue(User.class);

                final String name = user.getUsername();
                final String title = mJudulPost.getText().toString();
                final String postMessage = mPost.getText().toString();
                final String id = databaseFood.push().getKey();
                final String userId = mAuth.getUid();
                final long timestamp = System.currentTimeMillis();

                if (imageUri != null && !TextUtils.isEmpty(name)) {

                    final StorageReference image = mStorage.child(id + ".jpg");

                    image.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> uploadTask) {

                            if (uploadTask.isSuccessful()) {

                                String download_url = uploadTask.getResult().getDownloadUrl().toString();
                                Post post = new Post(id, userId, name, download_url, title, postMessage,0-timestamp);
                                databaseFood.child(id).setValue(post);

                            } else {
                                Toast.makeText(AddPostActivity.this, "Error : " + uploadTask.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                    //displaying a success toast
                    Toast.makeText(AddPostActivity.this, "Uploaded", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(AddPostActivity.this, HomeActivity.class);
                    startActivity(i);
                } else {
                    //if the value is not given displaying a toast
                    Toast.makeText(AddPostActivity.this, "Please Fill the form and choose image", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            mGambar.setImageURI(imageUri);
        }
    }
}



