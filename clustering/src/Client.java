/**
 * Created by Soure on 31.05.2017.
 */
public class Client extends Point {

    boolean inRoute;

    Client(int id, int startWindow, int endWindow){
        this.id=id;
        this.isDepot=false;
        this.startWindow=startWindow;
        this.endWindow=endWindow;
    }

    Client(){
    }

    double getPamDistance(MatrixDistance matr, int myPoint, int posPoint ){
        double pam_dist;
        pam_dist=0.5*Math.sqrt(Math.pow(matr.matrix[posPoint][myPoint].dist,2));
        pam_dist+=0.5*Math.abs((endWindow-startWindow-matr.matrix[posPoint][myPoint].point.endWindow+matr.matrix[posPoint][myPoint].point.startWindow)/2);
        return pam_dist;
    }
    @Override
    public String toString(){
        return "(id#"+id+"; Client)";
    }
    //new comparator for clients
  /*  @Override
    public int compare(Client c1, Client c2){
        return Double.compare(c2.prior, c1.prior);
    }
*/
}
