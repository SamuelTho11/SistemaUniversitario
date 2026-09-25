/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.miapp.modelo;

import com.miapp.servicios.IBuscador;

/**
 *
 * @author Samuel
 */
public class Profesor extends Persona {
    private final double salarioBase;

    public Profesor(double salarioBase, String nombre, String apellido, int id) {
        super(nombre, apellido, id);
        this.salarioBase = salarioBase;
    }
    
    public void impartirClase(){
        
    }

    @Override
    public double calcularPago() {
        //salario base por las horas
        return salarioBase;
    }

}
