import java.util.ArrayList;

/**
 * Created by Soure on 31.05.2017.
 */
public class PAM {

    MatrixDistance matr;
    ArrayList<Depot> depots;

    PAM(MatrixDistance matr, ArrayList<Depot> depots){
        this.matr = matr;
        this.depots = depots;
    }

    public void pamCluster(){
        for (int i=0; i<matr.matrix[0].length; i++){
            for(int j=0; j<matr.matrix.length; j++) {
                matr.matrix[j][i].dist = matr.matrix[j][i].client.getPamDistance(matr,i,j);
            }
        }
        for (int i=0; i<matr.clients.size(); i++){
            for(int j=0; j<matr.matrix[0].length; j++){
                if (matr.matrix[0][j].client.id == matr.clients.get(i).id){
                    depots.get(findBestDepot(j)).cluster.add(matr.clients.get(j));
                }
            }
        }
    }

    private int findBestDepot(int j){
        double record=0.0;
        double temp=0.0;
        int numbDepot=-1;
        for(int k=0; k<depots.size(); k++){
            for(int i=0; i<matr.matrix.length; i++){
                if((depots.get(k).id==matr.matrix[i][j].point.id)&&(matr.matrix[i][j].point.isDepot)){
                    temp = matr.matrix[i][j].dist;
                    break;
                }
            }
            for(int l=0; l<depots.get(k).cluster.size(); l++){
                for(int i=0; i<matr.matrix.length; i++){
                    if ((depots.get(k).cluster.get(l).id==matr.matrix[i][j].point.id)&&(!matr.matrix[i][j].point.isDepot)) {
                        temp += matr.matrix[i][j].dist;
                        break;
                    }
                }
            }
            temp/=(depots.get(k).cluster.size()+1);
            temp = findMedoid(temp, j, k);
            if ((temp<record)||(record==0.0)){
                record=temp;
                numbDepot=k;
            }
        }
        return numbDepot;
    }

    private double findMedoid(double temp, int j, int k){
        double eps=-1.0;
        double medoid=-1.0;
        for(int i=0; i<matr.matrix.length; i++){
            if((depots.get(k).id==matr.matrix[i][j].point.id)&&(matr.matrix[i][j].point.isDepot)){
                eps=Math.abs(temp-matr.matrix[i][j].dist);
                medoid=matr.matrix[i][j].dist;
                break;
            }
        }
        for(int l=0; l<depots.get(k).cluster.size(); l++){
            for(int i=0; i<matr.matrix.length; i++){
                if ((depots.get(k).cluster.get(l).id==matr.matrix[i][j].point.id)&&(!matr.matrix[i][j].point.isDepot)) {
                    if(Math.abs(matr.matrix[i][j].dist-temp)<eps){
                        eps=Math.abs(matr.matrix[i][j].dist-temp);
                        medoid=matr.matrix[i][j].dist;
                    }
                    break;
                }
            }
        }
        return medoid;
    }

}
