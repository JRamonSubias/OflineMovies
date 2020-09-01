package com.esime.oflinemovies.Data.Remoto;

import com.esime.oflinemovies.Data.Local.Entity.MovieEntity;
import com.esime.oflinemovies.Data.Local.Entity.TvEntity;
import com.esime.oflinemovies.Data.Remoto.Request.RequestRate;
import com.esime.oflinemovies.Data.Remoto.Request.RequestSession;
import com.esime.oflinemovies.Data.Remoto.Request.RequestToken;
import com.esime.oflinemovies.Data.Remoto.model.GetTokenResponse;
import com.esime.oflinemovies.Data.Remoto.model.MovieCompleteResponse;
import com.esime.oflinemovies.Data.Remoto.model.MoviesResponse;
import com.esime.oflinemovies.Data.Remoto.model.RateResponse;
import com.esime.oflinemovies.Data.Remoto.model.SearchResponse;
import com.esime.oflinemovies.Data.Remoto.model.SessionResponse;
import com.esime.oflinemovies.Data.Remoto.model.TvInformationResponse;
import com.esime.oflinemovies.Data.Remoto.model.TvResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApiService {

    @GET("movie/popular")
    Call<MoviesResponse> loadPopularMovies(@Query("page") int page);

    @GET("tv/popular")
    Call<TvResponse> loadPopularTv();

    @GET("search/multi")
    Call<SearchResponse> search(@Query("query") String query );

    @GET("movie/{movie_id}")
    Call<MovieCompleteResponse> getMovieInformation(@Path("movie_id") int movie_id, @Query("language") String language);

    @GET("tv/{tv_id}")
    Call<TvInformationResponse> getTvInformation(@Path("tv_id") int tv_id, @Query("language") String language);

    @GET("authentication/token/new")
    Call<GetTokenResponse> getToken();

    @POST("authentication/token/validate_with_login")
    Call<GetTokenResponse> getRequestToken(@Body RequestToken requestToken);

    @POST("authentication/session/new")
    Call<SessionResponse> getSessionId(@Body RequestSession requestSession);

    @POST("tv/{tv_id}/rating")
    Call<RateResponse> postRatingTv(@Path("tv_id") int tv_id, @Query("session_id") String session_id, @Body RequestRate requestRate);

    @POST("movie/{movie_id}/rating")
    Call<RateResponse> postRatingMoview(@Path("movie_id") int movie_id, @Query("session_id") String session_id, @Body RequestRate requestRate);


}
