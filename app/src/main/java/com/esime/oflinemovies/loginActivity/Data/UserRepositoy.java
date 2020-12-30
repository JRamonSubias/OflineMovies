package com.esime.oflinemovies.loginActivity.Data;

import android.os.AsyncTask;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.esime.oflinemovies.Data.Remoto.ApiConstants;
import com.esime.oflinemovies.Data.Remoto.MovieApiService;
import com.esime.oflinemovies.Data.Remoto.Request.RequestSession;
import com.esime.oflinemovies.Data.Remoto.Request.RequestToken;
import com.esime.oflinemovies.Data.Remoto.RequestInterceptor;
import com.esime.oflinemovies.Data.Remoto.model.GetTokenResponse;
import com.esime.oflinemovies.Data.Remoto.model.SessionResponse;
import com.esime.oflinemovies.app.MyApp;
import com.esime.oflinemovies.loginActivity.Data.Local.Dao.LoginDao;
import com.esime.oflinemovies.loginActivity.Data.Local.Entity.UserEntity;
import com.esime.oflinemovies.loginActivity.Data.Local.LoginRoomDatabase;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserRepositoy {
    private final LoginDao loginDao;
    private final MovieApiService LoginApiService;
    private MutableLiveData<GetTokenResponse> TokenRespone;
    private MutableLiveData<SessionResponse> sessionResponse;
    private LiveData<List<UserEntity>> listUsers;


    public UserRepositoy() {
        LoginRoomDatabase loginRoomDatabase = Room.databaseBuilder(
                MyApp.getContext(),
                LoginRoomDatabase.class,
                "db_user"
        ).build();
        loginDao = loginRoomDatabase.getDao();


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

        LoginApiService = retrofit.create(MovieApiService.class);

        sessionResponse = new MutableLiveData<>();

    }


    public MutableLiveData<SessionResponse> getToken() {

            Call<GetTokenResponse> responseCallGetToken = LoginApiService.getToken();
            responseCallGetToken.enqueue(new Callback<GetTokenResponse>() {
                @Override
                public void onResponse(Call<GetTokenResponse> call, Response<GetTokenResponse> response) {
                    if (response.isSuccessful()) {
                        getRequestToken(response.body().getRequestToken());
                    }
                }

                @Override
                public void onFailure(Call<GetTokenResponse> call, Throwable t) {
                    Toast.makeText(MyApp.getContext(), "GET_TOKEN", Toast.LENGTH_LONG).show();
                }
            });

        return sessionResponse;
    }


    public void getRequestToken(String Token){
        RequestToken requestToken = new RequestToken(ApiConstants.USER,ApiConstants.PASSWORD,Token);
        Call<GetTokenResponse> tokenResponseCall = LoginApiService.getRequestToken(requestToken);
        tokenResponseCall.enqueue(new Callback<GetTokenResponse>() {
            @Override
            public void onResponse(Call<GetTokenResponse> call, Response<GetTokenResponse> response) {
                if(response.isSuccessful()){
                    GetSessionId(response.body().getRequestToken());
                }
                else {
                    response.body();
                }
            }

            @Override
            public void onFailure(Call<GetTokenResponse> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "ERROR REQUEST", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void GetSessionId(String requestToken){
        RequestSession requestSession = new RequestSession(requestToken);

        Call<SessionResponse> responseCallGetSession = LoginApiService.getSessionId(requestSession);
        responseCallGetSession.enqueue(new Callback<SessionResponse>() {
            @Override
            public void onResponse(Call<SessionResponse> call, Response<SessionResponse> response) {
                if(response.isSuccessful()){
                    sessionResponse.setValue(response.body());
                    String session_id = response.body().getSessionId();
                    Toast.makeText(MyApp.getContext(), session_id+"session", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SessionResponse> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "ERROR GET_SESSION_ID", Toast.LENGTH_LONG).show();

            }
        });

    }

    public void InsertUser(String user, String password, String session_id){
        UserEntity userEntity = new UserEntity(user,password,session_id);
        loginDao.insertAll(userEntity);

    }

    public LiveData<List<UserEntity>> getAllUser(){
        listUsers = loginDao.getAllUser();
        return listUsers;
    }


    //Obtenemos la informacion (UserName y el password) del usuario
    public UserEntity getUserInformation(String username){
        UserEntity userEntity = loginDao.selectUser(username);
         String prueba = userEntity.getUserName();
        return userEntity;
    }

    //metodo para actualizar un usuario trayendo su idDB
    public void UpdateUser(String newUsername, String oldUsername){
        loginDao.updateUserDB(newUsername,oldUsername);
    }

    //metodo para actualizar password a la DB con Room
    public void updatePasswordDb(String newPassword, String oldPassword){
        loginDao.updatePasswordDB(newPassword,oldPassword);
    }

}


