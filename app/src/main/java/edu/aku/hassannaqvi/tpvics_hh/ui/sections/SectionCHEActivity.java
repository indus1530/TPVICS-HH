package edu.aku.hassannaqvi.tpvics_hh.ui.sections;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.hassannaqvi.tpvics_hh.R;
import edu.aku.hassannaqvi.tpvics_hh.contracts.ChildContract;
import edu.aku.hassannaqvi.tpvics_hh.core.DatabaseHelper;
import edu.aku.hassannaqvi.tpvics_hh.core.MainApp;
import edu.aku.hassannaqvi.tpvics_hh.databinding.ActivitySectionChEBinding;
import edu.aku.hassannaqvi.tpvics_hh.ui.other.ChildEndingActivity;
import edu.aku.hassannaqvi.tpvics_hh.ui.other.TakePhoto;
import edu.aku.hassannaqvi.tpvics_hh.utils.JSONUtils;

import static edu.aku.hassannaqvi.tpvics_hh.core.MainApp.child;
import static edu.aku.hassannaqvi.tpvics_hh.utils.UtilKt.openChildEndActivity;

public class SectionCHEActivity extends AppCompatActivity {

    ActivitySectionChEBinding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_ch_e);
        bi.setCallback(this);

        setupListeners();

    }

    private void setupListeners() {

    }

    private boolean UpdateDB() {
        DatabaseHelper db = MainApp.appInfo.getDbHelper();
        int updcount = db.updatesChildColumn(ChildContract.SingleChild.COLUMN_SCC, child.getsCC());
        if (updcount == 1) {
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void SaveDraft() throws JSONException {

        JSONObject f1 = new JSONObject();

        f1.put("im26a", bi.im26a.getText().toString());
        f1.put("im26b", bi.im26b.getText().toString());
        f1.put("im26c", bi.im26c.getText().toString());
        f1.put("im26d", bi.im26d.getText().toString());

        try {
            JSONObject json_merge = JSONUtils.mergeJSONObjects(new JSONObject(child.getsCC()), f1);

            child.setsCC(String.valueOf(json_merge));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.fldGrpSectionCHE);
    }

    public void BtnContinue() {

        if (formValidation()) {
            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {
                finish();
                startActivity(new Intent(this, ChildEndingActivity.class).putExtra("complete", true));

            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void BtnEnd() {
        openChildEndActivity(this);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Back Press Not Allowed", Toast.LENGTH_SHORT).show();
    }


    public void takePhoto(View view) {

        Intent intent = new Intent(SectionCHEActivity.this, TakePhoto.class);

        intent.putExtra("picID", MainApp.fc.getClusterCode() + "_" + MainApp.fc.getHhno() + "_" + MainApp.child.getChildSerial() + "_");
        //intent.putExtra("picID", "901001" + "_" + "A-0001-001" + "_" + "1" + "_");

        //intent.putExtra("childName", "Hassan");
        intent.putExtra("childName", MainApp.child.getChildName());

        if (view.getId() == bi.frontPhoto.getId()) {
            intent.putExtra("picView", "front".toUpperCase());
            startActivityForResult(intent, 1); // Activity is started with requestCode 1 = Front
        } else {
            intent.putExtra("picView", "back".toUpperCase());
            startActivityForResult(intent, 2); // Activity is started with requestCode 2 = Back
        }
    }

    // Call Back method  to get the Message form other Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Toast.makeText(this, requestCode + "_" + resultCode, Toast.LENGTH_SHORT).show();

        String fileName = data.getStringExtra("FileName");

        // Check if the requestCode 1 = Front : 2 = Back -- resultCode 1 = Success : 2 = Failure
        // Results received with requestCode 1 = Front

        if (requestCode == 1 && resultCode == 1) {
            Toast.makeText(this, "Photo Taken", Toast.LENGTH_SHORT).show();

            bi.frontFileName.setText(fileName);
            bi.frontPhoto.setEnabled(false);


        } else if (requestCode == 1 && resultCode != 1) {
            Toast.makeText(this, "Photo Cancelled", Toast.LENGTH_SHORT).show();

            //TODO: Implement functionality below when photo was not taken
            // ...
            bi.frontFileName.setText("Photo not taken.");

        }

        // Results received with requestCode 2 = Back
        if (requestCode == 2 && resultCode == 1) {
            Toast.makeText(this, "Photo Taken", Toast.LENGTH_SHORT).show();

            bi.backFileName.setText(fileName);
            bi.backPhoto.setEnabled(false);
        } else if (requestCode == 2 && resultCode != 1) {

            Toast.makeText(this, "Photo Cancelled", Toast.LENGTH_SHORT).show();

            //TODO: Implement functionality below when photo was not taken
            // ...
            bi.backFileName.setText("Photo not taken.");

        }
    }
}
