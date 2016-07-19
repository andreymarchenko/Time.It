package andrey.timeit;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

import andrey.timeit.alarm.AlarmHelper;
import andrey.timeit.dataBase.DBHelper;
import andrey.timeit.dialog.AddingTaskDialogFragment;
import andrey.timeit.dialog.EditTaskDialogFragment;
import andrey.timeit.fragments.AdviceFragment;
import andrey.timeit.fragments.CurrentTasksFragment;
import andrey.timeit.fragments.DoneTasksFragment;
import andrey.timeit.fragments.SplashFragment;
import andrey.timeit.fragments.StatisticTasksFragment;
import andrey.timeit.fragments.TaskFragment;
import andrey.timeit.fragments.UserProfileFragment;
import andrey.timeit.model.ModelDuration;
import andrey.timeit.model.ModelTask;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        AddingTaskDialogFragment.AddingTaskListener,
        CurrentTasksFragment.OnTaskDoneListener,
        DoneTasksFragment.OnTaskRestoreListener,
        EditTaskDialogFragment.EditingTaskListener {

    FragmentManager fragmentManager;

    TaskFragment currentTasksFragment;
    TaskFragment doneTasksFragment;

    StatisticTasksFragment statisticTasksFragment;
    UserProfileFragment userProfileFragment;
    AdviceFragment adviceFragment;

    FloatingActionButton fab;
    Toolbar toolbar;

    int currentPosition = 0;

    SearchView searchView;

    public DBHelper dbHelper;

    RecyclerView rv;
    private List<Task> tasks;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AlarmHelper.getInstance().init(getApplicationContext());

        dbHelper = new DBHelper(getApplicationContext());

        fragmentManager = getFragmentManager();
        ModelDuration modelDuration = new ModelDuration();
        dbHelper.saveDuration(modelDuration);

        statisticTasksFragment = new StatisticTasksFragment();
        fragmentManager.beginTransaction().add(R.id.content_main, statisticTasksFragment).commit();
        adviceFragment = new AdviceFragment();
        fragmentManager.beginTransaction().add(R.id.content_main, adviceFragment).commit();
        doneTasksFragment = new DoneTasksFragment();
        fragmentManager.beginTransaction().add(R.id.content_main, doneTasksFragment).commit();
        currentTasksFragment = new CurrentTasksFragment();
        fragmentManager.beginTransaction().add(R.id.content_main, currentTasksFragment).commit();
        toolbar.setTitle("Текущие задачи");

        runSplash();

        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (currentPosition) {
                    case 0: {
                        DialogFragment addingTaskDialogFragment = new AddingTaskDialogFragment();
                        addingTaskDialogFragment.show(fragmentManager, "addingTaskDialogFragment");
                        break;
                    }
                    case 1: {

                    }
                }
            }
        });

        searchView = (SearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                currentTasksFragment.findTasks(newText);
                doneTasksFragment.findTasks(newText);
                return false;
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //rv = (RecyclerView) findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        //rv.setLayoutManager(llm);
        // rv.setHasFixedSize(true);

        initializeData();

        RVAdapter adapter = new RVAdapter(tasks);
        //rv.setAdapter(adapter);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        StartApp.activityResumed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        StartApp.activityPaused();
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
            toolbar.setTitle("Текущие задачи");
            currentPosition = 0;
            fab.setImageResource(R.drawable.plus);
            fab.show();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_main, currentTasksFragment)
                    .addToBackStack("added")
                    .commit();

        } else if (id == R.id.done_tasks) {
            toolbar.setTitle("Завершенные задачи");
            currentPosition = 0;
            fab.setImageResource(R.drawable.plus);
            fab.show();
            //fab.setImageResource(R.drawable.ic_mode_edit_white_24dp);

            fragmentManager.beginTransaction()
                    .replace(R.id.content_main, doneTasksFragment)
                    .addToBackStack("adasd")
                    .commit();

        } else if (id == R.id.tasks_statistic) {
            toolbar.setTitle("Статистика");
            statisticTasksFragment = new StatisticTasksFragment();
            fragmentManager.beginTransaction().add(R.id.content_main, statisticTasksFragment).commit();
            currentPosition = 1;
            fab.hide();

            fragmentManager.beginTransaction()
                    .replace(R.id.content_main, statisticTasksFragment)
                    .addToBackStack("adasd")
                    .commit();

        } else if (id == R.id.advice) {
            toolbar.setTitle("Советы");
            adviceFragment = new AdviceFragment();
            fragmentManager.beginTransaction().add(R.id.content_main, adviceFragment).commit();
            currentPosition = 1;
            fab.hide();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_main, adviceFragment)
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onTaskAdded(ModelTask newTask) {
        currentTasksFragment.addTask(newTask, true);
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

    @Override
    public void onTaskDone(ModelTask task) {
        doneTasksFragment.addTask(task, false);
    }

    @Override
    public void onTaskRestore(ModelTask task) {
        currentTasksFragment.addTask(task, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://andrey.timeit/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://andrey.timeit/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    @Override
    public void onTaskEdited(ModelTask updatedTask) {
        currentTasksFragment.updateTask(updatedTask);
        dbHelper.update().task(updatedTask);
    }
}
