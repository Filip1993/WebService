package com.filipkesteli.webservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    private EditText etMovieName;
    private Button btnGetMovie;
    private Callback<Movie> callback;
    private IMovies iMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidgets();
        setupListeners();

        setupRestAdapter(); //postavljamo RETROFITOV REST ADAPTER
    }

    private void setupRestAdapter() {
        //REST ADAPTER zna da ce se spojiti na www.tako dalje...
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(IMovies.ENDPOINT_URL)
                .build();

        iMovies = restAdapter.create(IMovies.class);
        //moramo napraviti CALLBACK:
        callback = new Callback<Movie>() {
            //proparsiran JSON objekt Movie s interneta
            //sad mozemo napisat sto hocemo!
            @Override
            public void success(Movie movie, Response response) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(movie.getTitle() + "\n");
                stringBuilder.append(movie.getYear() + "\n");
                stringBuilder.append(movie.getDirector() + "\n");
                stringBuilder.append(movie.getActors());
                String text = stringBuilder.toString();
                Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void initWidgets() {
        etMovieName = (EditText) findViewById(R.id.etMovieName);
        btnGetMovie = (Button) findViewById(R.id.btnGetMovie);
    }

    private void setupListeners() {
        btnGetMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //moramo settirati REST adapter
                //moramo ga trznuti (triggerirati):
                //moramo se zakaciti na WEB SERVICE (Kljucni moment)
                if (formIsOK()) {
                    iMovies.getMovie(etMovieName.getText().toString(), callback);
                }
            }
        });
    }

    private boolean formIsOK() {
        if (etMovieName.getText().toString().trim().length() == 0) {
            Toast.makeText(MainActivity.this, R.string.enter_title, Toast.LENGTH_SHORT).show();
            etMovieName.requestFocus();
            return false;
        }
        return true;
    }
}
