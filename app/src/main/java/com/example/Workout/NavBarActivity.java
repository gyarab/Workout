package com.example.Workout;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;


public class NavBarActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Context context;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DBHandler dbHandler = new DBHandler(this);
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.query(DBHandler.TB_CURR, null, null, null, null, null, null);
        context = getApplicationContext();
        if (cursor.isAfterLast()) {
            Intent intent = new Intent(this, StartActivity.class);
            this.startActivity(intent);

        } else {
            setContentView(R.layout.activity_nav);


            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            NavigationView navigationView = findViewById(R.id.nav_view);
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_maxes, R.id.nav_progress, R.id.nav_program)
                    .setDrawerLayout(drawer)
                    .build();
            NavController navController = androidx.navigation.Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);

        }
        cursor.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav, menu);
        MenuItem down = menu.findItem(R.id.action_settings);
        down.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(context, ChooseProgramActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = androidx.navigation.Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Toast.makeText(getApplicationContext(),"YOUCLICKED TI", Toast.LENGTH_LONG);
        MenuItem item2 = item;
        String a = item2.toString();
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        // item.getTitle().toString();
        Toast.makeText(getApplicationContext(),"YOU CLIKCED SETTINGS", Toast.LENGTH_LONG);
        String a = item.getTitle().toString();
        return true;
    }
}