package de.fhguide;

import android.os.Bundle;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import de.fhguide.database.FHGuideDatabase;
import de.fhguide.databinding.ActivityMainBinding;

public class MainActivity extends NetContentActivity
{
    private ActivityMainBinding binding;

    @Override
    public void onContentLoaded(Bundle savedInstanceState)
    {
        this.showContent();
    }

    @Override
    public NetContentLoader getContentLoaders()
    {
        return new NetContentLoader()
        {
            @Override
            public void loadContent()
            {
                FHGuideDatabase.getInstance(MainActivity.this).loadAllModules(this::onSuccess, this::onError);
            }
        };
    }

    private void showContent()
    {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }
}