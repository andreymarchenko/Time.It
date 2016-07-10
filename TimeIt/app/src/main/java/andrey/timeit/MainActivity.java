package andrey.timeit;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import andrey.timeit.dialog.AddingTaskDialogFragment;
import andrey.timeit.fragments.AdviceFragment;
import andrey.timeit.fragments.CurrentTasksFragment;
import andrey.timeit.fragments.DoneTasksFragment;
import andrey.timeit.fragments.SplashFragment;
import andrey.timeit.fragments.StatisticTasksFragment;
import andrey.timeit.fragments.UserProfileFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AddingTaskDialogFragment.AddingTaskListener {

    FragmentManager fragmentManager;

    RecyclerView rv;
    private List<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentManager = getFragmentManager();

        runSplash();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment addingTaskDialogFragment = new AddingTaskDialogFragment();
                addingTaskDialogFragment.show(fragmentManager, "addingTaskDialogFragment");
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        rv = (RecyclerView) findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        //rv.setLayoutManager(llm);
       // rv.setHasFixedSize(true);

        initializeData();

        RVAdapter adapter = new RVAdapter(tasks);
        //rv.setAdapter(adapter);
    }

    public void runSplash() {
        SplashFragment splashFragment = new SplashFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.app_bar_layout_main, splashFragment)
                .addToBackStack(null).commit();
    }

    private void initializeData() {
        tasks = new ArrayList<>();
        tasks.add(new Task("Работа", R.drawable.work, R.drawable.play, R.drawable.stop, R.drawable.pause));
        tasks.add(new Task("Поездка домой", R.drawable.trip, R.drawable.play, R.drawable.stop, R.drawable.pause));
        tasks.add(new Task("Отдых", R.drawable.rest, R.drawable.play, R.drawable.stop, R.drawable.pause));
        tasks.add(new Task("Сходить на свидание", R.drawable.love, R.drawable.play, R.drawable.stop, R.drawable.pause));
        tasks.add(new Task("Посетить театр", R.drawable.theatre, R.drawable.play, R.drawable.stop, R.drawable.pause));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.current_tasks) {
            fragmentManager.popBackStack();
            CurrentTasksFragment currentTasksFragment = new CurrentTasksFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, currentTasksFragment)
                    .commit();
        } else if (id == R.id.done_tasks) {
            fragmentManager.popBackStack();
            DoneTasksFragment doneTasksFragment = new DoneTasksFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, doneTasksFragment)
                    .commit();

        } else if (id == R.id.tasks_statistic) {
            fragmentManager.popBackStack();
            StatisticTasksFragment statisticTasksFragment = new StatisticTasksFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, statisticTasksFragment)
                    .commit();
        }

        else if (id == R.id.my_profile) {
            fragmentManager.popBackStack();
            UserProfileFragment userProfileFragment = new UserProfileFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, userProfileFragment)
                    .commit();
        }

        else if (id == R.id.advice) {
            fragmentManager.popBackStack();
            AdviceFragment adviceFragment = new AdviceFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, adviceFragment)
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onTaskAdded() {
        //Toast.makeText(this, "Task Added", Toast.LENGTH_LONG).show();
        Snackbar.make(findViewById(R.id.fab), "Задача добавлена", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void onTaskAddingCancel() {
        //Toast.makeText(this, "Task Adding cancel", Toast.LENGTH_LONG).show();
        Snackbar.make(findViewById(R.id.fab), "Добавление отменено", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
