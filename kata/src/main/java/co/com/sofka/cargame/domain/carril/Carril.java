package co.com.sofka.cargame.domain.carril;

import co.com.sofka.cargame.domain.carril.values.Posicion;
import co.com.sofka.cargame.domain.carro.Carro;

public class Carril{
    //Instance Variables
    
    Carro carro;
    int juegoId;
    Posicion position;
    int metros;
    Boolean desplazamientoFinal;

    public Carril(Posicion position){
        this.position = position;
        this.metros = position.actual();
        this.desplazamientoFinal = false;
    }
    // Carril from(int carrilId, List<Carril> lista){

    // }
    
    public void agregarCarro(Carro carro){
        this.carro = carro;
    }

    public void alcanzarLaMeta(){

        if(this.posicionActual() >= this.posicionDeseada())
        {
            this.desplazamientoFinal = true;
        }
        else
        {
            this.desplazamientoFinal = false;
        }
    }

    public void moverCarro(int metros){
        this.position.setActual(metros);
        this.carro.avanzarEnCarril(metros);
    }

    public int metros(){
        return this.metros;
    }

    public int posicionActual(){
        return this.position.actual();
    }

    public int posicionDeseada(){
        return this.position.meta();
    }

    public Boolean desplazamientoFinal(){
        return this.desplazamientoFinal;
    }

    public Carro carro(){
        return this.carro;
    }
}
