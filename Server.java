public class Server{
    private int serverID;
    private int amountDone;
    private boolean isAlive;
    
    public Server(int serverID){
        this.serverID = serverID;
        isAlive = true;
    }

    public int getAmountDone(){
        return amountDone;
    }

    public void addOne(){
        amountDone++;
    }

    public int getID(){
        return serverID;
    }

    public void setAlive(boolean isAlive){
        this.isAlive = isAlive;
    }

    public boolean getAlive(){
        return isAlive;
    }
}