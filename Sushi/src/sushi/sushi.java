package sushi;


public class Sushi {

    
    public static void main(String[] args) {
        
        Barra barra= new Barra();
        Thread h1,h2;
        h1=new Chef(barra);
        h2=new Thread(new Comensal(barra));
        h1.start();
        h2.start();
        
    }
    
}
