package com.mygdx.game.Settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.math.Interpolation;

public class Server {

    static int responseLogin = 0;

    //static JSONObject jsonObject = new JSONObject();

    public static int sendHttpRequestAndroidRegister(String name,String pwd,String email) {
        //String url = "http://192.168.207.59:7151/registerUserAndroid/"+name+"/"+pwd+"/"+email;

        String url = "http://localhost:3007/registerUserAndroid";
       // System.out.println(url);
        Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.GET);
        httpRequest.setUrl(url);
        httpRequest.setHeader("Content-Type", "application/json");
        httpRequest.setTimeOut(5000);
        httpRequest.setContent("");


        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {

            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                int response = Integer.valueOf(httpResponse.getResultAsString());
                responseLogin = response;
            }

            @Override
            public void failed(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void cancelled() {
                System.out.println("HTTP request cancelled.");
            }
        });
        try {
            Thread.sleep(3000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //System.out.println(jsonObject);
        //System.out.println(responseLogin);
        return responseLogin;
    }
}
