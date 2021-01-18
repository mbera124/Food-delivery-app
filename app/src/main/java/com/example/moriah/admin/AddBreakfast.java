package com.example.moriah.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.moriah.R;
import com.example.moriah.activities.AboutUsActivity;
import com.example.moriah.activities.CartActivity;
import com.example.moriah.activities.OrdersActivity;
import com.example.moriah.activities.UserDashboard;
import com.example.moriah.model.Breakfast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class AddBreakfast extends AppCompatActivity {
    private Button btnselect,btnsave;
    private ImageView imgcategory;
    private EditText etbreakfastname,etbreakfastid,etbreakfastprice;
    BottomNavigationView bottomNavigationView;
    private static final String TAG = AddCategory.class.getSimpleName();
    private Uri filePath;
    DatabaseReference databaseReference;
    FirebaseStorage storage;
    StorageReference storageReference;
    // Folder path for Firebase Storage.
    String Storage_Path = "https://console.firebase.google.com/u/0/project/bookings-f6c90/storage/bookings-f6c90.appspot.com/files~2FCategory";

    // Root Database Name for Firebase Database.
    String Database_Path = "Breakfast";

    private final int PICK_IMAGE_REQUEST = 71;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_breakfast);
        btnsave=findViewById(R.id.btnsave);
        btnselect=findViewById(R.id.btnselect);
        imgcategory=findViewById(R.id.imgcat);
        etbreakfastid=findViewById(R.id.etbreakfastid);
        etbreakfastname=findViewById(R.id.etbreakfastname);
        etbreakfastprice=findViewById(R.id.etbreakfastprice);
              GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
            Log.e(TAG, "Name: " + personName + ", email: " + personEmail+ ",Id:"+ personId+
                    ", Image: " + personPhoto);
        }
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_menu:
                        startActivity(new Intent(getApplicationContext(), UserDashboard.class));
                        overridePendingTransition(0,0);
                        finish();
                        break;
                    case R.id.navigation_cart:
                        startActivity(new Intent(getApplicationContext(), CartActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                        break;
                    case R.id.navigation_orders:
                        if (acct.getEmail().equals("josephmbera124@gmail.com")) {
                            startActivity(new Intent(getApplicationContext(), EditOrders.class));
                        }else{
                            startActivity(new Intent(getApplicationContext(), OrdersActivity.class));
                            overridePendingTransition(0,0);}
                        finish();
                        break;
                    case R.id.navigation_about:
                        startActivity(new Intent(getApplicationContext(), AboutUsActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("Breakfast");

        btnselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();

            }
        });

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();

            }
        });
    }


    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imgcategory.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {
        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String ImageURL = "";
                                    ImageURL = uri.toString();
                                    String TempBreakfastname = etbreakfastname.getText().toString().trim();
                                    String TempBreakfastid = etbreakfastid.getText().toString().trim();
                                    String TempBreakfastprice = etbreakfastprice.getText().toString().trim();
//                                    String TempImageId = etmenuid.getText().toString().trim();
//                                    String TempImagePrice = etmenuprice.getText().toString().trim();
//                                    String TempImageDescription = etmenudescription.getText().toString().trim();

                                    Breakfast breakfast = new Breakfast(TempBreakfastname,ImageURL,TempBreakfastprice,TempBreakfastid);
                                    databaseReference.push().setValue(breakfast);
                                }
                            });


                            progressDialog.dismiss();
                            Toast.makeText(AddBreakfast.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddBreakfast.this, UserDashboard.class));
                            finish();
                        }
                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AddBreakfast.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })

                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        }
    }

}
