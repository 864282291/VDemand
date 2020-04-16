package com.bigdata.base.util;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {
    private static Context mContext;
    private static FileUtil INSTANCE;

    public static FileUtil getInstance(Context context) {
        mContext = context;
        if (INSTANCE != null) {
            INSTANCE = null;
        }
        INSTANCE = new FileUtil();
        return INSTANCE;
    }

    /**
     * 判断SD卡挂载
     *
     * @date 2015年11月11日15:23:53
     * @author qinqin
     */
    public static boolean getSDCardWorking() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED); // 判断sd卡是否存在且有权限读写
    }

    /**
     * 判断SD卡挂载
     *
     * @date 2015年11月11日15:23:53
     * @author qinqin
     */
    public static boolean getSDCardWorking(Context context) {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED); // 判断sd卡是否存在且有权限读写
    }


    public long getPathSize(String psth) {
        long size = 0;
        if (!getSDCardWorking()) {
            return 0;
        }
        File file = new File(psth);
        if (!file.exists() && !file.isDirectory()) {
            Log.d("BaseUtils", "路径不存在");
            return 0;
        } else {
            Log.d("BaseUtils", "路径存在");
            File[] files = file.listFiles();
            for (File file2 : files) {
                try {
                    FileInputStream fis = null;
                    fis = new FileInputStream(file2);
                    size = fis.available();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return size;
    }

    /**
     * 获取外部存储路径
     *
     * @date 2015年11月11日15:28:58
     * @author qinqin
     */
    public String getSDCardPath() {
        if (getSDCardWorking()) {
            File sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
            return sdDir.toString();
        }
        return null;
    }

    /**
     * 获取缓存路径
     *
     * @date 2015年11月11日15:28:58
     * @author qinqin
     */
    public static File getCache() {
        if (getSDCardWorking()) {
            File sdDir = new File(Environment.getExternalStorageDirectory()
                    .getPath() + "/YSTStore");// 获取跟目录
            return sdDir;
        }
        return null;
    }

    /**
     * 保存图片为文件
     *
     * @param photo
     * @param capturePath
     * @return
     * @throws IOException
     */
    public boolean saveImage(Bitmap photo, String capturePath)
            throws IOException {
        File f = new File(capturePath);
        FileOutputStream out = null;
        f.createNewFile();
        out = new FileOutputStream(capturePath);
        photo.compress(Bitmap.CompressFormat.PNG, 100, out);
        out.flush();
        out.close();
        return true;
    }

    /**
     * 2016年11月14日13:03:57
     */
    private final String TAG = "FileUtil";
    private String pathDiv = "/";
    private File cacheDir = !isExternalStorageWritable() ? mContext.getFilesDir() : mContext.getExternalCacheDir();


    private FileUtil() {
            /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }


    /**
     * 创建临时文件
     *
     * @param type 文件类型
     */
    public File getTempFile(FileType type) {
        try {
            File file = File.createTempFile(type.toString(), null, cacheDir);
            file.deleteOnExit();
            return file;
        } catch (IOException e) {
            return null;
        }
    }


    /**
     * 获取缓存文件地址
     */
    public String getCacheFilePath(String fileName) {
        return cacheDir.getAbsolutePath() + pathDiv + fileName;
    }


    /**
     * 判断缓存文件是否存在
     */
    public boolean isCacheFileExist(String fileName) {
        File file = new File(getCacheFilePath(fileName));
        return file.exists();
    }

    /**
     * 压缩图片
     *
     * @param path
     * @return
     */
    public static Bitmap getPhotoBit(String path) {
        Bitmap picture = null;
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        BitmapFactory.Options ops = new BitmapFactory.Options();
        ops.inSampleSize = 4;
        picture = BitmapFactory.decodeFile(path, ops);
        return picture;
    }

    /**
     * 将图片存储为文件
     *
     * @param bitmap 图片
     */
    public String createFiles(Bitmap bitmap, String filename) {
        File f = new File(cacheDir, filename);
        try {
            if (!f.exists())
                f.createNewFile();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
            byte[] bitmapdata = bos.toByteArray();
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            Log.e(TAG, "create bitmap file error" + e);
        }
        if (f.exists()) {
            return f.getAbsolutePath();
        }
        return null;
    }

    /**
     * 压缩图片
     *
     * @param path
     * @return
     */
    public Bitmap getPhotoBitmap(String path) {
        Bitmap picture = null;
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        BitmapFactory.Options ops = new BitmapFactory.Options();
        ops.inSampleSize = 4;
        picture = BitmapFactory.decodeFile(path, ops);
        return picture;
    }

    /**
     * 将图片存储为文件
     *
     * @param bitmap 图片
     */
    public String createFile(Bitmap bitmap, String filename) {
        File f = new File(cacheDir, filename);
        try {
            if (!f.exists())
                f.createNewFile();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
            byte[] bitmapdata = bos.toByteArray();
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            Log.e(TAG, "create bitmap file error" + e);
        }
        if (f.exists()) {
            return f.getAbsolutePath();
        }
        return null;
//        File f = new File(cacheDir, filename);
//        try {
//            if (f.createNewFile()) {
//                ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
//                byte[] bitmapdata = bos.toByteArray();
//                FileOutputStream fos = new FileOutputStream(f);
//                fos.write(bitmapdata);
//                fos.flush();
//                fos.close();
//            }
//        } catch (IOException e) {
//            Log.e(TAG, "create bitmap file error" + e);
//        }
//        if (f.exists()) {
//            return f.getAbsolutePath();
//        }
//        return null;
    }

    /**
     * 将数据存储为文件
     *
     * @param data 数据
     */
    public void createFile(byte[] data, String filename) {
        File f = new File(cacheDir, filename);
        try {
            if (f.createNewFile()) {
                FileOutputStream fos = new FileOutputStream(f);
                fos.write(data);
                fos.flush();
                fos.close();
            }
        } catch (IOException e) {
            Log.e(TAG, "create file error" + e);
        }
    }


    /**
     * 将数据存储为文件
     *
     * @param data     数据
     * @param fileName 文件名
     * @param type     文件类型
     */
    public boolean createFile(byte[] data, String fileName, String type) {
        if (isExternalStorageWritable()) {
            File dir = mContext.getExternalFilesDir(type);
            if (dir != null) {
                File f = new File(dir, fileName);
                try {
                    if (f.createNewFile()) {
                        FileOutputStream fos = new FileOutputStream(f);
                        fos.write(data);
                        fos.flush();
                        fos.close();
                        return true;
                    }
                } catch (IOException e) {
                    Log.e(TAG, "create file error" + e);
                    return false;
                }
            }
        }
        return false;
    }


    /**
     * 从URI获取图片文件地址
     *
     * @param context 上下文
     * @param uri     文件uri
     */
    public String getImageFilePath(Context context, Uri uri) {
        if (uri == null) {
            return null;
        }
        String path = null;
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isKitKat) {
            if (isMediaDocument(uri)) {
                try {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{
                            split[1]
                    };
                    path = getDataColumn(context, contentUri, selection, selectionArgs);
                } catch (IllegalArgumentException e) {
                    path = null;
                }
            }
        }
        if (path == null) {
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = ((Activity) context).managedQuery(uri, projection, null, null, null);
            if (cursor != null) {
                int column_index = cursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                return cursor.getString(column_index);
            }

            path = null;
        }
        return path;
    }


    /**
     * 从URI获取文件地址
     *
     * @param context 上下文
     * @param uri     文件uri
     */
    public String getFilePath(Context context, Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }


    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    private static String getDataColumn(Context context, Uri uri, String selection,
                                        String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * 判断外部存储是否可用
     */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
//        Log.e(TAG, "ExternalStorage not mounted");
        return false;
    }

    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }


    public enum FileType {
        IMG,
        AUDIO,
        VIDEO,
        FILE,
    }

    public final String IMGE_PATH_SDCARD = Environment
            .getExternalStorageDirectory()
            + "/"
            + "sheiding"
            + "/ImgCache";// 默认图片缓存地址
}
