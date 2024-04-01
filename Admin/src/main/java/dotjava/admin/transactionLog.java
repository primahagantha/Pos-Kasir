package dotjava.admin;

public class transactionLog {
    private int idTransaction;
    private int idAct;
    private int idItemSold;
    private int cash;
    private int total;

    private int idUser;
    private String time;

    private String user;

    private String date;

    private String infoTransaction;

    private  String item;


    public int getIdTransaction() {
        return idTransaction;
    }

    // Setter for idTransaction
    public void setIdTransaction(int idTransaction) {
        this.idTransaction = idTransaction;
    }




    public void setIdAct(int idAct) {
        this.idAct = idAct;
    }

    // Getter for idItemSold
    public int getIdItemSold() {
        return idItemSold;
    }

    // Setter for idItemSold
    public void setIdItemSold(int idItemSold) {
        this.idItemSold = idItemSold;
    }

    // Getter for cash
    public double getCash() {
        return cash;
    }

    // Setter for cash
    public void setCash(int cash) {
        this.cash = cash;
    }

    // Getter for total
    public double getTotal() {
        return total;
    }

    // Setter for total
    public void setTotal(int total) {
        this.total = total;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getInfoTransaction() {
        return infoTransaction;
    }

    public void setInfoTransaction(String infoTransaction) {
        this.infoTransaction = infoTransaction;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getIdAct() {
        return idAct;
    }
}




