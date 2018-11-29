package com.example.joakk.letmetakeyouto;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.PropertyName;

import java.util.List;

public class Tienda {
    private String DOCUMENTID;
    private String NOMBRE_TIENDA;
    private GeoPoint COORDENADAS;
    private List<String> PRODUCTOS;
    private String DIRECCION;
    private String TIPO_TIENDA;

    public Tienda() {

    }
    public Tienda (String nombre, GeoPoint point){
    this.NOMBRE_TIENDA = nombre;
    this.COORDENADAS = point;
    }

    public Tienda(String nombre, GeoPoint coords, String direccion, String tipo_tienda, String docid, List<String> products) {
        this.NOMBRE_TIENDA = nombre;
        this.COORDENADAS = coords;
        this.DIRECCION = direccion;
        this.TIPO_TIENDA = tipo_tienda;
        this.DOCUMENTID = docid;
        this.PRODUCTOS = products;
    }

    @PropertyName("Tienda")
    public List<String> getPRODUCTOS(){
        return PRODUCTOS;
    }

    @PropertyName("Tienda")
    public void setPRODUCTOS(List<String> productos){
        this.PRODUCTOS = productos;
    }


    public GeoPoint getCOORDENADAS() {
        return COORDENADAS;
    }

    public void setCOORDENADAS(GeoPoint COORDENADAS) {
        this.COORDENADAS = COORDENADAS;
    }

    public String getNOMBRE_TIENDA() {
        return NOMBRE_TIENDA;
    }

    public void setNOMBRE_TIENDA(String NOMBRE_TIENDA) {
        this.NOMBRE_TIENDA = NOMBRE_TIENDA;
    }

    public String getDIRECCION() {
        return DIRECCION;
    }

    public void setDIRECCION(String DIRECCION) {
        this.DIRECCION = DIRECCION;
    }

    public String getTIPO_TIENDA() {
        return TIPO_TIENDA;
    }

    public void setTIPO_TIENDA(String TIPO_TIENDA) {
        this.TIPO_TIENDA = TIPO_TIENDA;
    }

    @Exclude
    public String getDOCUMENTID() {
        return DOCUMENTID;
    }

    public void setDOCUMENTID(String DOCUMENTID) {
        this.DOCUMENTID = DOCUMENTID;
    }
}
