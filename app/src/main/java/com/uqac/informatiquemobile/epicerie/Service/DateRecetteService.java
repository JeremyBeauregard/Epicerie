package com.uqac.informatiquemobile.epicerie.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import com.uqac.informatiquemobile.epicerie.activity.recette.DetailedRecetteActivity;
import com.uqac.informatiquemobile.epicerie.dataBase.DataBaseManager;
import com.uqac.informatiquemobile.epicerie.metier.Repas;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by paull on 16/04/2016.
 */
public class DateRecetteService extends Service {
    boolean serviceStopped;
    private DataBaseManager dbm;

    private Handler mHandler;
    private Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            if (serviceStopped == false)
            {
                createNotificationIcon();
            }
            queueRunnable();
        }
    };

    private void queueRunnable() {
        // 600000 : cada 10 minutos, comprueba si hay nuevas notificaciones y actualiza la
        // notification BAR
        mHandler.postDelayed(updateRunnable, 5000);

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        dbm = new DataBaseManager(getApplicationContext());
        serviceStopped = false;

        // //////////////////////////////////////MANEJADOR SIMILAR A UN HILO
        mHandler = new Handler();
        queueRunnable();
        // ///////////////////////////////////// FIN MANEJADOR
    }

    @Override
    public void onDestroy() {
        serviceStopped = true;
    }

    @Override
    public void onStart(Intent intent, int startid) {

    }

    public void createNotificationIcon()
    {
        //Log.d("MyServiceNotifications", "Hello");


        ArrayList<Repas> repas  = new ArrayList<>();
        repas = dbm.getRepasPlanifies();
        System.out.println(repas);


        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        int hour = c.get(Calendar.HOUR)%12;
        int minute = c.get(Calendar.MINUTE);


        for (Repas r :
                repas) {

            Date temp = new Date(year-1900, month, day, hour, minute);


            if (temp.after(r.getDatePreparation())){
                Toast.makeText(getApplicationContext(), r.getRecette().getNom(), Toast.LENGTH_SHORT).show();
                Intent mIntent = new Intent(getApplicationContext(), DetailedRecetteActivity.class);
                Bundle extras = new Bundle();
                extras.putInt("id", r.getRecette().getId());
                mIntent.putExtras(extras);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                dbm.supprimerRepas(r.getId());

                startActivity(mIntent);


            }


            System.out.println(temp.after(r.getDatePreparation()));
            System.out.println(temp.before(r.getDatePreparation()));
            System.out.println("TEMP = "+temp);
            System.out.println("RECETTE = "+r.getDatePreparation());
        }



    }
}
