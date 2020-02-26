package sushi;


public class Barra 
{
    public int n;
    public boolean ocupado;
    public Barra()
    {
        n=0;
        ocupado=false;
    }
    public synchronized void poner()
    {
        while(ocupado||n>=1)
        {
            try
            {
                wait();
            }catch(InterruptedException e)
            {
                
            }
        }
        ocupado=true;
        n++;
        System.out.println("Sushi servido");
        ocupado=false;
        notify();
    }
    public synchronized void quitar()
    {
        while(ocupado||n<=0)
        {
            try
            {
                wait();
            }catch(InterruptedException e)
            {
                
            }
        }
        ocupado= true;
        n--;
        System.out.println("Yumi!!!");
        ocupado=false;
        notify();
    }
    
}
