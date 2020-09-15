package br.com.alura.roomapplication.database.conversor;

import android.arch.persistence.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Conversor_data {

      public static final String PADRAO_DATA = "dd/MM/yyyy";
      public static final SimpleDateFormat FORMATADOR = new SimpleDateFormat(PADRAO_DATA);

      @TypeConverter
      public static Long converte(Calendar calendarData){

          return calendarData.getTime().getTime();
      }

      @TypeConverter
      public static Calendar calendar(Long milisegundos){
          Calendar calendar = Calendar.getInstance();
          calendar.setTime(new Date(milisegundos));
          return calendar;
      }

      public static Calendar calendar_string(String data){

          Calendar calendar = Calendar.getInstance();

          try {

              Date date = FORMATADOR.parse(data);

              calendar.setTime(date);

          } catch (ParseException e) {
              e.printStackTrace();
          }

          return calendar;
      }

      public static String converteData(Calendar data){

          String dataFormatada = FORMATADOR.format(data.getTime());
          return dataFormatada;
      }

}
