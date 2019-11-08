public class User{
    private int userID;
    private int amountDone;
    private boolean isAlive;

    public User(int userID){
        this.userID = userID;
        isAlive = true;
    }

    public int getAmountDone(){
        return amountDone;
    }

    public void addOne(){
        amountDone++;
    }

    public int getID(){
        return userID;
    }

    public void setAlive(boolean isAlive){
        this.isAlive = isAlive;
    }

    public boolean getAlive(){
        return isAlive;
    }
}