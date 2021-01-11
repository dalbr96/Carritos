package co.com.sofka.cargame.domain.juego;

public class Jugador{
    String nombre;
    Color color;
    int puntos;

    public Jugador(String nombre, Color color){
        
        this.nombre = nombre;
        this.color = color;

    }

    public String nombre(){
        return this.nombre;
    }

    public Color color(){
        return this.color;
    }
    
    public void asignarPuntos(int puntos){
        this.puntos += puntos;
    }
}


