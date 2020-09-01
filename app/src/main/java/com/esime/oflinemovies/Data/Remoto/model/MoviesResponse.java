package com.esime.oflinemovies.Data.Remoto.model;

import com.esime.oflinemovies.Data.Local.Entity.MovieEntity;

import java.util.List;

public class MoviesResponse {
    private List<MovieEntity> results;
    private  int page;
    private  int total_pages;


    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }



    public List<MovieEntity> getResults() {
        return results;
    }

    public void setResults(List<MovieEntity> results) {
        this.results = results;
    }
}
