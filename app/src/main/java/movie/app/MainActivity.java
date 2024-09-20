package movie.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements FilmIDCallback, FilmAvailable {
    private static final String TAG = "MainActivity";
    private ArrayList<Integer> allID;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<Film> films;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NowPlayingFilmID nowPlayingFilmID = new NowPlayingFilmID(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        films = new ArrayList<>();

        mAdapter = new FilmAdapter(films, this);
        mRecyclerView.setAdapter(mAdapter);


        Button homeButton = (Button) findViewById(R.id.home);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent customerIntent = new Intent(v.getContext().getApplicationContext(),MainActivity.class);
                v.getContext().startActivity(customerIntent);
            }
        });


        Button infoButton = (Button) findViewById(R.id.ticketButton);
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent customerIntent = new Intent(v.getContext().getApplicationContext(),TicketActivity.class);
                v.getContext().startActivity(customerIntent);
            }
        });

        Button contactButton = (Button) findViewById(R.id.contact);
        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent customerIntent = new Intent(v.getContext().getApplicationContext(),ContactActivity.class);
                v.getContext().startActivity(customerIntent);
            }
        });


    }

    private void fetchFilm(String url) {

        FilmTask task = new FilmTask(this);
        String[] urls = new String[] {url};
        task.execute(urls);

    }

    @Override
    public void filmIDCallback(ArrayList<Integer> moviesID) {
        Log.d(TAG, "filmIDCallback: all movies ids" + moviesID);
        allID = moviesID;

        for(Integer id: allID){
            String url = "https://api.themoviedb.org/3/movie/" + id + "?api_key=9356cb1f42f053e63a72c6bf6ca12171&append_" +
                    "to_response=credits,images,keywords,lists,releases,reviews,videos";

            fetchFilm(url);
        }
    }

    @Override
    public void filmAvailable(Film film) {
        films.add(film);
        mAdapter.notifyDataSetChanged();
    }



}

