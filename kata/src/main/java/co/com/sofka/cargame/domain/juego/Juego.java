package co.com.sofka.cargame.domain.juego;

import java.util.HashMap;
import java.util.Map;
import co.com.sofka.cargame.domain.juego.values.Pista;
import co.com.sofka.cargame.domain.juego.values.Podio;
import co.com.sofka.cargame.domain.juego.values.Values;
import co.com.sofka.cargame.domain.juego.values.Props;

public class Juego {
    Map <Integer, Jugador> jugadores = new HashMap<Integer, Jugador>();

    Pista pista;
    Boolean jugando;
    Podio podio;

    public Juego(Pista pista){
        this.pista = pista;
        this.podio = new Podio();
    }
    // Juego from(int juegoId, List<DomainEvent> eventos){
    //     return null;
    // }

  
    public void crearJugador(int jugadorId, String nombre, Color color){
        Jugador jugador = new Jugador(nombre, color);
        jugadores.put(jugadorId, jugador);
    }

    public void asignarPrimerLugar(int jugadorId)
    {
        Jugador jugador = jugadores.get(jugadorId);
        this.podio.asignarPrimerLugar(jugador);
    }

    public void asignarSegundoLugar(int jugadorId)
    {
        Jugador jugador = jugadores.get(jugadorId);
        this.podio.asignarSegundoLugar(jugador);
    }

    public void asignarTercerLugar(int jugadorId)
    {
        Jugador jugador = jugadores.get(jugadorId);
        this.podio.asignarTercerLugar(jugador);
    }

    public void iniciarJuego(){
        this.jugando = true;
    }

    public Map<Integer, Jugador> jugadores(){
        return this.jugadores;
    }  

    public Values pista(){
        return (Values)(this.pista);
    }

    public Boolean jugando(){
        return this.jugando;
    }

    public Props podio(){
        return (Props)(this.podio);
    }
}

