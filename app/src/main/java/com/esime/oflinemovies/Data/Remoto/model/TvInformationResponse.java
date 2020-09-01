
package com.esime.oflinemovies.Data.Remoto.model;

import java.util.ArrayList;
import java.util.List;

import com.esime.oflinemovies.Data.Local.Entity.GenerosEntity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TvInformationResponse {

    @SerializedName("created_by")
    @Expose
    private List<Object> createdBy = new ArrayList<Object>();
    @SerializedName("episode_run_time")
    @Expose
    private List<Object> episodeRunTime = new ArrayList<Object>();
    @SerializedName("first_air_date")
    @Expose
    private String firstAirDate;
    @SerializedName("genres")
    @Expose
    private List<GenerosEntity> genres = new ArrayList<GenerosEntity>();
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("in_production")
    @Expose
    private boolean inProduction;
    @SerializedName("last_air_date")
    @Expose
    private String lastAirDate;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("networks")
    @Expose
    private List<Object> networks = new ArrayList<Object>();
    @SerializedName("number_of_episodes")
    @Expose
    private int numberOfEpisodes;
    @SerializedName("number_of_seasons")
    @Expose
    private int numberOfSeasons;
    @SerializedName("original_name")
    @Expose
    private String originalName;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("popularity")
    @Expose
    private double popularity;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("seasons")
    @Expose
    private List<Object> seasons = new ArrayList<Object>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("vote_average")
    @Expose
    private double voteAverage;
    @SerializedName("vote_count")
    @Expose
    private int voteCount;

    public List<Object> getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(List<Object> createdBy) {
        this.createdBy = createdBy;
    }

    public List<Object> getEpisodeRunTime() {
        return episodeRunTime;
    }

    public void setEpisodeRunTime(List<Object> episodeRunTime) {
        this.episodeRunTime = episodeRunTime;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public List<GenerosEntity> getGenres() {
        return genres;
    }

    public void setGenres(List<GenerosEntity> genres) {
        this.genres = genres;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isInProduction() {
        return inProduction;
    }

    public void setInProduction(boolean inProduction) {
        this.inProduction = inProduction;
    }

    public String getLastAirDate() {
        return lastAirDate;
    }

    public void setLastAirDate(String lastAirDate) {
        this.lastAirDate = lastAirDate;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Object> getNetworks() {
        return networks;
    }

    public void setNetworks(List<Object> networks) {
        this.networks = networks;
    }

    public int getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    public void setNumberOfEpisodes(int numberOfEpisodes) {
        this.numberOfEpisodes = numberOfEpisodes;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public List<Object> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Object> seasons) {
        this.seasons = seasons;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

}
