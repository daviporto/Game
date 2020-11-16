package jogo.util;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

public class NumberFormaters {


	
    public static DateFormat DATE_SQL = new SimpleDateFormat("yyyy-MM-dd");    
    public static DateFormat DATE_HOUR_SQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static DateFormat DATE_BR = new SimpleDateFormat("dd-MM-yyyy");    
    public static DateFormat DATE_BR_YY = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
    public static DateFormat DATE_BR_FULL = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public static DateFormat TIME = new SimpleDateFormat("HH:mm:ss");
    public static DateFormat DATE_DAY = new SimpleDateFormat("dd");


    public static NumberFormat NF_DEC = new DecimalFormat("#,##0.00");
    public static NumberFormat NF_NDEC = new DecimalFormat("#,##0");


    public static NumberFormat NF_5DIG = new DecimalFormat("00000");
    public static NumberFormat NF_6DIG = new DecimalFormat("000000");

    public static NumberFormat NF_4DEC = new DecimalFormat("0.0000");

    public static NumberFormat NF_2DEC2 = new DecimalFormat("#,###.##");
    
}

