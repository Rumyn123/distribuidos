/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejemploudp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;




public class HiloCliente extends Thread //Se usa porque estoy jalando un hilo aparte a la interfaz
{
    private DatagramSocket ds; //El socket que usa para viajar
    private DatagramPacket dp; //El sobre 
    private InetAddress destino; //El destino
    private byte[] msg; //Carta
    private int PUERTO;
    private Cliente cliente; //Referencia al cliente
    
    public HiloCliente(Cliente cliente)
    {
        this.cliente=cliente;
        msg = new byte[1024]; //No se recomienda enviar mensajes de mas de 1024 bytes
        cliente.setMensaje("Cliente iniciado \n");
          try {
                        // Establecemos en socket bajo UDP
                        ds = new DatagramSocket();
                    } catch (SocketException ex) {
                       cliente.setMensaje(cliente.getMensaje()+"Error al iniciar el socket!!\n");
                    }
    }
   
    @Override
    public  void run() {
        
            while (cliente.getActivo())
            {
                synchronized(this)
                {
                    try
                    {
                        wait();
                    }catch(InterruptedException ex){}
                    
                }
                if (!cliente.getActivo())
                {
                    if (ds!=null)
                    {
                        ds.close();
                        System.out.println("Salio");
                    }
                    return;
                }
                
                try
                {
                    destino=InetAddress.getByName(cliente.getIP()); //Convierte una cadena en una IP en caso de que la ip este incorrecta, manda una excepcion
                }catch (UnknownHostException e1){
                    cliente.setMensaje(cliente.getMensaje()+"Error al obtener la IP de envio!!\n");
                    continue;
                }
                try
                {
                    PUERTO=Integer.parseInt(cliente.getPuerto());
                }catch (NumberFormatException e2){
                    cliente.setMensaje(cliente.getMensaje()+"Error al obtener el puerto de envio!!\n");
                    continue;
                }
              
                 //datagrama, con el mensaje, la longitud, la dirección y el puerto
                dp = new DatagramPacket (msg, msg.length, destino, PUERTO);
                try {
                    //enviamos el datagrama
                    ds.send(dp);
                } catch (IOException ex) {
                    cliente.setMensaje(cliente.getMensaje()+"Error al enviar el mensaje!!\n");
                }
                cliente.setMensaje(cliente.getMensaje()+"Peticion enviada al servidor...\n");
                dp = new DatagramPacket (msg, msg.length);
                //esperamos que nos llegue respuesta desde el servidor 
                cliente.setMensaje(cliente.getMensaje()+"Esperando respuesta...\n");
                try {
                    ds.receive(dp);
                } catch (IOException ex) {
                    cliente.setMensaje(cliente.getMensaje()+"Error al recibir el mensaje!!\n");
                }
                cliente.setMensaje(cliente.getMensaje()+"Respuesta recibida...\n");
                //ha llegado un datagrama, para ver los datos se utiliza getData()
                String received = new String (dp.getData()); //Extrae los bytes del mensaje
                cliente.setMensaje(cliente.getMensaje()+"Mensaje obtenido: " + received.trim()+"\n");
            }
            
        
    }
    public synchronized void notificar()
    {
        notify();
    }
    
    
}