import java.util.ArrayList;

/**
 * Created by Soure on 01.06.2017.
 */
public class ClarkeWright {

    final double SPEED = 1.0;
    Depot depot;
    MatrixDistance matr;
    Logic logic = new Logic();
    ArrayList<ArrayList<Point>> routes = new ArrayList<ArrayList<Point>>();
    ObjectMatrix[][] sav;

    ClarkeWright(Logic logic, MatrixDistance matr, Depot depot){
        this.matr = matr;
        this.depot = depot;
        this.logic = logic;
    }

    public void findSavings() {
        sav = new ObjectMatrix[depot.cluster.size() + 1][depot.cluster.size()]; //here we have got savings
        for (int i = 0; i < depot.cluster.size(); i++) {
            for (int j = 0; j < matr.matrix.length; j++) {
                for (int k = 0; k < matr.matrix[0].length; k++) {
                    if ((depot.id == matr.matrix[j][0].point.id) && (matr.matrix[j][0].point.isDepot)
                            && (matr.matrix[j][k].client.id == depot.cluster.get(i).id)) {
                        sav[0][i] = new ObjectMatrix(depot, depot.cluster.get(i), matr.matrix[j][k].dist);
                    }
                }
            }
        }

        for (int i = 1; i < depot.cluster.size() + 1; i++) {
            for (int j = 0; j < depot.cluster.size(); j++) {
                double dist = 0.0;
                for (int k = 0; k < matr.matrix.length; k++) {
                    for (int l = 0; l < matr.matrix[0].length; l++) {
                        for (int m = 0; m < matr.matrix.length; m++) {
                            if ((depot.cluster.get(i - 1).id == matr.matrix[k][l].point.id) && (!matr.matrix[k][l].point.isDepot)
                                    && (depot.cluster.get(j).id == matr.matrix[k][l].client.id) && (depot.id == matr.matrix[m][k - logic.amountDepot].point.id)
                                    && (matr.matrix[m][0].point.isDepot)) {
                                dist = matr.matrix[m][k - logic.amountDepot].dist + matr.matrix[m][l].dist - matr.matrix[k][l].dist;
                            }
                            if ((depot.cluster.get(j).startWindow - depot.cluster.get(i-1).endWindow - matr.matrix[k][l].dist * SPEED > 60.0)
                                    || (j == i - 1)) {
                                         dist = -1.0;
                            }
                        }
                    }
                    sav[i][j] = new ObjectMatrix(depot.cluster.get(i - 1), depot.cluster.get(j), dist);
                }
            }
        }
    }

    public int findStartRoute(){
            int maxTimeDif=1440;
        int numbWinner = -1; //номер клиента с самым ранним стартом

        for(int i=0; i<depot.cluster.size(); i++){
            if ((maxTimeDif>depot.cluster.get(i).startWindow-depot.startWindow)&&(!depot.cluster.get(i).inRoute)){
                maxTimeDif = depot.cluster.get(i).startWindow-depot.startWindow;
                numbWinner = i;
            }
        }

        double minDist = sav[0][numbWinner].dist;

        for(int i=0; i<depot.cluster.size(); i++){
            if(((minDist>sav[0][i].dist)&&(depot.cluster.get(i).startWindow-depot.cluster.get(numbWinner).startWindow<=30)
                    ||(depot.cluster.get(i).startWindow-depot.startWindow<=30))&&(!depot.cluster.get(i).inRoute)){
                numbWinner = i;
            }
        }
        return numbWinner;
    }

    public ArrayList<Point> findRoute(ArrayList<Point> route, int startRoute){
        int nextInd;
        route.add(depot.cluster.get(startRoute));
        depot.cluster.get(startRoute).inRoute=true;

        for(int i=0; i<sav.length; i++){
            if ((depot.cluster.get(startRoute).id==sav[i][0].point.id)&&(!sav[i][0].point.isDepot)) {
                double maxDist = sav[i][i-1].dist;
                nextInd = i-1;
                for (int j = 0; j < sav[i].length; j++) {
                    if (maxDist < sav[i][j].dist) {
                        for (int k = 0; k < depot.cluster.size(); k++) {
                            if ((depot.cluster.get(k).id == sav[i][j].client.id) && (!depot.cluster.get(k).inRoute)) {
                                maxDist = sav[i][j].dist;
                                nextInd = j;
                            }
                        }
                    }
                }
                if (nextInd == i-1) {
                    route.add(depot);
                } else {
                    route = findRoute(route, nextInd);
                }
                break;
            }
        }
        return route;
    }

    public void getRoute(){
        boolean flag=false;
        while (!flag){
            if(depot.cluster.size()==0){
                flag=true;
                continue;
            }
            ArrayList<Point> route = new ArrayList<Point>();
            route.add(depot);
            route = findRoute(route, findStartRoute());
            routes.add(route);
            int check = 0;

            for(int i=0; i<depot.cluster.size(); i++){
                if(depot.cluster.get(i).inRoute){
                    check++;
                }
                if (check==depot.cluster.size()){
                    flag = true;
                }
            }
        }
    }
}