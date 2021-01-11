package co.com.sofka.cargame.domain.carro;

import java.util.Random;

public class Conductor {
    //Instance Variables
    String nombre;

    //Constructor
    public Conductor(String nombre){
        this.nombre = nombre;
    }

    public String nombre(){
        return this.nombre;
    }

    public int lanzarDado(){
        
        Random rand = new Random();
        int upperbound = 6;

        //Return random integer between 1-6

        return (rand.nextInt(upperbound) + 1);
    }
}
