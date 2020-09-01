package com.esime.oflinemovies.Data.Remoto.model;

import com.esime.oflinemovies.Data.Local.Entity.MovieEntity;
import com.esime.oflinemovies.Data.Local.Entity.TvEntity;

import java.util.List;

public class TvResponse {
    private List<TvEntity> results;

    public List<TvEntity> getResults() {
        return results;
    }

    public void setResults(List<TvEntity> results) {
        this.results = results;
    }
}
