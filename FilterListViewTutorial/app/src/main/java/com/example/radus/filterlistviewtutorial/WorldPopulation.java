package com.example.radus.filterlistviewtutorial;

/**
 * Created by radusqrt on 12/1/2017.
 */

public class WorldPopulation {
    private String rank, country, population;

    public WorldPopulation(String rank, String country, String population) {
        this.rank = rank;
        this.country = country;
        this.population = population;
    }

    public String getRank() {
        return rank;
    }

    public String getCountry() {
        return country;
    }

    public String getPopulation() {
        return population;
    }
}
