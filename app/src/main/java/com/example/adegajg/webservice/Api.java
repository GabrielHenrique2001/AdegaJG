package com.example.adegajg.webservice;

public class Api {
    private static final String ROOT_URL = "http://www.irmy.com.br/prova/adegaapi/v1/Api.php?apicall=";

    public static String URL_CREATE_APPADEGA = ROOT_URL + "createappadega";
    public static String URL_READ_APPADEGA = ROOT_URL + "getappadega";
    public static String URL_UPDATE_APPADEGA = ROOT_URL + "updateappadega";
    public static String URL_DELETE_APPADEGA = ROOT_URL + "deleteappadega&id=";

}
