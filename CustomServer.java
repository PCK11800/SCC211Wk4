import java.util.*;

public class CustomServer{

    Buffer buffer;

    private ArrayList<User> userList = new ArrayList<>();
    private ArrayList<Server> serverList = new ArrayList<>();

    private int bufferNum, userNum, serverNum, elementNum;

    private long startTime, endTime;

    public CustomServer(int bufferNum, int userNum, int serverNum, int elementNum){
        this.bufferNum = bufferNum;
        this.userNum = userNum;
        this.serverNum = serverNum;
        this.elementNum = elementNum;

        buffer = new Buffer(this.bufferNum);
        generateUsers();
        generateServers();
    }

    public void generateUsers(){
        for(int i = 1; i < userNum + 1; i++){
            userList.add(new User(i));
        }
    }

    public void generateServers(){
        for(int i = 1; i < serverNum + 1; i++){
            serverList.add(new Server(i));
        }
    }

    public void activateUsers(){
        for(int i = 0; i < userList.size(); i++){
            User thisUser = userList.get(i);
            Thread userThread = new Thread(new Runnable(){
                public void run(){
                    while(thisUser.getAmountDone() < elementNum/userNum){
                        try{
                            buffer.synchronizedAddToBuffer(thisUser);
                        }catch(InterruptedException ie){}
                    }
                    thisUser.setAlive(false);
                    endTime = System.currentTimeMillis();
                    return;
                }
            });
            userThread.start();
        }
    }

    public void activateServers(){
        for(int i = 0; i < serverList.size(); i++){
            Server thisServer = serverList.get(i);
            Thread serverThread = new Thread(new Runnable(){
                public void run(){
                    while(thisServer.getAmountDone() < elementNum/serverNum){
                        try{
                            buffer.synchronizedRemoveFromBuffer(thisServer);
                        }catch(InterruptedException ie){}
                    }
                    thisServer.setAlive(false);
                    endTime = System.currentTimeMillis();
                    return;
                }
            });
            serverThread.start();
        }
    }

    public void exec(){
        startTime = System.currentTimeMillis();

        Thread t1 = new Thread(new Runnable(){
            public void run(){
                activateUsers();
            }
        });

        Thread t2 = new Thread(new Runnable(){
            public void run(){
                activateServers();
            }
        });

        try{
            t1.join();
            t2.join();
        }catch(InterruptedException ie){}
        
        t1.start();
        t2.start();
        
        try{
            Thread.sleep(1000);
        }catch(InterruptedException ie){}

        printResult(endTime - startTime);
    }

    public void printResult(long milli){

        System.out.println("======================================================");
        System.out.println("======================================================");

        for(int i = 0; i < userList.size(); i++){
            User thisUser = userList.get(i);
            System.out.println("User " + thisUser.getID() + " created a total of " + thisUser.getAmountDone() + " elements");
        }

        for(int i = 0; i < serverList.size(); i++){
            Server thisServer = serverList.get(i);
            System.out.println("Server " + thisServer.getID() + " consumed a total of " + thisServer.getAmountDone() + " elements");
        }

        System.out.println("======================================================");
        System.out.println("Buffer has " + buffer.getSize() + " elements remaining");
        System.out.println("======================================================");

        for(int i = 0; i < userList.size(); i++){
            User thisUser = userList.get(i);
            System.out.println("User thread " + thisUser.getID() + " is alive: " + thisUser.getAlive());
        }

        for(int i = 0; i < serverList.size(); i++){
            Server thisServer = serverList.get(i);
            System.out.println("Server thread " + thisServer.getID() + " is alive: " + thisServer.getAlive());
        }

        System.out.println("======================================================");
        System.out.println("Program took " + milli + " milliseconds to run.");   
    }
}