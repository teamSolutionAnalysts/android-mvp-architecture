package com.sa.baseproject.imagepicker;

import android.content.Intent;

public interface ImagePickerInterface {

    void handleCamera(Intent takePictureIntent);

    void handleGallery(Intent galleryPickerIntent);

}
