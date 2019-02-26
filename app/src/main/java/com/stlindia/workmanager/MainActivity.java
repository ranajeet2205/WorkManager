package com.stlindia.workmanager;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class MainActivity extends AppCompatActivity {

    RadioGroup networkOptions;
    Button scheduleJob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scheduleJob = findViewById(R.id.schedule_job);



        scheduleJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                networkOptions = findViewById(R.id.networkOptions);
                int selectedNetworkID = networkOptions.getCheckedRadioButtonId();
               // int selectedNetworkOption = JobInfo.NETWORK_TYPE_NONE;

                NetworkType networkType = NetworkType.NOT_REQUIRED;

                switch(selectedNetworkID){
                    case R.id.rad_nonetwork:
                        networkType = NetworkType.NOT_REQUIRED;
                        break;
                    case R.id.rad_any_network:
                        networkType = NetworkType.CONNECTED;
                        break;
                    case R.id.rad_wifi_network:
                        networkType = NetworkType.UNMETERED;
                        break;
                }

                //boolean conStraintSet = networkType!=NetworkType.NOT_REQUIRED;


                //Setting the constraints for the work manager
                Constraints myConstraints = new Constraints.Builder()
                        .setRequiredNetworkType(networkType)
                        // Many other constraints are available, see the
                        // Constraints.Builder reference
                        .build();


                //Making the onetime request to work manager
                OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(NotificationWorker.class).setConstraints(myConstraints).build();

                //Assigning work to work manager
                WorkManager.getInstance().enqueue(oneTimeWorkRequest);
            }

              /*  if (conStraintSet){
                    // Create a Constraints object that defines when the task should run


            }*/
        });
    }
}
