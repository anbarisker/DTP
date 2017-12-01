package com.hci.eea.dtp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by anbarisker on 28/11/17.
 */
public class PushUpCounter extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mProximity;

    RelativeLayout mRelativeLayout;
    private static final int SENSOR_SENSITIVITY = 4;

    private TextView txt_pushup_count, txt_time_count;
    private Button btn_go;
    CountDownTimer waitTimer;
    int counter=0;
    boolean checker = false;

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mProximity, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.push_up);
       // overridePendingTransition(R.anim.right_go_in, R.anim.right_go_out);

        mRelativeLayout = (RelativeLayout) findViewById(R.id.activity_main);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);


       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_go = (Button) findViewById(R.id.btn_go);
        btn_go.setText("Start");
        txt_pushup_count = (TextView) findViewById(R.id.txt_pushcount);
        txt_time_count = (TextView) findViewById(R.id.txt_time);

        start_pushup();
    }

    public void start_pushup() {

        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(btn_go.getText()!= "Done" && btn_go.getText()!="Started") {
                    new AlertDialog.Builder(PushUpCounter.this)
                            .setTitle("Push Up!")
                            .setMessage("Are you sure you want to Start?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    // Toast.makeText(PushUpCounter.this, "FML", Toast.LENGTH_SHORT).show();
                                    startPush_count();
                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();
                }
                else
                {
                    Intent intent = new Intent(PushUpCounter.this, PushUpResult.class);
                    intent.putExtra("EXTRA_RESULT_VALUE", txt_pushup_count.getText());
                    PushUpCounter.this.startActivity(intent);
                }


            }

        });
    }

    public void startPush_count()
    {
        btn_go.setEnabled(false);
        btn_go.setText("Started");
        checker = true;
        waitTimer = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                txt_time_count.setText(millisUntilFinished / 1000+"s");
            }

            public void onFinish() {
                txt_time_count.setText("0");
                checker = false;
                btn_go.setEnabled(true);
                btn_go.setText("Done");
                //done
            }
        }.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (checker == true)
        {
            if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                if (event.values[0] >= -SENSOR_SENSITIVITY && event.values[0] <= SENSOR_SENSITIVITY) {
                    // near

                    counter++;
                    txt_pushup_count.setText(String.valueOf(counter));
                    // playSound(this);
                    playAlertTone(this);
                    //customSnackBar(getString(R.string.proximity_sensor_activity_near), R.color.colorGreen);
                } else {
                    // far
                    //customSnackBar(getString(R.string.proximity_sensor_activity_far), R.color.colorAccent);
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }




    public void playSound(Context context) throws IllegalArgumentException,
            SecurityException,
            IllegalStateException,
            IOException {

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        MediaPlayer mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setDataSource(context, soundUri);

        final AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
            mMediaPlayer.setLooping(false);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        }
    }

    public  void playAlertTone(final Context context){


        Thread t = new Thread(){
            public void run(){
                MediaPlayer player = null;
                int countBeep = 0;
                while(countBeep<1){
                    player = MediaPlayer.create(context,R.raw.beep);
                    player.start();
                    countBeep++;
                    try {

                        // 100 milisecond is duration gap between two beep
                        Thread.sleep(player.getDuration());
                        player.release();
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                }
            }
        };

        t.start();

    }
}
