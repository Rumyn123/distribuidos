package sushi;

public class Comensal implements Runnable
{
    private String id;
    private Barra barra;
    public Comensal(Barra barra)
    {
        this.barra=barra;
        
    }
    @Override
    public void run()
    {
        while(true)
        {
            barra.quitar();
        }
    }
}
