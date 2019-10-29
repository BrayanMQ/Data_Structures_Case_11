/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.regex.Pattern;

/**
 *
 * @author dark1
 */
public interface IConstants {
    public static String PATH_FILE = "src\\jsonFile\\links.json";
    public static Pattern PATRON_PALABRAS = Pattern.compile("[a-zA-Z]{4,}");
    public static Pattern PATRON_LINKS = Pattern.compile("^https://.*");
    public static String TAG_LINEAS = "p";
    public static String ESPACIO = " ";
    public static String TAG_HIPERVINCULO = "a";
    public static String TAG_LINK = "href";
    public static int TOP_CINCO = 5;
}
