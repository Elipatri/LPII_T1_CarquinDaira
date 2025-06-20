package com.empresa.alquiler.model;

import java.io.Serializable;
import java.util.Objects;

public class DetalleAlquilerId implements Serializable {
    private int alquiler;
    private int pelicula;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DetalleAlquilerId)) return false;
        DetalleAlquilerId that = (DetalleAlquilerId) o;
        return Objects.equals(alquiler, that.alquiler) &&
               Objects.equals(pelicula, that.pelicula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alquiler, pelicula);
    }
}
