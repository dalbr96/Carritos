package co.com.sofka.cargame.domain.carril.values;

public class Posicion implements Props{
    int actual;
    int meta;

    // Constructor
    public Posicion(int actual, int meta){
        this.actual = actual;
        this.meta = meta;

    }

    public void setActual(int metros){
        this.actual += metros;
    }

    // Implement PosicionProps interface
    public int actual(){
            return this.actual;
        }

    public int meta(){
        return this.meta;        
    }
}
