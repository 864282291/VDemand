package com.bigdata.base.pic.act;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigdata.base.R;
import com.bigdata.base.pic.bean.ImageItem;
import com.bigdata.base.pic.config.ImgConfig;
import com.bigdata.base.pic.helper.GlideImgHelper;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @auther Leo--李智
 * Create at 2017/9/8 17:26
 */
public class SelectPicActivity extends Activity {


    public static final String INTENT_MAX_NUM = "intent_max_num";
    public static final String INTENT_SELECTED_PICTURE = "intent_selected_picture";
    public static final String INTENT_SELECTED_TYPE = "intent_selected_type";

    private Context context;
    private GridView gridview;
    private PictureAdapter adapter;

    /**
     * 临时的辅助类，用于防止同一个文件夹的多次扫描
     */
    private HashMap<String, Integer> tmpDir = new HashMap<String, Integer>();
    private ArrayList<ImageFloder> mDirPaths = new ArrayList<ImageFloder>();

    private ContentResolver mContentResolver;

    //选择按钮和完成按钮
    private Button btn_select, btn_ok;

    //已选择的图片
    private ArrayList<String> selectedPicture = new ArrayList<String>();

    private String cameraPath = null;
    private int isExisted;
    private TextView preview_tv;
    public ArrayList<String> existedPictureList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_select_picture);
        Bundle bundle = getIntent().getExtras();
        existedPictureList = bundle.getStringArrayList("allSelectedPicture");
        isExisted = existedPictureList.size();
        context = this;
        mContentResolver = getContentResolver();
        initView();
    }

    /**
     * 点击完成按钮
     */
    public void ok(View v) {
        Intent data = new Intent();
        data.putExtra(INTENT_SELECTED_PICTURE, selectedPicture);
        setResult(RESULT_OK, data);
        this.finish();
    }

    /**
     * 视图初始化
     */
    private void initView() {
        btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_ok.setText(getResources().getString(R.string.complete) + isExisted + "/" + ImgConfig.MAX_NUM);

        btn_select = (Button) findViewById(R.id.btn_select);

        gridview = (GridView) findViewById(R.id.gridview);
        adapter = new PictureAdapter();
        gridview.setAdapter(adapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    goCamare();
                }
            }
        });

        preview_tv = (TextView) findViewById(R.id.preview_tv);
        preview_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPicture.size() == 0) {
                    Toast.makeText(SelectPicActivity.this, "请选择图片", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(SelectPicActivity.this, ImagePagerActivity.class);
                intent.putStringArrayListExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, selectedPicture);
                intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, 0);
                startActivity(intent);
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra(INTENT_SELECTED_PICTURE, selectedPicture);
                setResult(ImgConfig.RESULT_IMG, data);
                finish();
            }
        });
        getThumbnail();
    }

    /**
     * 使用相机拍照
     */
    protected void goCamare() {
        if (isExisted + selectedPicture.size() + 1 > ImgConfig.MAX_NUM) {
            Toast.makeText(context, getResources().getString(R.string.chose_at_most) + ImgConfig.MAX_NUM + getResources().getString(R.string.sheet), Toast.LENGTH_SHORT).show();
            return;
        }

        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri imageUri = getOutputMediaFileUri();
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(openCameraIntent, ImgConfig.TAKE_PICTURE);
    }

    /**
     * 用于拍照时获取输出的Uri
     */
    protected Uri getOutputMediaFileUri() {
        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Night");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        cameraPath = mediaFile.getAbsolutePath();
        return Uri.fromFile(mediaFile);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && cameraPath != null) {

            selectedPicture.add(cameraPath);

            Intent data2 = new Intent();
            data2.putExtra(INTENT_SELECTED_PICTURE, selectedPicture);

            setResult(RESULT_OK, data2);
            this.finish();
        }
    }

    public void back(View v) {
        onBackPressed();
    }

    class PictureAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return ImgConfig.currentImageFolder.images.size() + 1;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            //自定义的一个类用来缓存convertview
            ViewHolder holder = null;

            if (convertView == null) {
                convertView = View.inflate(context, R.layout.grid_item_picture, null);
                holder = new ViewHolder();
                holder.iv = (ImageView) convertView.findViewById(R.id.iv);
                holder.checkBox = (Button) convertView.findViewById(R.id.check);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position == 0) {//拍摄图片
                holder.iv.setImageResource(R.drawable.pickphotos_to_camera_normal);
                holder.checkBox.setVisibility(View.INVISIBLE);
            } else {
                position = position - 1;
                holder.checkBox.setVisibility(View.VISIBLE);

                final ImageItem item = ImgConfig.currentImageFolder.images.get(position);

                //显示图片
                GlideImgHelper.getInstance(SelectPicActivity.this).showNormalImg("file://" + item.path, holder.iv, new RequestListener() {
                    @Override
                    public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                });
                //是否选中
                boolean isSelected = (selectedPicture.contains(item.path) || existedPictureList.contains(item.path));
                holder.checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //若选中的图片多于所设置的上限，不再加入
                        if (!v.isSelected() && isExisted + selectedPicture.size() + 1 > ImgConfig.MAX_NUM) {
                            Toast.makeText(context, getResources().getString(R.string.chose_at_most) + ImgConfig.MAX_NUM + getResources().getString(R.string.sheet), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        //二次选择，移除
                        if (selectedPicture.contains(item.path) || existedPictureList.contains(item.path)) {
                            selectedPicture.remove(item.path);
                        } else {

                            //加入数组
                            selectedPicture.add(item.path);
                        }

                        btn_ok.setEnabled(selectedPicture.size() > 0);
                        btn_ok.setText(getResources().getString(R.string.complete) + (selectedPicture.size() + isExisted) + "/" + ImgConfig.MAX_NUM);

                        v.setSelected(selectedPicture.contains(item.path) || existedPictureList.contains(item.path));
                    }
                });

                holder.checkBox.setSelected(isSelected);
            }

            return convertView;
        }
    }

    //自定义的一个类用来缓存convertview
    class ViewHolder {
        ImageView iv;
        Button checkBox;
    }

    /**
     * 获取缩略图
     */
    private void getThumbnail() {
        Cursor mCursor = mContentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.ImageColumns.DATA}, "", null,
                MediaStore.MediaColumns.DATE_ADDED + " DESC");

        if (mCursor.moveToFirst()) {
            int _date = mCursor.getColumnIndex(MediaStore.Images.Media.DATA);
            do {

                // 获取图片的路径
                String path = mCursor.getString(_date);
                ImgConfig.imageAll.images.add(new ImageItem(path));

                // 获取该图片的父路径名
                File parentFile = new File(path).getParentFile();
                if (parentFile == null) {
                    continue;
                }

                ImageFloder imageFloder = null;
                String dirPath = parentFile.getAbsolutePath();

                if (!tmpDir.containsKey(dirPath)) {
                    // 初始化imageFloder
                    imageFloder = new ImageFloder();
                    imageFloder.setDir(dirPath);
                    imageFloder.setFirstImagePath(path);
                    mDirPaths.add(imageFloder);
                    tmpDir.put(dirPath, mDirPaths.indexOf(imageFloder));
                } else {
                    imageFloder = mDirPaths.get(tmpDir.get(dirPath));
                }

                imageFloder.images.add(new ImageItem(path));
            } while (mCursor.moveToNext());
        }

        mCursor.close();

        tmpDir = null;
    }

    class ImageFloder implements Serializable {
        /**
         * 图片的文件夹路径
         */
        private String dir;

        /**
         * 第一张图片的路径
         */
        private String firstImagePath;
        /**
         * 文件夹的名称
         */
        private String name;

        public List<ImageItem> images = new ArrayList<ImageItem>();

        public String getDir() {
            return dir;
        }

        public void setDir(String dir) {
            this.dir = dir;
            int lastIndexOf = this.dir.lastIndexOf("/");
            this.name = this.dir.substring(lastIndexOf);
        }

        public String getFirstImagePath() {
            return firstImagePath;
        }

        public void setFirstImagePath(String firstImagePath) {
            this.firstImagePath = firstImagePath;
        }

        public String getName() {
            return name;
        }

    }
}
