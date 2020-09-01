package com.esime.oflinemovies.loginActivity.Data.Local.Entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class UserEntity {

    @PrimaryKey(autoGenerate = true)
    private int idUser;

    private String userName;
    private String password;
    private String session_id;


    public UserEntity(String userName, String password, String session_id){
        this.userName = userName;
        this.password = password;
        this.session_id = session_id;
    }

    public int getIdUser() {
        return idUser;
    }
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }
}
