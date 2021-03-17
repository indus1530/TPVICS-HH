package edu.aku.hassannaqvi.tpvics_hh.workers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

import edu.aku.hassannaqvi.tpvics_hh.core.MainApp;
import edu.aku.hassannaqvi.tpvics_hh.utils.shared.ServerSecurity;


public class DataUpWorkerALL extends Worker {

    private final String TAG = "DataWorkerEN()";
    private final String uploadTable;
    private final JSONArray uploadData;
    private final int position;
    private final NotificationUtils notify;

    public DataUpWorkerALL(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        uploadTable = workerParams.getInputData().getString("table");
        position = workerParams.getInputData().getInt("position", -2);
        uploadData = MainApp.uploadData.get(position);

        Log.d(TAG, "Upload Begins uploadData.length(): " + uploadData.length());
        Log.d(TAG, "Upload Begins uploadData: " + uploadData);

        Log.d(TAG, "DataDownWorkerALL: position " + position);
        //uploadColumns = workerParams.getInputData().getString("columns");
        String uploadWhere = workerParams.getInputData().getString("where");

        notify = new NotificationUtils(context, 2);

    }

    /*
     * This method is responsible for doing the work
     * so whatever work that is needed to be performed
     * we will put it here
     *
     * For example, here I am calling the method displayNotification()
     * It will display a notification
     * So that we will understand the work is executed
     * */

    @NonNull
    @Override
    public Result doWork() {
        Data data;
        if (uploadData.length() == 0) {
            data = new Data.Builder()
                    .putString("error", "No new records to upload")
                    .putInt("position", this.position)
                    .build();

            return Result.failure(data);
        }
        Log.d(TAG, "doWork: Starting");
        String nTitle = uploadTable + " : Data Upload";
        notify.displayNotification(nTitle, "Initializing upload data connection");

        StringBuilder result = new StringBuilder();

        URL url;
        try {
            url = new URL(MainApp._HOST_URL + MainApp._SERVER_URL);
            Log.d(TAG, "doWork: Connecting...");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(100000 /* milliseconds */);
            urlConnection.setConnectTimeout(150000 /* milliseconds */);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("charset", "utf-8");
            urlConnection.setUseCaches(false);
            urlConnection.connect();
            Log.d(TAG, "downloadURL: " + url);

            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());

            JSONObject jsonTable = new JSONObject();
            JSONArray jsonParam = new JSONArray();

            jsonTable.put("table", uploadTable);
            Log.d(TAG, "doWork: " + uploadData);
            System.out.print("doWork: " + uploadData);
            //jsonSync.put(uploadData);
            jsonParam
                    .put(jsonTable)
                    .put(uploadData);

            Log.d(TAG, "Upload Begins Length: " + jsonParam.length());
            Log.d(TAG, "Upload Begins: " + jsonParam);

            wr.writeBytes(ServerSecurity.INSTANCE.encrypt(jsonParam.toString()));
            wr.flush();
            wr.close();

            Log.d(TAG, "doInBackground: " + urlConnection.getResponseCode());

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Log.d(TAG, "Connection Response: " + urlConnection.getResponseCode());
                notify.displayNotification(nTitle, "Start uploading data");

                int length = urlConnection.getContentLength();
                Log.d(TAG, "Content Length: " + length);

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);

                }
                notify.displayNotification(nTitle, "Upload data done");
                Log.d(TAG, "doWork(EN): " + result.toString());
            } else {

                Log.d(TAG, "Connection Response (Server Failure): " + urlConnection.getResponseCode());
                notify.displayNotification(nTitle, "Connection Response (Server Failure): " + urlConnection.getResponseCode());
                data = new Data.Builder()
                        .putString("error", String.valueOf(urlConnection.getResponseCode()))
                        .putInt("position", this.position)
                        .build();
                return Result.failure(data);
            }
        } catch (java.net.SocketTimeoutException e) {
            Log.d(TAG, "doWork (Timeout): " + e.getMessage());
            notify.displayNotification(nTitle, "Timeout Error: " + e.getMessage());
            data = new Data.Builder()
                    .putString("error", String.valueOf(e.getMessage()))
                    .putInt("position", this.position)
                    .build();
            return Result.failure(data);

        } catch (IOException | JSONException e) {
            Log.d(TAG, "doWork (IO Error): " + e.getMessage());
            notify.displayNotification(nTitle, "IO Error: " + e.getMessage());
            data = new Data.Builder()
                    .putString("error", String.valueOf(e.getMessage()))
                    .putInt("position", this.position)
                    .build();

            return Result.failure(data);
        }

        notify.displayNotification(nTitle, String.format(Locale.ENGLISH, "Uploaded %d records", result.length()));

        ///BE CAREFULL DATA.BUILDER CAN HAVE ONLY 1024O BYTES. EACH CHAR HAS 8 BYTES
        if (result.toString().length() > 10240) {
            data = new Data.Builder()
                    .putString("message", String.valueOf(result).substring(0, (10240 - 1) / 8))
                    .putInt("position", this.position)
                    .build();
        } else {
            data = new Data.Builder()
                    .putString("message", String.valueOf(result))
                    .putInt("position", this.position)
                    .build();
        }

        notify.displayNotification(nTitle, "Uploaded successfully");
        return Result.success(data);
    }
}