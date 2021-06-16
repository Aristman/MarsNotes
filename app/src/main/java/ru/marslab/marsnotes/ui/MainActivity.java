package ru.marslab.marsnotes.ui;

import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import ru.marslab.marsnotes.R;
import ru.marslab.marsnotes.domain.Publisher;
import ru.marslab.marsnotes.domain.PublisherHolder;
import ru.marslab.marsnotes.ui.about.AboutFragment;
import ru.marslab.marsnotes.ui.settings.SettingsFragment;


public class MainActivity extends AppCompatActivity implements PublisherHolder {

    private final Publisher publisher = new Publisher();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        initDrawer();
    }

    private void initDrawer() {
        DrawerLayout drawer = findViewById(R.id.drawer_main_layout);

        Toolbar toolbar = findViewById(R.id.main_toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.app_name,
                R.string.app_name
        );
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.main_navigation_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            drawer.closeDrawer(GravityCompat.START);
            if (item.getItemId() == R.id.about_program) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_fragment_container, new AboutFragment())
                        .addToBackStack(null)
                        .commit();
            } else if (item.getItemId() == R.id.settings) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_fragment_container, new SettingsFragment())
                        .addToBackStack(null)
                        .commit();
            }
            return true;
        });
    }


    @Override
    public Publisher getPublisher() {
        return publisher;
    }

}
