package com.sa.baseproject.utils;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.sa.baseproject.base.AppActivity;
import com.sa.baseproject.utils.PermissionUtils.PermissionGranted;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

/**
 * Created by sa on 05/04/17.
 *
 */

public class LocationUtils implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, PermissionGranted {

    private static final String TAG = "location-updates-sample";
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 5 * 1000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mCurrentLocation;
    private double latitude;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    private double longitude;

    private LocationSettingsRequest.Builder builder;

    private Context context;
    private PermissionUtils permissionUtils;

    public LocationUtils(final Context context) {
        this.context = context;
        permissionUtils = ((AppActivity) context).getPermissionUtils();
        permissionUtils.setPermissionGranted(this);
        buildGoogleApiClient();
        checkGPS();
        onStart();
    }

    /**
     * Builds a GoogleApiClient. Uses the {@code #addApi} method to request the
     * LocationServices API.
     */
    private synchronized void buildGoogleApiClient() {
        Log.i(TAG, "Building GoogleApiClient");
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        createLocationRequest();
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        //**************************
        builder.setAlwaysShow(true); //this is the key ingredient
        //**************************

    }

    private void startLocationUpdates() {
        if (!permissionUtils.checkPermission(ACCESS_COARSE_LOCATION) && !permissionUtils.checkPermission(ACCESS_FINE_LOCATION)) {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);
        } else {
            permissionUtils.checkPermission(ACCESS_COARSE_LOCATION, Constants.REQUEST_CODE_ASK_PERMISSIONS);
        }
    }

    private void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    private void onStart() {
        mGoogleApiClient.connect();
    }

    public void onResume() {
        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
        }
    }

    public void onPause() {
        if (mGoogleApiClient.isConnected()) {
            stopLocationUpdates();
        }
    }

    public void onStop() {
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.i(TAG, "Connected to GoogleApiClient");
        if (mCurrentLocation == null) {
            if (permissionUtils.checkPermission(ACCESS_COARSE_LOCATION) && permissionUtils.checkPermission(ACCESS_FINE_LOCATION)) {
                permissionUtils.checkPermission(ACCESS_COARSE_LOCATION, Constants.REQUEST_CODE_ASK_PERMISSIONS);
                return;
            }
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            getLocation();

        }
        startLocationUpdates();
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
        getLocation();
    }

    @Override
    public void onConnectionSuspended(int cause) {
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    private void checkGPS() {
        if (mGoogleApiClient != null) {
            PendingResult<LocationSettingsResult> result =
                    LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(@NonNull LocationSettingsResult result) {
                    final Status status = result.getStatus();
//                    final LocationSettingsStates state = result.getLocationSettingsStates();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            createLocationRequest();
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                status.startResolutionForResult(
                                        (Activity) context, 1000);
                            } catch (IntentSender.SendIntentException e) {
                                e.printStackTrace();
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            break;
                    }
                }
            });
        }
    }

    private void getLocation() {
        if (mCurrentLocation != null) {
            longitude = mCurrentLocation.getLongitude();
            latitude = mCurrentLocation.getLatitude();
            if (longitude != 0.0 && latitude != 0.0) {
                LogUtils.e("onConnected Latitude : " + latitude + " Longitude : " + longitude);
            }
        }
    }


    @Override
    public void onPermissionGranted(int requestCode) {
        onStart();
    }
}
