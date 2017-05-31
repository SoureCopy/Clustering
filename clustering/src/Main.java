/**
 * Created by Soure on 31.05.2017.
 */
public class Main {

    public static void main(String[] args){
        Logic l = new Logic();
        l.test();
        if ((l.amountClient>=100)||(l.amountClient*l.amountDepot>=500)) {
            ThreeCriteria t = new ThreeCriteria(l.matr, l.depots);
            t.CriteriaCluster();
        } else {
            PAM pam = new PAM(l.matr, l.depots);
            pam.pamCluster();
        }
        l.showCluster();
    }


}
