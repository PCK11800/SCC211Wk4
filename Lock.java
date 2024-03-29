import java.util.*;
public class Lock{

    private boolean isLocked = false;
  
    public synchronized void lock() throws InterruptedException{
      while(isLocked){
        wait();
      }
      isLocked = true;
    }
  
    public synchronized void unlock(){
      isLocked = false;
      notify();
    }

    public synchronized boolean getStatus(){
        return isLocked;
    }
}