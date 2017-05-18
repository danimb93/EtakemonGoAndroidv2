package com.example.dani.etakemongo;

/**
 * Created by Dani on 18/05/2017.
 */

public class Contributor {

    public final String login;
    public final int contributions;

    public Contributor(String login, int contributions, String email) {
        this.login = login;
        this.contributions = contributions;
    }
}
