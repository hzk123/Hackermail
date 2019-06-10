package com.example.hackermail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import static android.provider.AlarmClock.EXTRA_MESSAGE;
import static com.example.hackermail.newClock.RESULT_INSERT;
import static com.example.hackermail.newClock.RESULT_UPDATE;

public class MainActivity extends AppCompatActivity {

    //log in and out
    public static String emailId = "";
    private static String hint;
    public static final String LOGIN_MESSAGE = "login";
    public static final String LOGOUT_MESSAGE = "logout";
        public static final int LOGIN_REQUEST = 1;
    public static final String EXTRA_REPLY =
            "LOGIN.SET";

    public static final int NEW_CLOCK_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_CLOCK_ACTIVITY_REQUEST_CODE = 2;
    public static final String EXTRA_DATA_UPDATE_CLOCK = "extra_clock_to_be_updated";

    private TextView myEmail;
    private ClockViewModel mClockViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //set my email ID
        myEmail = (TextView)findViewById(R.id.txtID);
        if(emailId == ""){
            hint = getString(R.string.txtHint);
            myEmail.setText(hint);
        }
        else{ myEmail.setText(emailId); }


        // Setup the RecyclerView
        RecyclerView recyclerViewMain = findViewById(R.id.recyclerviewMain);
        final ClockListAdapter adapter = new ClockListAdapter(this);
        recyclerViewMain.setAdapter(adapter);
        recyclerViewMain.setLayoutManager(new LinearLayoutManager(this));

        // Setup the WordViewModel
        mClockViewModel = ViewModelProviders.of(this).get(ClockViewModel.class);
        // Get all the clocks from the database
        // and associate them to the adapter
        mClockViewModel.getmAllClocks().observe(this, new Observer<List<Clock>>() {
            @Override
            public void onChanged(@Nullable final List<Clock> clocks) {
                // Update the cached copy of the clocks in the adapter.
                adapter.setClocks(clocks);
            }
        });


        // Floating action button setup
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, newClock.class);
                startActivityForResult(intent, NEW_CLOCK_ACTIVITY_REQUEST_CODE);
            }
        });



        // Add the functionality to swipe items in the
        // recycler view to delete that item
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    // We are not implementing onMove() in this app
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    // When the use swipes a word,
                    // delete that word from the database.
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Clock myClock = adapter.getClockAtPosition(position);
                        Toast.makeText(MainActivity.this,
                                getString(R.string.delete_word_preamble) + " " +
                                        myClock.getId(), Toast.LENGTH_LONG).show();

                        // Delete the word
                        mClockViewModel.deleteWord(myClock);
                    }
                });
        // Attach the item touch helper to the recycler view
        helper.attachToRecyclerView(recyclerViewMain);

        adapter.setOnItemClickListener(new ClockListAdapter.ClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Clock clock = adapter.getClockAtPosition(position);
                launchUpdateClockActivity(clock);
            }
        });

    }


    //LogIn and LogOut
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(emailId == ""){
            getMenuInflater().inflate(R.menu.menu_log_in, menu);}
        else{
            getMenuInflater().inflate(R.menu.menu_log_out, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            //noinspection SimplifiableIfStatement
            case R.id.action_login:
                //auto jump to logIn activity
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Intent jumpIntent = new Intent(MainActivity.this,
                                logIn.class);
                        MainActivity.this.startActivity(jumpIntent);
                        MainActivity.this.finish();
                        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                    }
                }, 1000);
                break;

            case R.id.action_logout:

                //auto jump to logOut activity
                new Handler().postDelayed(new Runnable() {
                    public void run() {

                        Intent jumpIntent = new Intent(MainActivity.this,
                                logOut.class);
                        String email = myEmail.getText().toString();
                        jumpIntent.putExtra(LOGOUT_MESSAGE, email);
                        MainActivity.this.startActivity(jumpIntent);
                        MainActivity.this.finish();
                        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                    }
                }, 1000);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * When the user enters a new word in the NewWordActivity,
     * that activity returns the result to this activity.
     * If the user entered a new word, save it in the database.
     *
     * @param requestCode -- ID for the request
     * @param resultCode  -- indicates success or failure
     * @param data        -- The Intent sent back from the NewWordActivity,
     *                    which includes the word that the user entered
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_INSERT) {
            Clock clock = (Clock) data.getSerializableExtra(newClock.EXTRA_REPLY);
            // Save the data
            mClockViewModel.insert(clock);
        } else if (resultCode == RESULT_UPDATE) {
            // TODO: implement "UPDATE"
            Clock clock = (Clock) data.getSerializableExtra(newClock.EXTRA_REPLY);
            mClockViewModel.updateWord(clock);
        }
        else if(requestCode == LOGIN_REQUEST){
            if (resultCode == RESULT_OK) {
                Bundle bundle = data.getExtras();
                String email = bundle.getString(EXTRA_REPLY);
                //String email = data.getStringExtra(logIn.EXTRA_REPLY);
                myEmail.setText(email);
            }
        }
        else {
            Toast.makeText(
                    this, R.string.empty_not_saved, Toast.LENGTH_LONG).show();
        }
    }

    public void launchUpdateClockActivity(Clock clock) {
        Intent intent = new Intent(this, newClock.class);
        intent.putExtra(EXTRA_DATA_UPDATE_CLOCK, clock);
        startActivityForResult(intent, UPDATE_CLOCK_ACTIVITY_REQUEST_CODE);
    }
}

