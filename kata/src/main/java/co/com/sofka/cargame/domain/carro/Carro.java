package co.com.sofka.cargame.domain.carro;

import co.com.sofka.cargame.domain.juego.Color;

public class Carro {

    //Instance Variables
    Conductor conductor;
    int distancia = 0;
    Color color;
    int juegoId;

    public Carro(Color color){
        this.color = color;
    }

    public void asignarConductor(Conductor conductor){
        this.conductor = conductor;
    }

    public void avanzarEnCarril(int position){
        this.distancia += position;
    }

    public Conductor conductor(){
        return this.conductor;
    }

    public int distancia(){
        return this.distancia;
    }

    public String color(){
        return this.color.name();
    }
}
