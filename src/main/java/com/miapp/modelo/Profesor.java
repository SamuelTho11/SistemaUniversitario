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
    private int horasDictadas;
    private double tarifaPorHora;

    public Profesor(double salarioBase, String nombre, String apellido, int id) {
        super(nombre, apellido, id);
        this.salarioBase = salarioBase;
        this.tarifaPorHora = 50000.0;
        this.horasDictadas = 0;
    }

    public int getHorasDictadas() {
        return horasDictadas;
    }

    public void setHorasDictadas(int horasDictadas) {
        this.horasDictadas = horasDictadas;
    }

    public double getTarifaPorHora() {
        return tarifaPorHora;
    }

    public void setTarifaPorHora(double tarifaPorHora) {
        this.tarifaPorHora = tarifaPorHora;
    }
    
    
    
    public void impartirClase(int horas){
        if(horas>0){
            this.horasDictadas+=horas;
            System.out.println("El profesor "+ getNombre() +" dictó "+ horas+ " horas de clase.");
        }
    }

    @Override
    public double calcularPago() {
        return salarioBase + (horasDictadas * tarifaPorHora);
    }

}
