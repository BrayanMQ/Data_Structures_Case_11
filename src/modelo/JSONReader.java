/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.ArrayList;

import modelo.JSONData;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Ronny
 */
public class JSONReader {
    
    private String rutaArchivo;
    
    public JSONData convertJSonToData(){
        ArrayList<String> listaLinks = new ArrayList<>();
        Long anchura = new Long(0);
        
        Long profundidad = new Long(0);
        
        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader("C:\\Users\\Ronny\\OneDrive\\Documentos\\NetBeansProjects\\Caso_11\\Data_Structures_Case_11\\src\\jsonFile\\test.json"));

            JSONObject jsonObject = (JSONObject) obj;
                
            anchura = (Long) jsonObject.get("Anchura");
            
            profundidad = (Long) jsonObject.get("Profundidad");
		
            JSONArray links = (JSONArray) jsonObject.get("Links");
            Iterator<String> iterator = links.iterator();
            while (iterator.hasNext()) {
                listaLinks.add(iterator.next());
            }
        } catch (FileNotFoundException e) {
            //manejo de error
        } catch (IOException e) {
            //manejo de error
	} catch (ParseException e) {
            //manejo de error
	}
        
        
            
        JSONData jsonData = new JSONData(listaLinks, anchura.intValue(), profundidad.intValue());
            
        return jsonData;
    } 
    
}
