package com.hci.eea.dtp;

import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import android.text.style.BackgroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;


import static android.R.attr.data;

/**
 * Created by anbarisker on 28/11/17.
 */
public class PushUpResult extends AppCompatActivity {


    RelativeLayout mRelativeLayout;

    private ArrayList<String> data = new ArrayList<String>();
    private TextView txt_pushup_count, txt_time_count;
    private Button btn_home;
    private ListView lv_friends;
    CountDownTimer waitTimer;
    int counter=0;
    boolean checker = false;


    //get result


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.push_up_result);
       // overridePendingTransition(R.anim.right_go_in, R.anim.right_go_out);
        //mRelativeLayout = (RelativeLayout) findViewById(R.id.activity_main);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        btn_home = (Button) findViewById(R.id.btn_r_home);
        btn_home.setText("Home");
        txt_pushup_count = (TextView) findViewById(R.id.txt_pushcount_result);
        String result = getIntent().getStringExtra("EXTRA_RESULT_VALUE");
        txt_pushup_count.setText(result);
        lv_friends = (ListView) findViewById(R.id.list_sentToFriend);
        String[] names = {"Edmund","Edgar","Anba","Bob","Dude"};
        goHome();
        /*
        // storing string resources into Array
        String[] numbers = {"Edmund","Edgar","Anba","Bob","Dude"};

        // 1
        //final ArrayList<Recipe> recipeList = Recipe.getRecipesFromFile("recipes.json", this);
        // 2
        //String[] listItems = new String[recipeList.size()];
        String[] listItems = new String[numbers.length];
        // 3
        for(int i = 0; i < numbers.length; i++){
           // Recipe recipe = recipeList.get(i);
            //listItems[i] = recipe.title;
            listItems[i] = "\t\t"+numbers[i];

        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems)
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                // Get the current item from ListView
                View view = super.getView(position,convertView,parent);
                TextView textView=(TextView) view.findViewById(android.R.id.text1);
                if(position %2 == 1)
                {
                    // Set a background color for ListView regular row/item
                  //  view.setBackgroundColor(Color.parseColor("#FFB6B546"));

                }
                else
                {
                    // Set the background color for alternate row/item
                  //  view.setBackgroundColor(Color.parseColor("#FFCCCB4C"));
                    view.setBackgroundResource(R.drawable.list_view);
                    textView.setTextColor(Color.WHITE);



                }
                return view;
            }
        };

        // DataBind ListView with items from ArrayAdapter
        lv_friends.setAdapter(adapter);
*/
        ListView lv = (ListView) findViewById(R.id.list_sentToFriend);
        generateListContent(names);
        lv.setAdapter(new MyListAdaper(this, R.layout.friend_list_challenge, data));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              //  Toast.makeText(PushUpResult.this, "Challenge" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateListContent(String[] names) {
        for(int i = 0; i < names.length; i++) {
            data.add(names[i]);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class MyListAdaper extends ArrayAdapter<String> {
        private int layout;
        private List<String> mObjects;
        private MyListAdaper(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            mObjects = objects;
            layout = resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder mainViewholder = null;
            if(convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.title = (TextView) convertView.findViewById(R.id.list_item_text);
                viewHolder.title.setTextColor(Color.WHITE);
                viewHolder.button = (Button) convertView.findViewById(R.id.list_item_btn);
                convertView.setTag(viewHolder);
            }
            if(position %2 == 1)
            {
                // Set a background color for ListView regular row/item
                //  view.setBackgroundColor(Color.parseColor("#FFB6B546"));

            }
            else
            {
                // Set the background color for alternate row/item
                //  view.setBackgroundColor(Color.parseColor("#FFCCCB4C"));
                convertView.setBackgroundResource(R.drawable.list_view);




            }
            mainViewholder = (ViewHolder) convertView.getTag();
            final ViewHolder finalMainViewholder = mainViewholder;
            mainViewholder.button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                   // Toast.makeText(getContext(), "Challenge Sent to " + position, Toast.LENGTH_SHORT).show();
                    new AlertDialog.Builder(PushUpResult.this)
                            .setTitle("Confirm Challenge!")
                            .setMessage("Are you sure you to want to send for challenge?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                     //startPush_count();

                                    finalMainViewholder.button.setEnabled(false);
                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();
                }
            });
            mainViewholder.title.setText(getItem(position));

            return convertView;
        }
    }
    public class ViewHolder {


        TextView title;
        Button button;
    }


    @Override
    protected void onPause() {
        super.onPause();

    }

    public void goHome() {

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(PushUpResult.this, PushUpCounter.class);
                    //intent.putExtra("EXTRA_RESULT_VALUE", txt_pushup_count.getText());
                PushUpResult.this.startActivity(intent);

            }

        });
    }





}
