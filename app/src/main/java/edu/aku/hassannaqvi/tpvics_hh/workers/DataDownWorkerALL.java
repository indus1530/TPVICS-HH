package edu.aku.hassannaqvi.tpvics_hh.workers;

import android.content.Context;

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
import edu.aku.hassannaqvi.tpvics_hh.utils.shared.Keys;
import edu.aku.hassannaqvi.tpvics_hh.utils.shared.ServerSecurity;
import timber.log.Timber;


public class DataDownWorkerALL extends Worker {

    private final String TAG = "DataWorkerEN()";

    private final int position;
    HttpURLConnection urlConnection;
    private final String uploadTable;
    private final String uploadWhere;
    private final NotificationUtils notify;

    public DataDownWorkerALL(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        uploadTable = workerParams.getInputData().getString("table");
        position = workerParams.getInputData().getInt("position", -2);
        Timber.tag(TAG).d("DataDownWorkerALL: position %s", position);
        //uploadColumns = workerParams.getInputData().getString("columns");
        uploadWhere = workerParams.getInputData().getString("where");

        notify = new NotificationUtils(context, position + 1);
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

        Timber.tag(TAG).d("doWork: Starting");
        String nTitle = uploadTable + " : Data Upload";
        notify.displayNotification(nTitle, "Initializing download data connection");

        StringBuilder result = new StringBuilder();

        URL url;
        Data data;
        try {
            url = new URL(MainApp._HOST_URL + MainApp._SERVER_GET_URL);
            Timber.tag(TAG).d("doWork: Connecting...");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(100000 /* milliseconds */);
            urlConnection.setConnectTimeout(150000 /* milliseconds */);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("USER_AGENT", "SAMSUNG SM-T295");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("charset", "utf-8");
            urlConnection.setUseCaches(false);
            urlConnection.connect();
            Timber.tag(TAG).d("downloadURL: %s", url);

            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());

            JSONObject jsonTable = new JSONObject();
            JSONArray jsonParam = new JSONArray();

            jsonTable.put("table", uploadTable);
            //jsonTable.put("select", uploadColumns);
            jsonTable.put("filter", uploadWhere);
            //jsonTable.put("limit", "3");
            //jsonTable.put("orderby", "rand()");
            //jsonSync.put(uploadData);
            jsonParam.put(jsonTable);
            // .put(jsonSync);

            Timber.tag(TAG).d("Upload Begins: %s", jsonTable.toString());


            wr.writeBytes(ServerSecurity.INSTANCE.encrypt(String.valueOf(jsonTable), Keys.INSTANCE.apiKey()));
            wr.flush();
            wr.close();

            Timber.tag(TAG).d("doInBackground: %s", urlConnection.getResponseCode());

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Timber.tag(TAG).d("Connection Response: %s", urlConnection.getResponseCode());
                notify.displayNotification(nTitle, "Start downloading data");

                int length = urlConnection.getContentLength();
                Timber.tag(TAG).d("Content Length: %s", length);

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);

                }
                result = new StringBuilder(ServerSecurity.INSTANCE.decrypt(result.toString(), Keys.INSTANCE.apiKey()));
                if (result.toString().equals("[]")) {
                    notify.displayNotification(nTitle, "No data received from server");
                    Timber.tag(TAG).d("No data received from server: %s", result);
                    data = new Data.Builder()
                            .putString("error", "No data received from server: " + result)
                            .putInt("position", this.position)
                            .build();
                    return Result.failure(data);
                }
                Timber.d("doWork(EN): %s", result.toString());
            } else {
                Timber.d("Connection Response (Server Failure): %s", urlConnection.getResponseCode());
                notify.displayNotification(nTitle, "Connection Response (Server Failure): " + urlConnection.getResponseCode());
                data = new Data.Builder()
                        .putString("error", String.valueOf(urlConnection.getResponseCode()))
                        .putInt("position", this.position)
                        .build();
                return Result.failure(data);
            }
        } catch (java.net.SocketTimeoutException e) {
            Timber.d("doWork (Timeout): %s", e.getMessage());
            notify.displayNotification(nTitle, "Timeout Error: " + e.getMessage());
            data = new Data.Builder()
                    .putString("error", String.valueOf(e.getMessage()))
                    .putInt("position", this.position)
                    .build();
            return Result.failure(data);

        } catch (IOException | JSONException e) {
            Timber.d("doWork (IO Error): %s", e.getMessage());
            notify.displayNotification(nTitle, "IO Error: " + e.getMessage());
            data = new Data.Builder()
                    .putString("error", String.valueOf(e.getMessage()))
                    .putInt("position", this.position)
                    .build();

            return Result.failure(data);

        }

        notify.displayNotification(nTitle, String.format(Locale.ENGLISH, "Downloaded %d records", result.length()));
        ///BE CAREFULL DATA.BUILDER CAN HAVE ONLY 1024O BYTES. EACH CHAR HAS 8 bits
        MainApp.downloadData[this.position] = String.valueOf(result);

        data = new Data.Builder()
                //     .putString("data", String.valueOf(result))
                .putInt("position", this.position)
                .build();


        notify.displayNotification(nTitle, "Downloaded successfully");
        Timber.d("doWork: %s", result);
        Timber.d("doWork (success) : position %s", data.getInt("position", -1));
        return Result.success(data);
    }

}