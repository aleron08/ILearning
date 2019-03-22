package com.example.aleron08.ilearning.view.activity;

import android.Manifest;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aleron08.ilearning.R;
import com.example.aleron08.ilearning.util.ImmersiveStatusBar;
import com.example.aleron08.ilearning.util.PictureEditText;

public class NewBlogActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView ivBack, ivPicture;
    private TextView tvSend;
    private EditText etTitle;
    private PictureEditText petArticle;

    private static final int LOCAL_IMAGE_CODE = 888;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_blog);
        LinearLayout linearLayout = findViewById(R.id.ll_new_blog_actionbar);
        ImmersiveStatusBar.initStateInActivity(this,linearLayout);
        initView();
    }

    private void initView(){
        ivBack = findViewById(R.id.iv_new_blog_back);
        tvSend = findViewById(R.id.tv_new_blog_send);
        ivPicture = findViewById(R.id.iv_new_blog_picture);
        etTitle = findViewById(R.id.et_new_blog_title);
        petArticle = findViewById(R.id.pet_new_blog_article);
        ivBack.setOnClickListener(this);
        ivPicture.setOnClickListener(this);
        petArticle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.i("NewBlogActivity", petArticle.getmContentList().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.iv_new_blog_back:
                finish();
                break;
            case R.id.tv_new_blog_send:

                break;
            case R.id.iv_new_blog_picture:
                //获取相册运行时权限
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }
                selectPicFromLocal(this, LOCAL_IMAGE_CODE);
                break;
        }
    }

    //从本地相册选择图片
    private void selectPicFromLocal(Context context, int LOCAL_IMAGE_CODE){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, LOCAL_IMAGE_CODE);
    }

    //4.4之前
    public void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        Log.d("Uri1", imagePath);
        petArticle.insertBitmap(imagePath);
    }

    //4.4之后
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                //解析出数字格式的ID
                String id = docId.split(":")[1];
                //获取相册路径
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://download/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
            Log.d("Uri1", imagePath);
        }
        petArticle.insertBitmap(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;

        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case LOCAL_IMAGE_CODE:
                    if (data != null) {
                        if (resultCode == RESULT_OK) {
                            if (Build.VERSION.SDK_INT >= 19) {
                                handleImageOnKitKat(data);
                            } else {
                                handleImageBeforeKitKat(data);
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
