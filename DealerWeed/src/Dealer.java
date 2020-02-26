
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Alumnos
 */
public class Dealer extends Thread{
    
    private Mesa mesa;
    private Random r = new Random();
    
    public Dealer(Mesa mesa){
        this.mesa = mesa;
    }
    
    public void run(){
        while(true){
            int ingrediente = r.nextInt(3); //Esto dará un numero aleatorio entre 0 y 2
            try{
                Thread.sleep(r.nextInt(300));
                mesa.agregarIngrediente(ingrediente); //Agrega el nuevo ingrediente a la mesa
                
            }catch (InterruptedException ex){
                ex.printStackTrace();
            }
        }
    }
}
