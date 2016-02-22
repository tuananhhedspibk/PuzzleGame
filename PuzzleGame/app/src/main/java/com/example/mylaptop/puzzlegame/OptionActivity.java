/*  created by Tran Tuan Anh - HUST - 2016
 *  copyright (c) 2016
 */

package com.example.mylaptop.puzzlegame;

import android.content.Intent;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class OptionActivity extends AppCompatActivity {

    static int pic = 1;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.optionToolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

    public void imageClicked(View view) {
        Intent intent = new Intent(OptionActivity.this, GameScreenActivity.class);
        switch (view.getId()) {
            case R.id.pic1: {
                pic = 1;
                break;
            }
            case R.id.pic2: {
                pic = 2;
                break;
            }
            case R.id.pic3: {
                pic = 3;
                break;
            }
            case R.id.pic4: {
                pic = 4;
                break;
            }
            case R.id.pic5: {
                pic = 5;
                break;
            }
            case R.id.pic6: {
                pic = 6;
                break;
            }
        }
        startActivity(intent);
    }
}
