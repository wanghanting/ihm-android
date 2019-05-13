package com.example.ihm_android;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class Profile extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Profile";
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;
    Button bHome;
    Button button_avatars;
    EditText etLastName, etFirstName, etShortDescription, etLongDescription;
    ImageView imageView_avatars;
    Data data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        data = (Data) getApplication();

        bHome = (Button) findViewById(R.id.bHome);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etShortDescription = (EditText) findViewById(R.id.etShortDescription);
        etLongDescription = (EditText) findViewById(R.id.etLongDescription);
        button_avatars = (Button) findViewById(R.id.button_avatars);
        imageView_avatars = (ImageView) findViewById(R.id.imageView_avatars);

        displayUserDetails(data.user);

        bHome.setOnClickListener(this);
//        button_avatars.setOnClickListener(this);
        button_avatars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChoosePicDialog();
            }
        });
    }

    protected void showChoosePicDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choisir votre image");
        String[] items = {"Une image locale", "Prendre une photo"};
        builder.setNegativeButton("Annuler", null);
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case CHOOSE_PICTURE: // 选择本地照片
                        Intent openAlbumIntent = new Intent(
                                Intent.ACTION_PICK);
                        openAlbumIntent.setType("image/*");
                        startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                        break;
                    case TAKE_PICTURE: // 拍照
                        takePicture();
                        break;
                }
            }
        });
        builder.create().show();
    }

    private void takePicture() {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= 23) {
            // 需要申请动态权限
            int check = ContextCompat.checkSelfPermission(this, permissions[0]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (check != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
        Intent openCameraIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment
                .getExternalStorageDirectory(), "image.jpg");
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= 24) {
            openCameraIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            tempUri = FileProvider.getUriForFile(Profile.this, "com.example.ihm_android.fileProvider", file);
        } else {
            tempUri = Uri.fromFile(new File(Environment
                    .getExternalStorageDirectory(), "image.jpg"));
        }
        // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bHome:
                data.user.setLastName(etLastName.getText().toString());
                data.user.setFirstName(etFirstName.getText().toString());
                data.user.setSmallDescription(etShortDescription.getText().toString());
                data.user.setLongDescription(etLongDescription.getText().toString());
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }

    void displayUserDetails(User user) {
        etLastName.setText(user.getLastName());
        etFirstName.setText(user.getFirstName());
        etShortDescription.setText(user.getSmallDescription());
        etLongDescription.setText(user.getLongDescription());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageView_avatars = findViewById(R.id.imageView_avatars);
        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            switch (requestCode) {
                case TAKE_PICTURE:
                    startCrop(tempUri); // 开始对图片进行裁剪处理
                    break;
                case CHOOSE_PICTURE:
                    startCrop(data.getData()); // 开始对图片进行裁剪处理
                    System.out.printf("开始剪裁");
                    setImageToView(data);
                    break;
                case CROP_SMALL_PICTURE:
//                    if (data != null) {
//                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
//                    }
//                    break;
                    final Uri croppedUri = UCrop.getOutput(data);
                    try {
                        if (croppedUri != null) {
                            Bitmap bit = BitmapFactory.decodeStream(getContentResolver().openInputStream(croppedUri));
                            imageView_avatars.setImageBitmap(bit);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case UCrop.RESULT_ERROR: {
                    final Throwable cropError = UCrop.getError(data);
                    Log.i("RESULT_ERROR", "UCrop_RESULT_ERROR");
                    break;
                }
            }
        }
    }
//    protected void startPhotoZoom(Uri uri) {
//         if (uri == null) {
//            Log.i("tag", "The uri is not exist.");
//        }
//        tempUri = uri;
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(uri, "image/*");
//        // 设置裁剪
//        intent.putExtra("crop", "true");
//        // aspectX aspectY 是宽高的比例
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//        // outputX outputY 是裁剪图片宽高
//        intent.putExtra("outputX", 150);
//        intent.putExtra("outputY", 150);
//        intent.putExtra("return-data", true);
//        startActivityForResult(intent, CROP_SMALL_PICTURE);
//
//
//    }

        private void startCrop (Uri uri){
            UCrop.Options options = new UCrop.Options();
            //裁剪后图片保存在文件夹中
            Uri destinationUri = Uri.fromFile(new File(getExternalCacheDir(), "uCrop.jpg"));
            UCrop uCrop = UCrop.of(uri, destinationUri);//第一个参数是裁剪前的uri,第二个参数是裁剪后的uri
            uCrop.withAspectRatio(1, 1);//设置裁剪框的宽高比例
            //下面参数分别是缩放,旋转,裁剪框的比例
            options.setAllowedGestures(UCropActivity.ALL, UCropActivity.NONE, UCropActivity.ALL);
            options.setToolbarTitle("移动和缩放");//设置标题栏文字
            options.setCropGridStrokeWidth(2);//设置裁剪网格线的宽度(我这网格设置不显示，所以没效果)
            options.setCropFrameStrokeWidth(10);//设置裁剪框的宽度
            options.setMaxScaleMultiplier(3);//设置最大缩放比例
            options.setHideBottomControls(true);//隐藏下边控制栏
            options.setShowCropGrid(false);  //设置是否显示裁剪网格
            options.setOvalDimmedLayer(true);//设置是否为圆形裁剪框
            options.setShowCropFrame(false); //设置是否显示裁剪边框(true为方形边框)
            options.setToolbarWidgetColor(Color.parseColor("#ffffff"));//标题字的颜色以及按钮颜色
            options.setDimmedLayerColor(Color.parseColor("#AA000000"));//设置裁剪外颜色
            options.setToolbarColor(Color.parseColor("#000000")); // 设置标题栏颜色
            options.setStatusBarColor(Color.parseColor("#000000"));//设置状态栏颜色
            options.setCropGridColor(Color.parseColor("#ffffff"));//设置裁剪网格的颜色
            options.setCropFrameColor(Color.parseColor("#ffffff"));//设置裁剪框的颜色
            uCrop.withOptions(options);
            uCrop.start(this);
            System.out.printf("剪裁成功");
        }

        protected void setImageToView (Intent data){
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap photo = extras.getParcelable("data");
                Log.d(TAG, "setImageToView:" + photo);
                photo = ImageUtils.toRoundBitmap(photo); // 这个时候的图片已经被处理成圆形的了
                imageView_avatars.setImageBitmap(photo);
                uploadPic(photo);
            }
        }

        private void uploadPic (Bitmap bitmap){
            // 上传至服务器
            // ... 可以在这里把Bitmap转换成file，然后得到file的url，做文件上传操作
            // 注意这里得到的图片已经是圆形图片了
            // bitmap是没有做个圆形处理的，但已经被裁剪了
            String imagePath = ImageUtils.savePhoto(bitmap, Environment
                    .getExternalStorageDirectory().getAbsolutePath(), String
                    .valueOf(System.currentTimeMillis()));
            Log.e("imagePath", imagePath + "");
            if (imagePath != null) {
                // 拿着imagePath上传了
                // ...
                Log.d(TAG, "imagePath:" + imagePath);
            }
        }

        @Override
        public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults){
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                // 没有获取 到权限，从新请求，或者关闭app
                Toast.makeText(this, "需要存储权限", Toast.LENGTH_SHORT).show();
            }
        }



}


