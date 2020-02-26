/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rumyn
 */
public class MainWeed {
    
    public static void main(String args[]){
        
        Mesa mesa = new Mesa(); //Creamos una nueva mesa que se enviará como parametro al fumador y al dealer para que trabajen donde mismo
        Dealer dealer = new Dealer(mesa); //Creamos al dealer y le mandamo sla mesa en la que queremos que trabaje
        Fumador[] fumador = new Fumador[3]; //Creamos 3 fumadores
        for(int i=0; i<fumador.length; i++){ //Hacemos un for para inicializar a cada fumador con id de 0 a 2 en la misma mesa
            fumador[i]= new Fumador(i, mesa);
        }
        dealer.start(); //Iniciamos el run() del dealer
        for(int i=0; i<fumador.length; i++){ //Iniciamos el run() de los 3 fumadores del 0 al 2
            fumador[i].start();
        }
        
        
        
    }
    
}
