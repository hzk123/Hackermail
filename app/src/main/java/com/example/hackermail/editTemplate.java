package com.example.hackermail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class editTemplate extends AppCompatActivity {


    private TemplateViewModel mTemplateViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template);



        // Setup the RecyclerView
        RecyclerView recyclerViewMain = findViewById(R.id.recyclerView_template);
        final TemplateListAdapter adapter = new TemplateListAdapter(this);
        recyclerViewMain.setAdapter(adapter);
        recyclerViewMain.setLayoutManager(new LinearLayoutManager(this));

        // Setup the WordViewModel
        mTemplateViewModel = ViewModelProviders.of(this).get(TemplateViewModel.class);
        // Get all the Templates from the database
        // and associate them to the adapter
        mTemplateViewModel.getmAllTemplates().observe(this, new Observer<List<Template>>() {
            @Override
            public void onChanged(@Nullable final List<Template> Templates) {
                // Update the cached copy of the Templates in the adapter.
                adapter.setTemplates(Templates);
            }
        });


        // Floating action button setup
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(editTemplate.this, newTemplate.class);
                startActivityForResult(intent, NEW_Template_ACTIVITY_REQUEST_CODE);
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
                        Template myTemplate = adapter.getTemplateAtPosition(position);
                        Toast.makeText(MainActivity.this,
                                getString(R.string.delete_word_preamble) + " " +
                                        myTemplate.getId(), Toast.LENGTH_LONG).show();

                        // Delete the word
                        mTemplateViewModel.deleteWord(myTemplate);
                    }
                });
        // Attach the item touch helper to the recycler view
        helper.attachToRecyclerView(recyclerViewMain);

        adapter.setOnItemClickListener(new TemplateListAdapter.ClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Template Template = adapter.getTemplateAtPosition(position);
                launchUpdateTemplateActivity(Template);
            }
        });
    }

}
