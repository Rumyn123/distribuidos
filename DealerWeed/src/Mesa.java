/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rumyn
 */
public class Mesa {
    
    //0 - Mota, 1 - Papel, 2 - Cerillos
    //El dealer solo pone 2 ingredientes en la mesa, no puede poner nada más hasta que el que esté fumando acabe de fumar
    
    private int ingrediente = 0;
    private boolean fumando = false;
    private boolean ocupado = false;
    
    public synchronized void agregarIngrediente(int ingrediente) throws InterruptedException{
        while(ocupado || fumando){ //Mientras no esté ocupada la mesa (que haya ingredientes en la mesa) nadie esté fumando añade un nuevo ingrediente, sino pone a esperar al hilo
            wait();
        }
        ocupado = true;
        this.ingrediente = ingrediente;
        mensaje(ingrediente);
        notifyAll();
        
    }
    
    private void mensaje(int ingre){ //Permite mostrar lo que el dealer pondra en la mesa dependiendo el numero aleatorio el cual será igual al ingrediente que el dealer no pone
        switch(ingre){
            case 0:
                System.out.println("El Dealer pone Papel y Cerillos en la mesa");
                break;
            case 1:
                System.out.println("El dealer pone Mota y Cerillos en la mesa");
                break;
            case 2:
                System.out.println("El dealer pone Mota y Papel en la mesa");
                break;
        }
    }
    
    public synchronized void inicioFumar(int id) throws InterruptedException{
        while(!ocupado || fumando || ingrediente != id){ //Mientras no hayan ingredientes en la mesa, haya alguien fumando o los ingredientes no sean los que les corresponden al fumador, va a ponerse en wait()
            wait();
        }
        System.out.println("El Fumador "+id+" está fumando");
        ocupado = false;
        fumando = true;
    }
    
    public synchronized void finFumar(int id){
        fumando = false;
        System.out.println("El Fumador "+id+" terminó de fumar");
        notifyAll();
    }
    
}
