package br.com.appportaria.application.util;

import android.content.Context;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilApp {
    //Mensagens Toast
    public void msgToastLong(String msg, Context context){
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
    public void msgToastShort(String msg, Context context){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
    public String getDataHoraStringFormatada(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String strDate = dateFormat.format(date);
        return strDate;
    }

}
