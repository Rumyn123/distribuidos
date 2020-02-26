
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rumyn
 */
public class Fumador extends Thread{
    private int id;
    private Mesa mesa;
    private Random r = new Random();
    
    public Fumador(int id, Mesa mesa){
        this.mesa = mesa;
        this.id = id;
    }
    
    public void run(){
        while(true){
            try{
                mesa.inicioFumar(id);
                //Fumando
                Thread.sleep(r.nextInt(300));
                mesa.finFumar(id);
                //Terminó de fumar
                Thread.sleep(r.nextInt(300));
            }catch (InterruptedException ex){
                ex.printStackTrace();
            }
        }
    }
}
