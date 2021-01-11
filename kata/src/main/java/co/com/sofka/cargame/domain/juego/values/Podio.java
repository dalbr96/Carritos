package co.com.sofka.cargame.domain.juego.values;

import co.com.sofka.cargame.domain.juego.Jugador;

public class Podio implements Props{
    
    //Instance variables
   private Jugador primerLugar;
   private Jugador segundoLugar;
   private Jugador tercerLugar;

   //Setter for primerLugar
   public Podio asignarPrimerLugar(Jugador jugador){
       this.primerLugar = jugador;
       return this;
   }

   //Setter for segundoLugar
   public Podio asignarSegundoLugar(Jugador jugador){
       this.segundoLugar = jugador;
       return this;
   }

   //Setter for tercerLugar
   public Podio asignarTercerLugar(Jugador jugador){
       this.tercerLugar = jugador;
       return this;
   }

   //Getter for primerLugar
   public Jugador primerLugar(){
       return this.primerLugar;
   }

   //Getter for segundoLugar
   public Jugador segundoLugar(){
       return this.segundoLugar;
   }

   //Getter for tercerLugar
   public Jugador tercerLugar(){
       return this.tercerLugar;
   }

   public Boolean estaLleno(){
       if((this.primerLugar() != null) && (this.segundoLugar != null) && (this.tercerLugar != null)) {
           return true;
       }
       else{
           return false;
       }
   }
}

