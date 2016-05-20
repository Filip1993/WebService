package com.filipkesteli.webservice;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by programer on 20.5.2016..
 */

//interface za komunikaciju:
public interface IMovies {
    //endpoint -> kamo mi to gledamo:
    public static final String ENDPOINT_URL = "http://www.omdbapi.com/";

    //RETROFITOV API cita anotacije:

    //kamo ja trebam ici -> u routu sam:
    @GET("/")
    //uzmi film: -> retrofitov Callback:
    //movieName cu ti poslati:
    //on ce nama pozvati callback -> on kad bude spreman, pozvat ce nam callback:
    //spakirat ce nam u movieName callback
    void getMovie(@Query("t") String movieName, Callback<Movie> callback);
}
