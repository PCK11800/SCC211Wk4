import java.util.*;

public class Buffer{
    private LinkedList<Object> bufferList = new LinkedList<>();
    private int bufferNum;

    private Lock lock = new Lock();

    public Buffer(int bufferNum){
        this.bufferNum = bufferNum;
    }

    public void synchronizedAddToBuffer(User thisUser) throws InterruptedException{
        lock.lock();
        try{
            addToBuffer(thisUser);
        }finally{
            lock.unlock();
        }
    }

    public void synchronizedRemoveFromBuffer(Server thisServer) throws InterruptedException{
        lock.lock();
        try{
            removeFromBuffer(thisServer);
        }finally{
            lock.unlock();
        }
    }

    private void addToBuffer(User thisUser){
        if(bufferList.size() >= bufferNum){
            System.out.println("Buffer Full: User " + thisUser.getID() + " is now asleep");
        }
        else{
            bufferList.add(new Object());
            thisUser.addOne();
            System.out.println("User " + thisUser.getID() + " added an element " + bufferList.size() + "/" + bufferNum);
        }
    }

    public void removeFromBuffer(Server thisServer){
        if(bufferList.size() <= 0){
            System.out.println("Buffer Empty: Server " + thisServer.getID() + " is now asleep");
        }
        else{
            bufferList.removeLast();
            thisServer.addOne();
            System.out.println("Server " + thisServer.getID() + " removed an element " + bufferList.size() + "/" + bufferNum); 
        }
    }

    public void sleep(){
        try{
            Thread.sleep(100);
        }catch(InterruptedException ie){
            //Do nothing
        }
    }

    public int getSize(){
        return bufferList.size();
    }
}