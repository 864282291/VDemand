package com.bigdata.base.act;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bigdata.base.R;
import com.bigdata.base.pic.helper.GlideImgHelper;
import com.bigdata.base.view.TitleLibView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Administrator
 */
public class PictureActivity extends BaseActivity {
    private PictureAdapter pictureAdapter;
    private TitleLibView titleLibView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_picture;
    }

    @Override
    public void initView() {
        int picSize = getIntent().getExtras().getInt("picSize");
        int picMax = getIntent().getExtras().getInt("picMax");
        setTitle("图片选择");
        pictureAdapter = new PictureAdapter(this, picSize, picMax);
        GridView picture_grid = (GridView) findViewById(R.id.picture_grid);
        titleLibView = (TitleLibView) findViewById(R.id.titleLibView);
        picture_grid.setAdapter(pictureAdapter);

        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
        } else {
            PictureTask pictureTask = new PictureTask();
            pictureTask.execute();
        }
        titleLibView.setTitleTv("图片选择");
        titleLibView.setRightTv("确定");
        titleLibView.setOnRightTvClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("pic",
                        pictureAdapter.getChecked());
                intent.putExtras(bundle);
                setResult(101, intent);
                finish();
            }
        });
//        setRightText("完成", getResources().getColor(R.color.text_gray1),
//                new OnClickListener() {
//
//                    @Override
//                    public void onClick(View arg0) {
//                        // TODO Auto-generated method stub
//                        Intent intent = new Intent();
//                        Bundle bundle = new Bundle();
//                        bundle.putStringArrayList("pic",
//                                pictureAdapter.getChecked());
//                        intent.putExtras(bundle);
//                        setResult(101, intent);
//                        finish();
//                    }
//                });
    }

    @Override
    public void initClick() {

    }

    @Override
    public void preInitData() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onSuccess(int requestCode,int returnCode, String result) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    /**
     * 图片适配器
     *
     * @author Administrator
     */
    class PictureAdapter extends BaseAdapter {
        private ArrayList<PictureBean> pictures;// 保存路径
        private HashMap<String, String> checked = new HashMap<String, String>();
        private Context context;
        private int picSize;
        private int picMax;

        public PictureAdapter(Context context, int picSize, int picMax) {
            // TODO Auto-generated constructor stub
            this.context = context;
            this.picSize = picSize;
            this.picMax = picMax;
        }

        public void setPictures(ArrayList<PictureBean> pictures) {
            this.pictures = pictures;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            if (pictures != null) {
                return pictures.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        public ArrayList<String> getChecked() {
            if (checked == null) {
                return null;
            }
            ArrayList<String> checkList = null;
            Set<String> mapKeys = checked.keySet();
            if (mapKeys != null) {
                Iterator<String> iterator = mapKeys.iterator();
                checkList = new ArrayList<String>();
                while (iterator.hasNext()) {
                    String key = (String) iterator.next();
                    String value = (String) checked.get(key);
                    checkList.add(value);
                }
            }
            return checkList;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            VH vh;
            if (convertView == null) {
                convertView = LinearLayout.inflate(context,
                        R.layout.item_picture_checkbox, null);
                vh = new VH();
                vh.imageView = (ImageView) convertView.findViewById(R.id.goods_img);
                vh.child_checkbox = (CheckBox) convertView
                        .findViewById(R.id.child_checkbox);
                convertView.setTag(vh);
            } else {
                vh = (VH) convertView.getTag();
            }

            final VH viewholder = vh;
            String url = "file://" + pictures.get(position).getPicUri();
            GlideImgHelper.getInstance(mContext).showNormalImg(url, vh.imageView);
            final PictureBean pictureBean = pictures.get(position);
            vh.child_checkbox
                    .setOnCheckedChangeListener(new OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView,
                                                     boolean isChecked) {
                            // TODO Auto-generated method stub
                            if (isChecked) {
                                if (picMax == 1) {
                                    if (picSize + checked.size() >= picMax) {
                                        showToast(
                                                "图片选择不能超过" + picMax + "张");
                                        buttonView.setChecked(false);
                                    } else {
                                        checked.put(pictureBean.getPicUri(),
                                                pictureBean.getPicUri());
                                    }
                                } else {
                                    if (picSize + checked.size() > picMax) {
                                        showToast(
                                                "图片选择不能超过" + picMax + "张");
                                        buttonView.setChecked(false);
                                    } else {
                                        checked.put(pictureBean.getPicUri(),
                                                pictureBean.getPicUri());
                                    }
                                }

                            } else {
                                if (checked.size() > 0) {
                                    checked.remove(pictureBean.getPicUri());
                                }
                            }
                            pictureBean.setCheck(isChecked);
                        }
                    });
            vh.child_checkbox.setChecked(pictureBean.isCheck);
            return convertView;
        }

        class VH {
            ImageView imageView;
            CheckBox child_checkbox;
        }
    }

    class PictureBean {
        private String picUri;
        private boolean isCheck;

        public String getPicUri() {
            return picUri;
        }

        public void setPicUri(String picUri) {
            this.picUri = picUri;
        }

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean isCheck) {
            this.isCheck = isCheck;
        }
    }

    class PictureTask extends AsyncTask<Void, Void, ArrayList<PictureBean>> {

        @Override
        protected ArrayList<PictureBean> doInBackground(Void... params) {
            // TODO Auto-generated method stub
            ArrayList<PictureBean> picture = new ArrayList<PictureBean>();
            Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            ContentResolver mContentResolver = PictureActivity.this
                    .getContentResolver();

            // 只查询jpeg和png的图片
            Cursor mCursor = mContentResolver.query(mImageUri, null,
                    MediaStore.Images.Media.MIME_TYPE + "=? or "
                            + MediaStore.Images.Media.MIME_TYPE + "=?",
                    new String[]{"image/jpeg", "image/png"},
                    MediaStore.Images.Media.DATE_MODIFIED);

            while (mCursor.moveToNext()) {
                // 获取图片的路径
                String path = mCursor.getString(mCursor
                        .getColumnIndex(MediaStore.Images.Media.DATA));
                PictureBean pictureBean = new PictureBean();
                pictureBean.setPicUri(path);
                picture.add(pictureBean);
            }
            return picture;
        }

        @Override
        protected void onPostExecute(ArrayList<PictureBean> picture) {
            super.onPostExecute(picture);
            if (picture == null) {

            } else {
                pictureAdapter.setPictures(picture);
            }
        }
    }

}
