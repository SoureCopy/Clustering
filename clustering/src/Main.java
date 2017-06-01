/**
 * Created by Soure on 31.05.2017.
 */
public class Main {

    public static void main(String[] args){
        long start = System.currentTimeMillis();
        Logic l = new Logic();
        l.test();
        if ((l.amountClient<=100)||(l.amountClient*l.amountDepot>=500)) {
            ThreeCriteria t = new ThreeCriteria(l.matr, l.depots);
            t.criteriaCluster();
        } else {
            PAM pam = new PAM(l.matr, l.depots);
            pam.pamCluster();
        }
        l.showCluster();
        long finish = System.currentTimeMillis();
        System.out.println("Time working: "+(double)(finish-start)/1000);
        ClarkeWright cl = new ClarkeWright(l, l.matr, l.depots.get(0));
        cl.findSavings();
        for(int i=0; i<cl.sav.length; i++){
            for(int j=0; j<cl.sav[0].length; j++){
                System.out.print(cl.sav[i][j].dist+" ");
            }
            System.out.println();
        }
        cl.getRoute();
        for(int i=0; i<cl.routes.size(); i++){
            System.out.println("Route #"+(i+1)+": "+cl.routes.get(i));
        }
    }


}
