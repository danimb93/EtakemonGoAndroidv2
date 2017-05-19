package com.example.dani.etakemongo;

/**
 * Created by Dani on 18/05/2017.
 */

public class Contributor {

    public final String login;
    public final int contributions;
    public final String url;

    public Contributor(String login, int contributions, String url) {
        this.login = login;
        this.contributions = contributions;
        this.url=url;
    }
}
