package com.uqac.informatiquemobile.epicerie.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.uqac.informatiquemobile.epicerie.dataBase.DataBaseManager;
import com.uqac.informatiquemobile.epicerie.metier.Repas;

import java.util.ArrayList;

/**
 * Created by paull on 16/04/2016.
 */
public class DateRecetteService extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    public class MyBinder extends Binder{
        public DateRecetteService getService(){
            return DateRecetteService.this;
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("Service lancé !");
        final DataBaseManager dbm = new DataBaseManager(getApplicationContext());


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){


                    ArrayList<Repas> repas  = new ArrayList<>();
                    repas = dbm.getRepasPlanifies();
                    System.out.println(repas);



                    try{
                        Thread.sleep(1000);
                    } catch (InterruptedException ie){
                        ie.printStackTrace();
                    }





                }
            }
        });

        t.start();


    }
}
