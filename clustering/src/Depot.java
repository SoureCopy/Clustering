import java.util.ArrayList;
import java.util.Comparator;

public class Depot extends Point  {

    ArrayList<Client> cluster = new ArrayList<Client>(); //set of assigned clients

    Depot(int id, int startWindow, int endWindow){
        this.id = id;
        this.isDepot = true;
        this.startWindow=startWindow;
        this.endWindow=endWindow;
    }

    Depot(){
    }

    //new comparator for depots
   /* @Override
    public int compare(Depot d1, Depot d2){
        return Double.compare((double)d2.demands, (double)d1.demands);
    }*/
}
