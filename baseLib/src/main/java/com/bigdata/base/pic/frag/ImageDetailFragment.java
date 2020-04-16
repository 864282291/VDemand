package com.bigdata.base.pic.frag;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bigdata.base.R;
import com.bigdata.base.pic.helper.GlideImgHelper;
import com.bigdata.base.pic.view.photoview.PhotoViewAttacher;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class ImageDetailFragment extends Fragment {
    private String mImageUrl;
    private ImageView mImageView;
    private ProgressBar progressBar;
    private PhotoViewAttacher mAttacher;

    public static ImageDetailFragment newInstance(String imageUrl) {
        final ImageDetailFragment f = new ImageDetailFragment();

        final Bundle args = new Bundle();
        args.putString("url", imageUrl);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageUrl = getArguments() != null ? getArguments().getString("url") : null;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.image_detail_fragment, container, false);
        mImageView = (ImageView) v.findViewById(R.id.image);
        mAttacher = new PhotoViewAttacher(mImageView);

        mAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {

            @Override
            public void onPhotoTap(View arg0, float arg1, float arg2) {
                getActivity().finish();
            }
        });

        progressBar = (ProgressBar) v.findViewById(R.id.loading);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//
//        if (mImageUrl.contains("http")) {
//
//            GlideImgHelper.getInstance(getActivity()).showNormalImg(mImageUrl, mImageView, new RequestListener() {
//                @Override
//                public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
//                    Toast.makeText(getActivity(), "无法显示图片", Toast.LENGTH_SHORT).show();
//                    progressBar.setVisibility(View.GONE);
//                    return false;
//                }
//
//                @Override
//                public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
//                    progressBar.setVisibility(View.GONE);
//                    mAttacher.update();
//                    return false;
//                }
//            });
//        } else if(mImageUrl.startsWith("file://")){
        GlideImgHelper.getInstance(getActivity()).showNormalImg(mImageUrl, mImageView, new RequestListener() {
            @Override
            public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
                Toast.makeText(getActivity(), "无法显示图片", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                mAttacher.update();
                return false;
            }
        });
//            BitmapFactory.Options opts = new BitmapFactory.Options();
//            opts.inJustDecodeBounds = true;
//            BitmapFactory.decodeFile(mImageUrl, opts);
//            opts.inSampleSize = computeSampleSize(opts, -1, 256 * 256);
////这里一定要将其设置回false，因为之前我们将其设置成了true
//            opts.inJustDecodeBounds
//                    = false;
//            Bitmap bmp = null;
//            try {
//                bmp = BitmapFactory.decodeFile(mImageUrl, opts);
//            } catch (OutOfMemoryError
//                    err) {
//            }
//
//            //Bitmap bmp= BitmapFactory.decodeFile(mImageUrl);
//            mImageView.setImageBitmap(bmp);
//        }

    }

    public static int computeSampleSize(BitmapFactory.Options
                                                options,
                                        int minSideLength, int maxNumOfPixels) {
        int initialSize
                = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);

        int roundedSize;
        if (initialSize
                <= 8) {
            roundedSize
                    = 1;
            while (roundedSize
                    < initialSize) {
                roundedSize
                        <<= 1;
            }
        } else {
            roundedSize
                    = (initialSize + 7)
                    / 8 * 8;
        }

        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options
                                                        options,
                                                int minSideLength, int maxNumOfPixels) {
        double w
                = options.outWidth;
        double h
                = options.outHeight;

        int lowerBound
                = (maxNumOfPixels == -1)
                ? 1 :
                (int)
                        Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound
                = (minSideLength == -1)
                ? 128 :
                (int)
                        Math.min(Math.floor(w / minSideLength),
                                Math.floor(h
                                        / minSideLength));

        if (upperBound
                < lowerBound) {
            return lowerBound;
        }

        if ((maxNumOfPixels
                == -1)
                &&
                (minSideLength
                        == -1)) {
            return 1;
        } else if (minSideLength
                == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }
}
