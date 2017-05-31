import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;

/**
 * Created by Soure on 31.05.2017.
 */
public class ThreeCriteria {

    MatrixDistance matr;
    ArrayList<Depot> depots;

    ThreeCriteria(MatrixDistance matr, ArrayList<Depot> depots){
        this.matr = matr;
        this.depots = depots;
    }

    public void CriteriaCluster() {
        double record, temp, min;
        boolean eff;
        int numbDepot = -1, min_id = -1;
        for (int j = 0; j < matr.matrix[0].length; j++) {
            record = 0.0;
            temp = 0.0;
            min = 0.0;
            eff = false;
            for (int k = 0; k < depots.size(); k++) {
                for (int i = 0; i < matr.matrix.length; i++) {
                    if ((depots.get(k).id == matr.matrix[i][j].point.id) && (matr.matrix[i][j].point.isDepot)) {
                        temp = matr.matrix[i][j].dist;
                        if ((min == 0.0)||(min>temp)) {
                            min = matr.matrix[i][j].dist;
                            min_id = k;
                        }
                        break;
                    }
                }
                for (int l = 0; l < depots.get(k).cluster.size(); l++) {
                    for (int i = 0; i < matr.matrix.length; i++) {
                        if ((depots.get(k).cluster.get(l).id == matr.matrix[i][j].point.id) && (!matr.matrix[i][j].point.isDepot)) {
                            temp += matr.matrix[i][j].dist;
                            if (min > matr.matrix[i][j].dist){
                                min = matr.matrix[i][j].dist;
                                min_id = k;
                            }
                            break;
                        }
                    }
                }
                temp /= (depots.get(k).cluster.size() + 1);
                if (record == 0.0) {
                    record = temp;
                } else if (record > temp) {
                    if (record/temp > 1.1) {
                        eff = true;
                    } else {
                        eff = false;
                    }
                    record = temp;
                    numbDepot = k;
                }
            }
            if (eff) {
               depots.get(numbDepot).cluster.add(matr.clients.get(j));
            } else {
               depots.get(min_id).cluster.add(matr.clients.get(j));
            }
        }
    }


}
