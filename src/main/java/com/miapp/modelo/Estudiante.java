package com.miapp.modelo;

import java.util.List;

/**
 * Modelo: representa la entidad Estudiante.
 */
public final class Estudiante extends Persona {  

    private static int totalEstudiantes = 0;
    public static final int PROMEDIO_MINIMO = 0;
    public static final int PROMEDIO_MAXIMO = 5;
    public static final String CARRERA_PREDETERMINADA = "Sin especificar";
    public static final int MAX_MATERIAS = 12;

    // ── Atributos de instancia ────────────────────────────────────────────────
    private String carrera;
    private double promedio;

    // ── Constructor ───────────────────────────────────────────────────────────

    public Estudiante(int id, String nombre, String apellido, String carrera, double promedio) {
        super(nombre, apellido, id);
        this.carrera  = carrera;
   
        if (promedio >= PROMEDIO_MINIMO && promedio <= PROMEDIO_MAXIMO) {
            this.promedio = promedio;
        } else {
            this.promedio = 0.0;  // Por defecto si está fuera de rango
        }
        
        // nuevo: Incrementa el contador estático de estudiantes
        totalEstudiantes++;
    }

    // ── Métodos estáticos (de clase) ──────────────────────────────────────────

    public static int getTotalEstudiantes() {
        return totalEstudiantes;
    }

    public static void reiniciarContador() {
        totalEstudiantes = 0;
    }

    public static int getProximoId() {  
        return totalEstudiantes + 1;
    
    }

    // ── Getters ──────────────────────────────────────────────────────────────

    public int getId() { 
        return id; 
    }

    public String getCarrera() { 
        return carrera; 
    }

    public double getPromedio() { 
        return promedio; 
    }

    // ── Setters ──────────────────────────────────────────────────────────────

    public void setId(int id) { 
        this.id = id; 
    }

    public void setCarrera(String carrera) { 
        this.carrera = carrera; 
    }

    /**
     Valida el promedio antes de asignarlo usando constantes finales
     * @param p promedio a validar (debe estar entre PROMEDIO_MINIMO y PROMEDIO_MAXIMO)
     */
    public void setPromedio(double p) {
        // nuevo: Uso de constantes finales para validación
        if (p >= PROMEDIO_MINIMO && p <= PROMEDIO_MAXIMO) {
            this.promedio = p;
        }
    }
    /**
     Método final: no puede ser sobrescrito por subclases
     * @return 
     */
    @Override
    public String toString() {
        return super.personaToString() + "carrera=" + carrera + ", promedio=" + promedio + '}';
    }

    @Override
    public double calcularPago() {
        return 0;
    }
}