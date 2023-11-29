package com.example.solarsports.models;

public class Usuario {
    private int idUsuario;
    private String nombreUsuario;

    public Usuario(int idUsuario, String nombreUsuario) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    // Sobrescribe el método toString() para que el Spinner muestre el nombre del usuario correctamente
    @Override
    public String toString() {
        return nombreUsuario;
    }
}
