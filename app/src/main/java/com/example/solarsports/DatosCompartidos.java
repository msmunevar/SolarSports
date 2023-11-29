package com.example.solarsports;

public class DatosCompartidos {
    private static volatile DatosCompartidos instancia;

    private String nombreSeccion;
    private String nombreElemento;
    private String radiacion;
    private String consumo;
    private int diasMant;
    private double toneladaCO2;
    private int formRegistroId;

    // Nuevas variables
    private double consumoDifference;
    private double promedioRadiacion;
    private double promedioConsumo;
    private double porcentajeRaCon;
    private String porcentajeFormateado;

    private DatosCompartidos() {
        // Constructor privado para asegurar que solo haya una instancia
    }

    public static DatosCompartidos getInstance() {
        if (instancia == null) {
            synchronized (DatosCompartidos.class) {
                if (instancia == null) {
                    instancia = new DatosCompartidos();
                }
            }
        }
        return instancia;
    }

    // Métodos getter y setter para acceder a las variables

    public void setDatos(double consumoDifference, double promedioRadiacion,double promedioConsumo, double porcentajeRaCon, String porcentajeFormateado,int diasMant,double toneladaCO2) {
        this.consumoDifference = consumoDifference;
        this.promedioRadiacion= promedioRadiacion;
        this.promedioConsumo = promedioConsumo;
        this.porcentajeRaCon = porcentajeRaCon;
        this.porcentajeFormateado = porcentajeFormateado;
        this.diasMant = diasMant;
        this.toneladaCO2 = toneladaCO2;
    }

    // Nuevos métodos getter y setter
    public double getConsumoDifference() {
        return consumoDifference;
    }

    public double getToneladaCO2() {
        return toneladaCO2;
    }

    public int getDiasMant() {
        return diasMant;
    }

    public void setConsumoDifference(double consumoDifference) {
        this.consumoDifference = consumoDifference;
    }

    public double getPromedioRadiacion() {
        return promedioRadiacion;
    }

    public void setPromedioRadiacion(double promedioRadiacion) {
        this.promedioRadiacion = promedioRadiacion;
    }

    public double getPromedioConsumo() {
        return promedioConsumo;
    }

    public void setPromedioConsumo(double promedioConsumo) {
        this.promedioConsumo = promedioConsumo;
    }

    public double getPorcentajeRaCon() {
        return porcentajeRaCon;
    }

    public void setPorcentajeRaCon(double porcentajeRaCon) {
        this.porcentajeRaCon = porcentajeRaCon;
    }

    public String getPorcentajeFormateado() {
        return porcentajeFormateado;
    }
}
