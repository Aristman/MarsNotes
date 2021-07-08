package ru.marslab.marsnotes.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentContainerView;

import com.google.android.material.navigation.NavigationView;

import ru.marslab.marsnotes.R;
import ru.marslab.marsnotes.domain.Publisher;
import ru.marslab.marsnotes.domain.PublisherHolder;


public class MainActivity extends AppCompatActivity implements PublisherHolder, FragmentRouterHolder {


    private final Publisher publisher = new Publisher();
    private Toolbar toolbar;
    private FragmentRouter fragmentRouter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        fragmentRouter = new FragmentRouter(getSupportFragmentManager());
        if (savedInstanceState == null) {
            fragmentRouter.showNotesList();
        }
        initFragmentContainers();
        initToolbar();
        initDrawer();
    }

    private void initFragmentContainers() {
        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0 &&
                    getResources().getBoolean(R.bool.isLandscape)) {
                FragmentContainerView noteFragmentContainer = findViewById(R.id.note_fragment_container);
                noteFragmentContainer.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notes_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.new_note) {
            fragmentRouter.showNewNote();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initDrawer() {
        DrawerLayout drawer = findViewById(R.id.drawer_main_layout);

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
            FragmentContainerView noteFragmentContainer = findViewById(R.id.note_fragment_container);
            if (item.getItemId() == R.id.about_program) {
                fragmentRouter.showAbout();
                if (getResources().getBoolean(R.bool.isLandscape)) {
                    noteFragmentContainer.setVisibility(View.GONE);
                }
                return true;
            } else if (item.getItemId() == R.id.settings) {
                fragmentRouter.showSettings();
                if (getResources().getBoolean(R.bool.isLandscape)) {
                    noteFragmentContainer.setVisibility(View.GONE);
                }
                return true;
            } else if (item.getItemId() == R.id.main_screen) {
                fragmentRouter.showNotesList();
            }
            return false;
        });
    }


    @Override
    public Publisher getPublisher() {
        return publisher;
    }

    @Override
    public FragmentRouter getRouter() {
        return fragmentRouter;
    }
}
