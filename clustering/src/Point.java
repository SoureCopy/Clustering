/**
 * Created by Soure on 31.05.2017.
 */
abstract public class Point {

    int id;
    boolean isDepot;
    int startWindow; //in minutes, 0=00:00, 1439=23:59
    int endWindow;

    @Override
    public String toString(){
        return "(id#"+id+"; Depot:"+isDepot+")";
    }

}
