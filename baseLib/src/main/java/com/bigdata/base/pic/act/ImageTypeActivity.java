package com.bigdata.base.pic.act;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigdata.base.R;
import com.bigdata.base.pic.bean.ImageFolder;
import com.bigdata.base.pic.bean.ImageItem;
import com.bigdata.base.pic.config.ImgConfig;
import com.bigdata.base.pic.helper.GlideImgHelper;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @auther Leo--李智
 * Create at 2017/9/8 17:26
 */
public class ImageTypeActivity extends Activity {


    private Context context;

    private ContentResolver mContentResolver;

    //图片文件的列表
    private GridView gridView;

    private FolderAdapter folderAdapter;

    private String cameraPath = null;

    private ArrayList<ImageFolder> mDirPaths = new ArrayList<ImageFolder>();
    private HashMap<String, Integer> tmpDir = new HashMap<String, Integer>();
    private int isExisted;
    public ArrayList<String> existedPictureList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_image_type);
        context = this;
        mContentResolver = getContentResolver();
        Bundle bundle = getIntent().getExtras();
        existedPictureList = bundle.getStringArrayList("allSelectedPicture");
        initView();
    }

    /**
     * 视图初始化
     */
    private void initView() {
        ImgConfig.imageAll.setDir(getResources().getString(R.string.dir_all_pictures));
        ImgConfig.imageAll.setDir(getResources().getString(R.string.dir_all_pictures));
        ImgConfig.currentImageFolder = ImgConfig.imageAll;
        mDirPaths.add(ImgConfig.imageAll);
        gridView = (GridView) findViewById(R.id.gridView);
        folderAdapter = new FolderAdapter();
        gridView.setAdapter(folderAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageFolder currentImageFolder = mDirPaths.get(position);
                ImgConfig.currentImageFolder = currentImageFolder;
                Intent intent = new Intent(ImageTypeActivity.this, SelectPicActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("allSelectedPicture", existedPictureList);
                intent.putExtras(bundle);
                startActivityForResult(intent,ImgConfig.RESULT_IMG);
            }
        });
        getThumbnail();
    }

    /**
     * 所有图片
     */
    class FolderAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mDirPaths.size();
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
            FolderViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.list_dir_item, null);
                holder = new FolderViewHolder();
                holder.id_dir_item_image = (ImageView) convertView.findViewById(R.id.id_dir_item_image);
                holder.id_dir_item_name = (TextView) convertView.findViewById(R.id.id_dir_item_name);
                holder.id_dir_item_count = (TextView) convertView.findViewById(R.id.id_dir_item_count);
                convertView.setTag(holder);
            } else {
                holder = (FolderViewHolder) convertView.getTag();
            }
            ImageFolder item = mDirPaths.get(position);
            GlideImgHelper.getInstance(ImageTypeActivity.this).showNormalImg("file://" + item.getFirstImagePath(), holder.id_dir_item_image, new RequestListener() {
                @Override
                public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
                    return false;
                }
            });
            holder.id_dir_item_count.setText(item.images.size() + getResources().getString(R.string.sheet));
            holder.id_dir_item_name.setText(item.getName());
            return convertView;
        }
    }

    //自定义的一个类用来缓存convertview
    class FolderViewHolder {
        ImageView id_dir_item_image;
        TextView id_dir_item_name;
        TextView id_dir_item_count;
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

                ImageFolder imageFloder = null;
                String dirPath = parentFile.getAbsolutePath();

                if (!tmpDir.containsKey(dirPath)) {
                    // 初始化imageFloder
                    imageFloder = new ImageFolder();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImgConfig.RESULT_IMG && data != null) {
            setResult(ImgConfig.RESULT_IMG, data);
            finish();
        }
    }

    public void back(View v) {
        onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
