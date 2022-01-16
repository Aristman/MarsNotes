package ru.marslab.marsnotes.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentContainerView
import com.google.android.material.navigation.NavigationView
import ru.marslab.marsnotes.R
import ru.marslab.marsnotes.databinding.ActivityMainBinding
import ru.marslab.marsnotes.domain.Publisher
import ru.marslab.marsnotes.domain.PublisherHolder

class MainActivity : AppCompatActivity(), PublisherHolder, FragmentRouterHolder {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override val publisher = Publisher()
    private var toolbar: Toolbar? = null
    override val router: FragmentRouter by lazy { FragmentRouter(supportFragmentManager) }
    private val isSingInGoogle = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_drawer)
        if (savedInstanceState == null) {
            if (isSingInGoogle) {
                router.showNotesList()
            } else {
                router.showGoogleAuth()
            }
        }
        initFragmentContainers()
        initToolbar()
        initDrawer()
    }

    private fun initFragmentContainers() {
        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount == 0 &&
                resources.getBoolean(R.bool.isLandscape)
            ) {
                val noteFragmentContainer =
                    findViewById<FragmentContainerView>(R.id.note_fragment_container)
                noteFragmentContainer.visibility = View.VISIBLE
            }
        }
    }

    private fun initToolbar() {
        toolbar = findViewById(R.id.main_toolbar)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.notes_list_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.new_note) {
            router.showNewNote()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initDrawer() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_main_layout)
        val toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.app_name,
            R.string.app_name
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        val navigationView = findViewById<NavigationView>(R.id.main_navigation_view)
        navigationView.setNavigationItemSelectedListener { item: MenuItem ->
            drawer.closeDrawer(GravityCompat.START)
            val noteFragmentContainer =
                findViewById<FragmentContainerView>(R.id.note_fragment_container)
            when (item.itemId) {
                R.id.about_program -> {
                    router.showAbout()
                    if (resources.getBoolean(R.bool.isLandscape)) {
                        noteFragmentContainer.visibility = View.GONE
                    }
                    return@setNavigationItemSelectedListener true
                }
                R.id.settings -> {
                    router.showSettings()
                    if (resources.getBoolean(R.bool.isLandscape)) {
                        noteFragmentContainer.visibility = View.GONE
                    }
                    return@setNavigationItemSelectedListener true
                }
                R.id.main_screen -> {
                    router.showNotesList()
                }
            }
            false
        }
    }
}
