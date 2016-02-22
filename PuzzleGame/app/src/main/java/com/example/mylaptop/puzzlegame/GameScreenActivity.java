/*  created by Tran Tuan Anh - HUST - 2016
 *  copyright (c) 2016
 */

package com.example.mylaptop.puzzlegame;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.view.menu.ActionMenuItemView;
import android.support.v7.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameScreenActivity  extends AppCompatActivity{
    private int flag = 0;               // --- flag = 0 -> Don't Use Guess Mode --- flag = 1 -> Use Guess Mode ---
    private int start = 0;              // --- start = 0 -> Game Doesn't Start --- start = 1 -> Game Started
    private int numberMove = 0;         // --- The Number Of The Move ---
    private int idOfResultPic = 0;      // --- Id Of Pic Will Be Dsiplayed When Game Over ---

    TimerCounter timerCounter;          // --- Count Time Of Play Session ---
    TextView timerView;                 // --- TextView Displays Time ---

    GridView gridView;                  // --- Layout Stores Image 's Frag ---
    MyAdapter myAdapter;

    List<List<Integer>> adjList = new ArrayList<List<Integer>>();       // --- Adjective Cell 's Index Of Each Cell In Table Pic Fragment ---
    List<String> result = new ArrayList<String>();                           // --- Result Cell 's Index Order ---

    ImageView imageViewResult;          // --- ImageView Will Be Displayed When Game Over ---

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_screen_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.gameScreenToolbar);
        setSupportActionBar(toolbar);
        makeAdjListAndResultList();

        gridView = (GridView)findViewById(R.id.gridview);
        myAdapter = new MyAdapter(this);
        gridView.setAdapter(myAdapter);

        timerCounter = new TimerCounter();
        timerView  = (TextView)findViewById(R.id.textTimer);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              if (start == 0) {
                    timerCounter.start();
                    start = 1;
              }
              if (!myAdapter.items.get(position).name.equals("Image 0")) {
                   List<Integer> subList = new ArrayList<Integer>();
                   subList = adjList.get(position);
                   for (Integer d : subList) {
                       if (myAdapter.items.get(d).name.equals("Image 0")) {
                           numberMove++;

                           Collections.swap(myAdapter.items, d, position);
                           ImageView imageView = (ImageView) view.findViewById(R.id.picture);
                           imageView.setImageResource(myAdapter.items.get(position).drawableId);
                           TextView name = (TextView) view.findViewById(R.id.text);
                           name.setText("");
                           name.setPadding(0, 0, 0, 0);

                           View subViewOfGrid = gridView.getChildAt(d);
                           imageView = (ImageView) subViewOfGrid.findViewById(R.id.picture);
                           imageView.setImageResource(myAdapter.items.get(d).drawableId);

                           if (flag == 1) {
                                name = (TextView) subViewOfGrid.findViewById(R.id.text);
                                name.setText(myAdapter.items.get(d).name);
                                name.setPadding(10, 10, 10, 10);
                           }

                           name = (TextView) findViewById(R.id.moveNum);
                           name.setText(String.valueOf(numberMove));

                           List<String> resultOfUser = new ArrayList<String>();
                           for (int i = 0; i < 9; i++) {
                               resultOfUser.add(myAdapter.items.get(i).name);
                           }
                           if (resultOfUser.equals(result)) {
                                timerCounter.pause();
                               setResultPicForImageViewResult();
                           }
                           break;
                       }
                   }
              }
            }
            });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_game_screen, menu);
        MenuItem modeItemIcon = menu.findItem(R.id.itemModeIcon);
        MenuItem modeItemText = menu.findItem(R.id.itemModeText);

        modeItemIcon.setIcon(R.drawable.play);
        modeItemText.setTitle("Play");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        TextView name;
        String str = new String();

        switch (item.getItemId()){
            case R.id.itemGuess :{
                 if(flag == 0) {
                     flag = 1;
                     item.setTitle("GUESS ✓");
                     for(int i = 0 ; i < 9 ; i++){
                         name = (TextView) gridView.getChildAt(i).getTag(R.id.text);
                         str = myAdapter.items.get(i).name;
                         if(!str.equals("Image 0")) {
                             name.setText(myAdapter.items.get(i).name);
                             name.setPadding(10, 10, 10, 10);
                         }
                     }
                 }
                 else{
                     flag = 0;
                     item.setTitle("GUESS ✗");
                     for(int i = 0 ; i < 9 ; i++){
                         name = (TextView) gridView.getChildAt(i).getTag(R.id.text);
                         name.setText("");
                         name.setPadding(0, 0, 0, 0);
                     }
                 }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void makeAdjListAndResultList(){
        String str = new String("Image ");
        for(int i = 0 ; i < 9 ; i++){
            str += String.valueOf(i);
            result.add(str);
            str = "Image ";

            List<Integer> subList = new ArrayList<>();
            if(i == 0){
                subList.add(1);
                subList.add(3);
            }
            else if(i == 1){
                subList.add(0);
                subList.add(2);
                subList.add(4);
            }
            else if(i == 2){
                subList.add(1);
                subList.add(5);
            }
            else if(i == 3){
                subList.add(0);
                subList.add(4);
                subList.add(6);
            }
            else if(i == 4){
                subList.add(1);
                subList.add(3);
                subList.add(5);
                subList.add(7);
            }
            else if(i == 5){
                subList.add(2);
                subList.add(4);
                subList.add(8);
            }
            else if(i == 6){
                subList.add(3);
                subList.add(7);
            }
            else if(i == 7){
                subList.add(4);
                subList.add(6);
                subList.add(8);
            }
            else{
                subList.add(5);
                subList.add(7);
            }
            adjList.add(subList);
        }
    }

    public void buttonClicked(View view){
         TextView name;
         ImageView imageView;

         imageView = (ImageView)findViewById(R.id.imageViewResult);
         imageView.setImageDrawable(null);

         timerCounter.pause();
         timerView.setText("00:00:00");

         numberMove = 0;
         name = (TextView)findViewById(R.id.moveNum);
         name.setText("0");

         flag = 0;
         start = 0;

         ActionMenuItemView item = (ActionMenuItemView)findViewById(R.id.itemGuess);
         item.setTitle("GUESS ✗");
         for(int i = 0 ; i < 9 ; i++){
             name = (TextView) gridView.getChildAt(i).getTag(R.id.text);
             name.setText("");
             name.setPadding(0, 0, 0, 0);
         }

         Collections.shuffle(myAdapter.items);

         for(int i = 0 ; i < 9 ; i++){
            imageView = (ImageView) gridView.getChildAt(i).findViewById(R.id.picture);
            imageView.setImageResource(myAdapter.items.get(i).drawableId);
         }
    }

    public void setResultPicForImageViewResult(){
        imageViewResult = (ImageView) findViewById(R.id.imageViewResult);
        imageViewResult.setImageResource(idOfResultPic);
        imageViewResult.setPadding(1, 1, 1, 1);
    }

    public class MyAdapter extends BaseAdapter {
        public List<Item> items = new ArrayList<Item>();
        private LayoutInflater inflater;

        public MyAdapter(Context context) {
            inflater = LayoutInflater.from(context);
            storeImageToItemList();
        }

        public void storeImageToItemList(){
            ImageView imageView = (ImageView) findViewById(R.id.guesspic);
            if(OptionActivity.pic == 1){
                imageView.setImageResource(R.drawable.guesspic1);
                idOfResultPic = R.drawable.guesspic1;
                items.add(new Item("Image 0", R.drawable.bluepic));
                items.add(new Item("Image 1", R.drawable.pic1_2_3));
                items.add(new Item("Image 2", R.drawable.pic1_3_3));
                items.add(new Item("Image 3", R.drawable.pic1_4_3));
                items.add(new Item("Image 4", R.drawable.pic1_5_3));
                items.add(new Item("Image 5", R.drawable.pic1_6_3));
                items.add(new Item("Image 6", R.drawable.pic1_7_3));
                items.add(new Item("Image 7", R.drawable.pic1_8_3));
                items.add(new Item("Image 8", R.drawable.pic1_9_3));
            }
            else if(OptionActivity.pic == 2){
                imageView.setImageResource(R.drawable.guesspic2);
                idOfResultPic = R.drawable.guesspic2;
                items.add(new Item("Image 0", R.drawable.bluepic));
                items.add(new Item("Image 1", R.drawable.pic2_2_3));
                items.add(new Item("Image 2", R.drawable.pic2_3_3));
                items.add(new Item("Image 3", R.drawable.pic2_4_3));
                items.add(new Item("Image 4", R.drawable.pic2_5_3));
                items.add(new Item("Image 5", R.drawable.pic2_6_3));
                items.add(new Item("Image 6", R.drawable.pic2_7_3));
                items.add(new Item("Image 7", R.drawable.pic2_8_3));
                items.add(new Item("Image 8", R.drawable.pic2_9_3));
            }
            else if(OptionActivity.pic == 3){
                imageView.setImageResource(R.drawable.guesspic3);
                idOfResultPic = R.drawable.guesspic3;
                items.add(new Item("Image 0", R.drawable.bluepic));
                items.add(new Item("Image 1", R.drawable.pic3_2_3));
                items.add(new Item("Image 2", R.drawable.pic3_3_3));
                items.add(new Item("Image 3", R.drawable.pic3_4_3));
                items.add(new Item("Image 4", R.drawable.pic3_5_3));
                items.add(new Item("Image 5", R.drawable.pic3_6_3));
                items.add(new Item("Image 6", R.drawable.pic3_7_3));
                items.add(new Item("Image 7", R.drawable.pic3_8_3));
                items.add(new Item("Image 8", R.drawable.pic3_9_3));
            }
            else if(OptionActivity.pic == 4){
                imageView.setImageResource(R.drawable.guesspic4);
                idOfResultPic = R.drawable.guesspic4;
                items.add(new Item("Image 0", R.drawable.bluepic));
                items.add(new Item("Image 1", R.drawable.pic4_2_3));
                items.add(new Item("Image 2", R.drawable.pic4_3_3));
                items.add(new Item("Image 3", R.drawable.pic4_4_3));
                items.add(new Item("Image 4", R.drawable.pic4_5_3));
                items.add(new Item("Image 5", R.drawable.pic4_6_3));
                items.add(new Item("Image 6", R.drawable.pic4_7_3));
                items.add(new Item("Image 7", R.drawable.pic4_8_3));
                items.add(new Item("Image 8", R.drawable.pic4_9_3));
            }
            else if(OptionActivity.pic == 5){
                imageView.setImageResource(R.drawable.guesspic5);
                idOfResultPic = R.drawable.guesspic5;
                items.add(new Item("Image 0", R.drawable.bluepic));
                items.add(new Item("Image 1", R.drawable.pic5_2_3));
                items.add(new Item("Image 2", R.drawable.pic5_3_3));
                items.add(new Item("Image 3", R.drawable.pic5_4_3));
                items.add(new Item("Image 4", R.drawable.pic5_5_3));
                items.add(new Item("Image 5", R.drawable.pic5_6_3));
                items.add(new Item("Image 6", R.drawable.pic5_7_3));
                items.add(new Item("Image 7", R.drawable.pic5_8_3));
                items.add(new Item("Image 8", R.drawable.pic5_9_3));
            }
            else{
                imageView.setImageResource(R.drawable.guesspic6);
                idOfResultPic = R.drawable.guesspic6;
                items.add(new Item("Image 0", R.drawable.bluepic));
                items.add(new Item("Image 1", R.drawable.pic6_2_3));
                items.add(new Item("Image 2", R.drawable.pic6_3_3));
                items.add(new Item("Image 3", R.drawable.pic6_4_3));
                items.add(new Item("Image 4", R.drawable.pic6_5_3));
                items.add(new Item("Image 5", R.drawable.pic6_6_3));
                items.add(new Item("Image 6", R.drawable.pic6_7_3));
                items.add(new Item("Image 7", R.drawable.pic6_8_3));
                items.add(new Item("Image 8", R.drawable.pic6_9_3));
            }
            Collections.shuffle(items);
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int i)
        {
            return items.get(i);
        }

        @Override
        public long getItemId(int i)
        {
            return items.get(i).drawableId;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View v = view;
            ImageView picture;

            if(v == null) {
                v = inflater.inflate(R.layout.gridview_item, viewGroup, false);
                v.setTag(R.id.picture, v.findViewById(R.id.picture));
                v.setTag(R.id.text, v.findViewById(R.id.text));
            }

            picture = (ImageView)v.getTag(R.id.picture);

            Item item = (Item)getItem(i);
            picture.setImageResource(item.drawableId);

            return v;
        }

        private class Item {
            final String name;
            final int drawableId;

            Item(String name, int drawableId){
                this.name = name;
                this.drawableId = drawableId;
            }
        }
    }

    private class TimerCounter {

        private long startTime;
        private Handler myHandler = new Handler();

        long timeInMillies;
        long timeSwap;
        long finalTime;

        int minutes = 0;

        public void start() {

            timeInMillies = 0L;
            timeSwap = 0L;
            finalTime = 0L;
            startTime = 0L;

            startTime = SystemClock.uptimeMillis();
            myHandler.postDelayed(updateTimerMethod, 0);
        }

        public void pause() {
            timeSwap += timeInMillies;
            myHandler.removeCallbacks(updateTimerMethod);
        }

        private Runnable updateTimerMethod = new Runnable() {

            public void run() {
                timeInMillies = SystemClock.uptimeMillis() - startTime;
                finalTime = timeSwap + timeInMillies;

                int seconds = (int) (finalTime / 1000);
                minutes = seconds / 60;
                int hours = minutes / 60;
                seconds = seconds % 60;
                timerView.setText("" + String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":"
                        + String.format("%02d", seconds));
                myHandler.postDelayed(this, 0);
            }

        };
    }
}
