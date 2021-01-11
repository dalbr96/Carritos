package co.com.sofka.cargame.domain.juego.values;

public class Pista implements Values{
    //Intance Variables
    private int kilometros;
    private int numeroDeCarriles;

    //Define Constructor
    public Pista(int kilometros, int numeroDeCarriles){
        this.kilometros = kilometros;
        this.numeroDeCarriles = numeroDeCarriles;
    }

    //Getter for kilometros
    public int kilometros(){
        return this.kilometros;
    }

    //Getter for numeroDeCarriles  
    public int numeroDeCarriles(){
        return this.numeroDeCarriles;
    }
}

