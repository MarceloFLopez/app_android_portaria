package br.com.appportaria.application.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateSerializer implements JsonSerializer<Date>{
    //private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public JsonElement serialize(Date date, Type typeOfSrc, JsonSerializationContext context){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));//GMT
        return new JsonPrimitive(format.format(date));
    }
}