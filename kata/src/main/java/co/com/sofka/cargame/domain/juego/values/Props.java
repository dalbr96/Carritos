package co.com.sofka.cargame.domain.juego.values;

import co.com.sofka.cargame.domain.juego.Jugador;

public interface Props {
    
    Jugador primerLugar();
    Jugador segundoLugar();
    Jugador tercerLugar();

    Boolean estaLleno();
}
