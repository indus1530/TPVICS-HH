package edu.aku.ramshasaeed.mnch.sync;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;


public class SyncAllData extends AsyncTask<String, String, String> {

    HttpURLConnection urlConnection;
    private String TAG = "";
    private Context mContext;
    private ProgressDialog pd;
    private String syncClass, URL,updateSyncClass;
    private Collection dbData;
    private Class contractClass;


    public SyncAllData(Context context, String syncClass,String updateSyncClass,Class contractClass, String url, Collection dbData) {
        mContext = context;
        this.syncClass = syncClass;
        this.updateSyncClass = updateSyncClass;
        this.URL = url;
        this.contractClass = contractClass;
        this.dbData = dbData;

        TAG = "Get" + syncClass;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(mContext);
        pd.setTitle("Syncing " + syncClass);
        pd.setMessage("Getting connected to server...");
        pd.show();

    }

    @Override
    protected String doInBackground(String... args) {
        Log.d(TAG, "doInBackground: URL " + URL);
        return downloadUrl(contractClass);
    }
        /*
        StringBuilder result = new StringBuilder();

        URL url = null;
        try {
            url = new URL(URL);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 *//* milliseconds *//*);
            urlConnection.setConnectTimeout(15000 *//* milliseconds *//*);
            Log.d(TAG, "doInBackground: " + urlConnection.getResponseCode());
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) {
                    Log.i(TAG, syncClass + " In: " + line);
                    result.append(line);
                }
            }
        } catch (java.net.SocketTimeoutException e) {
            return null;
        } catch (java.io.IOException e) {
            return null;
        } finally {
            urlConnection.disconnect();
        }


        return result.toString();
    }*/
        private String downloadUrl(Class<?> contractClass) {
            String line = "No Response";

            Collection<?> DBData = dbData; // pass data that's coming from db

            Log.d(TAG, String.valueOf(DBData.size()));

            if (DBData.size() > 0) {

                HttpURLConnection connection = null;
                try {
                    String request = URL;

                    URL url = new URL(request);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                    int HttpResult = connection.getResponseCode();
                    if (HttpResult == HttpURLConnection.HTTP_OK) {
                        JSONArray jsonSync = new JSONArray();
                        connection = (HttpURLConnection) url.openConnection();

                        connection.setDoOutput(true);
                        connection.setDoInput(true);
                        connection.setInstanceFollowRedirects(false);
                        connection.setRequestMethod("POST");
                        connection.setRequestProperty("Content-Type", "application/json");
                        connection.setRequestProperty("charset", "utf-8");
                        connection.setUseCaches(false);
                        connection.connect();

                        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                        try {
                            while (contractClass != null) {
                                for (Method method : contractClass.getDeclaredMethods()) {
                                    String methodName = method.getName();
                                    if (methodName.equals("toJSONObject")) {
                                        for (Object fc : DBData) {
                                            jsonSync.put(fc.getClass().getMethod(methodName).invoke(fc));
                                        }
                                        break;
                                    }
                                }
                                break;
                            }

                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }


                        wr.writeBytes(jsonSync.toString().replace("\uFEFF", "") + "\n");
                        wr.flush();


                        BufferedReader br = new BufferedReader(new InputStreamReader(
                                connection.getInputStream(), "utf-8"));
                        StringBuffer sb = new StringBuffer();

                        while ((line = br.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        br.close();

                        System.out.println("" + sb.toString());
                        return sb.toString();
                    } else {
                        System.out.println(connection.getResponseMessage());
                        return connection.getResponseMessage();
                    }
                } catch (MalformedURLException e) {

                    e.printStackTrace();
                } catch (IOException e) {

                    e.printStackTrace();
                } finally {
                    if (connection != null)
                        connection.disconnect();
                }
            } else

            {
                return "No new records to sync";
            }
            return line;
        }

    @Override
    protected void onPostExecute(String result) {
        int sSynced = 0;
        int sDuplicate = 0;
        String sSyncedError = "";

        //Do something with the JSON string
        if (result != null) {
            String _json = result;
            if (_json.length() > 0) {
                JSONArray json = null;
                try {
                    json = new JSONArray(result);

//                    DatabaseHelper db = new DatabaseHelper(mContext); // Database Helper

                    for (int i = 0; i < json.length(); i++) {
                        int id  = 0;
                        JSONObject jsonObject = new JSONObject(json.getString(i));

                        if (jsonObject.getString("status").equals("1") && jsonObject.getString("error").equals("0")) {

                            //  db.updateSyncedChildForm(jsonObject.getString("id"));  // UPDATE SYNCED

                            id = Integer.parseInt(jsonObject.getString("id"));

                            sSynced++;
                        } else if (jsonObject.getString("status").equals("2") && jsonObject.getString("error").equals("0")) {
                            //db.updateSyncedChildForm(jsonObject.getString("id")); // UPDATE DUPLICATES

                            id = Integer.parseInt(jsonObject.getString("id"));

                            sDuplicate++;
                        } else {
                            sSyncedError += "\nError: " + jsonObject.getString("message");
                        }
                        switch (updateSyncClass) {
                            case "updateSyncedForms":
                                UpdateFncs.updateSyncedForms(id);
                                break;
                        }
                    }
                    Toast.makeText(mContext, syncClass + " synced: " + sSynced + "\r\n\r\n Errors: " + sSyncedError, Toast.LENGTH_SHORT).show();


                    pd.setMessage(syncClass + " synced: " + sSynced + "\r\n\r\n Duplicates: " + sDuplicate + "\r\n\r\n Errors: " + sSyncedError);
                    pd.setTitle("Done uploading +" + syncClass + " data");
                    pd.show();
                    //syncStatus.setText(syncStatus.getText() + "\r\nDone uploading +" + syncClass + " data");

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, "Failed Sync " + result, Toast.LENGTH_SHORT).show();

                    pd.setMessage(result);
                    pd.setTitle(syncClass + " Sync Failed");
                    pd.show();
                    //syncStatus.setText(syncStatus.getText() + "\r\n" + syncClass + " Sync Failed");
                }

            } else {
                pd.setMessage("Received: " + _json.length() + "");
                pd.show();
            }
        } else {
            pd.setTitle("Connection Error");
            pd.setMessage("Server not found!");
            pd.show();
        }
    }

}
