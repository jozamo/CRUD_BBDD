package com.jozamo74.crud_bbdd;

import android.provider.BaseColumns;

/**
 * Created by jozamo on 1/08/17.
 */

public class Estructura_BBDD {

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private Estructura_BBDD() {
    }

    /* Inner class that defines the table contents */
    //public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "datosPersonales";
        public static final String CAMPO1 = "ID";
        public static final String CAMPO2= "nombre";
        public static final String CAMPO3= "apellidos";

    //}

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    protected static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Estructura_BBDD.TABLE_NAME + " (" +
                    Estructura_BBDD.CAMPO1 + " INTEGER PRIMARY KEY," +
                    Estructura_BBDD.CAMPO2 + TEXT_TYPE + COMMA_SEP +
                    Estructura_BBDD.CAMPO3 + TEXT_TYPE + " )";

    protected static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Estructura_BBDD.TABLE_NAME;

}
