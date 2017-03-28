package com.example.gebruiker.nathalievansterkenburg_pset6;

import java.io.Serializable;

/**
 * Created by Nathalie on 28-3-2017.
 */

public class School implements Serializable{
    // object that contains info on school

    private String school;
    private String niveaus;
    private String plaats;
    private String adres;
    private String website;
    private String nummer;

    // initialize school
    public School(){}

    public String getSchool() {
        return school;
    }

    // getters and setters for all info
    public void setSchool(String school) {
        this.school = school;
    }

    public String getNiveaus() {
        return niveaus;
    }

    public void setNiveaus(String niveaus) {
        this.niveaus = niveaus;
    }

    public String getPlaats() {
        return plaats;
    }

    public void setPlaats(String place) {
        this.plaats = place;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getNummer() {
        return nummer;
    }

    public void setNummer(String nummer) {
        this.nummer = nummer;
    }
}
