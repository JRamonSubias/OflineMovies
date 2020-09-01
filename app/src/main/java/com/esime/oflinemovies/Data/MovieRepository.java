package com.esime.oflinemovies.Data;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.esime.oflinemovies.Data.Local.Dao.MovieDao;
import com.esime.oflinemovies.Data.Local.Entity.MovieEntity;
import com.esime.oflinemovies.Data.Local.Entity.SearchEntity;
import com.esime.oflinemovies.Data.Local.Entity.TvEntity;
import com.esime.oflinemovies.Data.Local.MovieRoomDatabase;
import com.esime.oflinemovies.Data.Remoto.ApiConstants;
import com.esime.oflinemovies.Data.Remoto.MovieApiService;
import com.esime.oflinemovies.Data.Remoto.Request.RequestRate;
import com.esime.oflinemovies.Data.Remoto.RequestInterceptor;
import com.esime.oflinemovies.Data.Remoto.model.MovieCompleteResponse;
import com.esime.oflinemovies.Data.Remoto.model.MoviesResponse;
import com.esime.oflinemovies.Data.Remoto.model.RateResponse;
import com.esime.oflinemovies.Data.Remoto.model.SearchResponse;
import com.esime.oflinemovies.Data.Remoto.model.TvInformationResponse;
import com.esime.oflinemovies.Data.Remoto.model.TvResponse;
import com.esime.oflinemovies.app.MyApp;
import com.esime.oflinemovies.network.NetworkBoundResource;
import com.esime.oflinemovies.network.Resource;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieRepository {
    private final MovieApiService movieApiService;
    private final MovieDao movieDao;
    private String query="";
     MutableLiveData<List<SearchEntity>> searchList;
    MutableLiveData<List<MovieEntity>> movieList;
    MutableLiveData<MovieCompleteResponse> movieInformation;
    MutableLiveData<TvInformationResponse> tvInformation;
    private int page = 1;
    private int totalPage;
    private int movie_id = 0;
    private int tv_id=0;
    private String language ="es-MX";

    public MovieRepository(){
        //Locla > Room
        MovieRoomDatabase movieRoomDatabase = Room.databaseBuilder(
                MyApp.getContext(),
                MovieRoomDatabase.class,
                "db_movieTv"
        ).build();
        movieDao = movieRoomDatabase.getDao();


        //RequestInterceptor:  incluir en la cabecera (URL) de la peticion
        //el TOKEN o API_Key que autoriza al usuario
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.addInterceptor(new RequestInterceptor());
        OkHttpClient client = okHttpClientBuilder.build();

        //Remote Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        movieApiService = retrofit.create(MovieApiService.class);
        movieList = getMovieWithOutBD();

        if(!query.equals("")){
            searchList = getSearchWithOutBD();
        }

        if(movie_id != 0){
            movieInformation = getMovieCompleteInformation();
        }
        if(tv_id != 0){
            tvInformation = getTvCompleteInformation();
        }
    }

    public LiveData<Resource<List<TvEntity>>> getPopularTv(){
        return new NetworkBoundResource<List<TvEntity>, TvResponse>() {
            @Override
            protected void saveCallResult(@NonNull TvResponse item) {
                movieDao.saveTv(item.getResults());
            }

            @NonNull
            @Override
            protected LiveData<List<TvEntity>> loadFromDb() {
                return movieDao.loadTV();
            }

            @NonNull
            @Override
            protected Call<TvResponse> createCall() {
                return movieApiService.loadPopularTv();
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<List<MovieEntity>>> getPopularMovies(){
        //Tipo que devulve Roomm (BD local), Tipo que devulve la API con retrofit
        return new NetworkBoundResource<List<MovieEntity>, MoviesResponse>() {
            @Override
            protected void saveCallResult(@NonNull MoviesResponse item) {
                movieDao.saveMovies(item.getResults());
            }

            @NonNull
            @Override
            protected LiveData<List<MovieEntity>> loadFromDb() {
                //los datos que dispongamos a la base de datos local
                return movieDao.loadMovie();
            }

            @NonNull
            @Override
            protected Call<MoviesResponse> createCall() {
               //realiza la llamada a la api, en caso que tengamos internet
                return  movieApiService.loadPopularMovies(page);
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<List<SearchEntity>>> getSearch(){
        return new NetworkBoundResource<List<SearchEntity>, SearchResponse>() {
            @Override
            protected void saveCallResult(@NonNull SearchResponse item) {
                movieDao.saveSearch(item.getResults());
            }

            @NonNull
            @Override
            protected LiveData<List<SearchEntity>> loadFromDb() {
                 return  movieDao.loadSearch();
            }

            @NonNull
            @Override
            protected Call<SearchResponse> createCall() {
                return movieApiService.search(query);
            }
        }.getAsLiveData();

    }

    public MutableLiveData<List<SearchEntity>> getSearchWithOutBD(){
        if (searchList == null){
            searchList = new MutableLiveData<>();
        }
        Call<SearchResponse> searchResponseCall = movieApiService.search(query);
        searchResponseCall.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if(response.isSuccessful()){
                    SearchResponse searchResponse = response.body();
                    searchList.setValue(searchResponse.getResults());
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error Conexion:SEARCH", Toast.LENGTH_SHORT).show();
            }
        });
        return searchList;
    }

    public MutableLiveData<List<MovieEntity>> getMovieWithOutBD(){
        if(movieList ==null){
            movieList = new MutableLiveData<>();
        }
        Call<MoviesResponse> moviesResponseCall = movieApiService.loadPopularMovies(page);
        moviesResponseCall.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                if(response.isSuccessful()){
                    MoviesResponse moviesResponse = response.body();
                    totalPage = moviesResponse.getTotal_pages();
                    movieList.setValue( moviesResponse.getResults());
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "ERROR CONEXION", Toast.LENGTH_SHORT).show();
            }
        });
        return  movieList;
    }

    public MutableLiveData<MovieCompleteResponse> getMovieCompleteInformation() {
            if(movieInformation == null){
                movieInformation = new MutableLiveData<>();
            }
        Call<MovieCompleteResponse> completeResponseCall = movieApiService.getMovieInformation(movie_id,language);
        completeResponseCall.enqueue(new Callback<MovieCompleteResponse>() {
            @Override
            public void onResponse(Call<MovieCompleteResponse> call, Response<MovieCompleteResponse> response) {
                if(response.isSuccessful()){
                    MovieCompleteResponse movieCompleteResponse = response.body();
                    movieInformation.setValue(movieCompleteResponse);
                }else{
                    Toast.makeText(MyApp.getContext(), "Algo salio mal:INFORMATION MOVIE"+response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<MovieCompleteResponse> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "ERROR Conexion:MOVIE INFORMATION", Toast.LENGTH_SHORT).show();
            }
        });
        return movieInformation;
    }

    public MutableLiveData<TvInformationResponse> getTvCompleteInformation() {
        if(tvInformation == null){
            tvInformation = new MutableLiveData<>();
        }
        Call<TvInformationResponse> completeResponseCall = movieApiService.getTvInformation(movie_id,language);
        completeResponseCall.enqueue(new Callback<TvInformationResponse>() {
            @Override
            public void onResponse(Call<TvInformationResponse> call, Response<TvInformationResponse> response) {
                if(response.isSuccessful()){
                    TvInformationResponse tvInformationResponse = response.body();
                    tvInformation.setValue(tvInformationResponse);
                }else{
                    Toast.makeText(MyApp.getContext(), "Algo salio mal:INFORMATION TV "+response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<TvInformationResponse> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "ERROR Conexion:MOVIE INFORMATION", Toast.LENGTH_SHORT).show();
            }
        });
        return tvInformation;
    }

    public void PostRatingTv(int id, String session_id, Double rating){
        RequestRate requestRate = new RequestRate(rating);
        Call<RateResponse> rateResponseCall = movieApiService.postRatingTv(id,session_id,requestRate);
        rateResponseCall.enqueue(new Callback<RateResponse>() {
            @Override
            public void onResponse(Call<RateResponse> call, Response<RateResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MyApp.getContext(), "Tu puntuación a sido guardada.", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(MyApp.getContext(), "Algo salio mal, intentelo mas tarde", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RateResponse> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Sin Internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void PostRatingMovie(int id, String session_id, Double rating){
        RequestRate requestRate = new RequestRate(rating);
        Call<RateResponse> rateResponseCallMovie= movieApiService.postRatingMoview(id,session_id,requestRate);
        rateResponseCallMovie.enqueue(new Callback<RateResponse>() {
            @Override
            public void onResponse(Call<RateResponse> call, Response<RateResponse> response) {
                if(response.isSuccessful()){
                    Toast.makeText(MyApp.getContext(), "Tu puntuación a sido guardada.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MyApp.getContext(), "Algo salio mal, Intentelo mas Tarde", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RateResponse> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Sin Internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getTotalPage(){
        return totalPage;
    }

    public void setPage(int page){
        this.page = page;
    }

    public void setId(int movie_id){
        this.movie_id = movie_id;}


    }


