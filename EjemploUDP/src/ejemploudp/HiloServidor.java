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


public class HiloServidor extends Thread
{
    private DatagramSocket ds;
    private DatagramPacket dp;
    private int PUERTO;
    private Servidor servidor;
    private byte[] msg;
    
    public HiloServidor(Servidor servidor)
    {
        this.servidor=servidor;
        try
        {
            PUERTO=Integer.parseInt(servidor.getPuerto());
        }catch (NumberFormatException e2){
            servidor.setMensaje(servidor.getMensaje()+"Error al obtener el puerto de envio!!\n");
        }
        msg=new byte[1024];
        try {
            //Creamos el socket UDP del servidor en el pueto asociado
            ds = new DatagramSocket(PUERTO);
        } catch (SocketException ex) {
            servidor.setMensaje(servidor.getMensaje()+"Error al abrir el puerto!!\n");
        }
        servidor.setMensaje(servidor.getMensaje()+"Servidor activo!!\n");

    }
    public void run()
    {
       //implementacion del protocolo del servidor en un bucle infinito
        while (servidor.getActivo())
        {
            msg=new byte[1024];
            dp = new DatagramPacket(msg,1024);
            servidor.setMensaje(servidor.getMensaje()+"Esperando peticion...\n");
            try {
                //llega un datagrama
                ds.receive(dp);
            } catch (IOException ex) {
                servidor.setMensaje(servidor.getMensaje()+"Error al recibir el mensaje!!\n");
            }
            if (!servidor.getActivo())
            {
                ds.close();
                return;
            }
            servidor.setMensaje(servidor.getMensaje()+"\nNueva peticion recibida\n");
            servidor.setMensaje(servidor.getMensaje()+"Procedente de:" + dp.getAddress()+"\n");
            servidor.setMensaje(servidor.getMensaje()+"Por el puerto:" + dp.getPort()+"\n");
            servidor.setMensaje(servidor.getMensaje()+"Atendiendo la petición...\n");

            //se prepara el mensaje a enviar con la fecha del sistema
            String message=new String("Hola! "+new String(dp.getData())+", mucho gusto!");
            msg=message.getBytes();

            //se crea el datagrama que contendrá al mensaje 
           dp=new DatagramPacket(msg,msg.length,dp.getAddress(),dp.getPort());

            try {
                //se le envia al cliente
                ds.send(dp);
            } catch (IOException ex) {
                servidor.setMensaje(servidor.getMensaje()+"Error al enviar el mensaje!!\n");
            }
            servidor.setMensaje(servidor.getMensaje()+"Respuesta enviada");
        }

    }
    public synchronized void notificar()
    {
        notify();
    }
    
}
