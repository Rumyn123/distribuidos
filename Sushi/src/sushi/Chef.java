package sushi;


public class Chef extends Thread
{
    private Barra barra;
    public Chef(Barra barra)
    {
        this.barra=barra;
        
    }
    public void run()
    {
        while(true)
        {
            barra.poner();
        }
    }
}
