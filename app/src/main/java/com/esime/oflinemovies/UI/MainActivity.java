package com.esime.oflinemovies.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.esime.oflinemovies.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        BottomNavigationView navigationView = findViewById(R.id.bottomNavigationView2);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.movieFragment, R.id.searchFragment, R.id.top_Fragment)
                .build();
        NavController navController = Navigation.findNavController(this,R.id.fragment);
        NavigationUI.setupActionBarWithNavController(this, navController,appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView,navController);



    }
}
