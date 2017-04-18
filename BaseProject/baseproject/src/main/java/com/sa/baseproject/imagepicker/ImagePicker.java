package com.sa.baseproject.imagepicker;


import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;


import com.sa.baseproject.BuildConfig;
import com.sa.baseproject.utils.DialogUtils;
import com.sa.baseproject.utils.ProgressUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.List;

public class ImagePicker {

    private Context context;
    private ImagePickerInterface imagePickerInterface;
    private String[] array = new String[]{"Camera", "Gallery"};
    private String title = "Complete action using";
    public static final int GALLERY_REQUEST = 123;
    public static final int CAMERA_REQUEST = 456;
    public static final int CROP_REQUEST = 789;
    private OnGetBitmapListener onGetBitmapListener;
    private Uri uri = null;
    private File file;

    public ImagePicker(Context context, ImagePickerInterface imagePickerInterface) {
        this.context = context;
        this.imagePickerInterface = imagePickerInterface;
    }

    public void createImageChooser() {
        List<String> list = Arrays.asList(array);
        CharSequence[] cs = list.toArray(new CharSequence[list.size()]);

        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(title);
        dialog.setSingleChoiceItems(cs, -1, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (which == 0) {
                    if (imagePickerInterface != null)
                        imagePickerInterface.handleCamera(generateCameraPickerIntent());
                } else {
                    if (imagePickerInterface != null)
                        imagePickerInterface.handleGallery(generateGalleryPickerIntent());
                }
            }
        });

        final AlertDialog alertDialog = dialog.create();
        if (!alertDialog.isShowing()) {
            alertDialog.show();
        }
    }

    /**
     * Generate Intent to open camera
     *
     * @return : {@link Intent} to open camera.
     */
    private Intent generateCameraPickerIntent() {
        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = new File(createOrGetProfileImageDir(context), System.currentTimeMillis() + ".jpg");
        uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        return intent;
    }

    /**
     * Generate intent to open gallery to choose image
     * <p/>
     * :{@link Context} to create intent
     *
     * @return : {@link Intent} to open gallery
     */
    private Intent generateGalleryPickerIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, Uri.fromFile(Environment.getExternalStorageDirectory()));
        intent.setType("image/*");
        return intent;
    }

    private boolean sdCardMounted() {
        boolean isMediaMounted = false;
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            isMediaMounted = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            isMediaMounted = false;
        } else if (Environment.MEDIA_CHECKING.equals(state)) {
            isMediaMounted = false;
        } else if (Environment.MEDIA_NOFS.equals(state)) {
            isMediaMounted = false;
        } else if (Environment.MEDIA_REMOVED.equals(state)) {
            isMediaMounted = false;
        } else if (Environment.MEDIA_SHARED.equals(state)) {
            isMediaMounted = false;
        } else if (Environment.MEDIA_UNMOUNTABLE.equals(state)) {
            isMediaMounted = false;
        } else if (Environment.MEDIA_UNMOUNTED.equals(state)) {
            isMediaMounted = false;
        }
        return isMediaMounted;
    }

    public Bitmap preventAutoRotate(Bitmap bitmap, int orientation) {

        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public String getPathFromURI(final Uri uri) {

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
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

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
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    private boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    private boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    private String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

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

    public File createOrGetProfileImageDir(final Context context) {
        final File dir = getProfileDirectory(context);
        if (!dir.exists()) {
            dir.mkdir();
        }
        return new File(dir.getAbsolutePath());
    }

    private File getProfileDirectory(final Context context) {
        final String root = Environment.getExternalStorageDirectory().toString();
        final File rootDir = new File(root + "/Android/data/" + context.getPackageName());

        if (!rootDir.exists()) {
            rootDir.mkdir();
        }

        final File dir = new File(rootDir + "/profile/");

        if (!dir.exists()) {
            dir.mkdir();
        }
        return dir;
    }


    public void getBitmap(Uri uri, boolean isFromGallery) {
        if (isFromGallery) {
            final String imagePath = getPathFromURI(uri);
            final String fileExtension = imagePath.substring(imagePath.lastIndexOf("."));
            file = new File(imagePath);
            if (file.exists()) {
                new SaveBitmapAsyncTask(imagePath, fileExtension).execute();
            }
        } else {
            final String imagePath = file.getAbsolutePath();
            new OptimizeBitmapTask().execute(imagePath);
        }
    }

    public interface OnGetBitmapListener {
        void onGetBitmap(Bitmap bitmap);
    }

    private class SaveBitmapAsyncTask extends AsyncTask<Void, Void, Void> {
        private Bitmap bitmap;
        private String path;
        private String fileExtension;

        public SaveBitmapAsyncTask(String path, String fileExtension) {
            this.path = path;
            this.fileExtension = fileExtension;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                final File sourceFile = new File(path);
                final File destinationFile = new File(createOrGetProfileImageDir(context), System.currentTimeMillis() + fileExtension);
                if (destinationFile.exists())
                    destinationFile.delete();


                FileChannel src = new FileInputStream(sourceFile).getChannel();
                FileChannel dst = new FileOutputStream(destinationFile).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();

                final String imagePath = destinationFile.getAbsolutePath();

                bitmap = getOptimizedBitmap(imagePath);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void s) {
            super.onPostExecute(s);
            if (onGetBitmapListener != null) {
                if (bitmap != null)
                    onGetBitmapListener.onGetBitmap(bitmap);
                else
                    onGetBitmapListener.onGetBitmap(null);
            }

        }
    }

    public void setOnGetBitmapListener(OnGetBitmapListener onGetBitmapListener) {
        this.onGetBitmapListener = onGetBitmapListener;
    }

    private Bitmap getOptimizedBitmap(String path) {
        try {
            final Bitmap bitmap = BitmapFactory.decodeFile(path);
            Bitmap scaledBitmap;
            if (bitmap.getWidth() > 1500 || bitmap.getHeight() > 1500) {
                scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / 2, bitmap.getHeight() / 2, true);
            } else
                scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

            final ExifInterface exif = new ExifInterface(path);
            final int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

            scaledBitmap = preventAutoRotate(scaledBitmap, orientation);

            final FileOutputStream out = new FileOutputStream(new File(path));
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();

            return scaledBitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void onActivityResult(int requestCode, Uri uri, Intent data) {
        if (requestCode == ImagePicker.CAMERA_REQUEST) {
            getBitmap(uri, false);
        } else if (requestCode == ImagePicker.GALLERY_REQUEST) {
            this.uri = data.getData();
            getBitmap(uri, true);
        }
    }

    private class OptimizeBitmapTask extends AsyncTask<String, Void, Bitmap> {

        private String path;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressUtils.getInstance(context).show();
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            path = params[0];
            return getOptimizedBitmap(path);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            ProgressUtils.getInstance(context).close();
            if (onGetBitmapListener != null)
                onGetBitmapListener.onGetBitmap(bitmap);
        }
    }

    /**
     * We can get image in file format using this method.
     *
     * @return
     */
    public File getImageFile() {
        return file;
    }
}
