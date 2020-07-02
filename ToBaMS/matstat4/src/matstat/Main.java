package matstat;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    static double getAvr(ArrayList<Double> data) {
        return data.stream().mapToDouble(z -> z).sum() / data.size();
    }

    static double get_disperssion(ArrayList<Double> data, double avr) {
        return Math.sqrt(data.stream().mapToDouble(z -> Math.pow(z - avr, 2)).sum() / (data.size() - 1));
    }

    public static void partA(ArrayList<Double> data_x, ArrayList<Double> data_y) {

        ArrayList<Double> d = new ArrayList<>();

        for (int i = 0; i < data_x.size(); i++) {
            d.add(data_x.get(i) - data_y.get(i));
        }

        double avr_d = d.stream().mapToDouble(z -> z).sum() / d.size();
        double sigma_s_kryschechkoi = get_disperssion(d, avr_d);

        System.out.println("average d=" + avr_d);
        System.out.println("Скорректированная дисперсия:" + sigma_s_kryschechkoi);

        //t-statistics
        //A2
        int x_size = data_x.size();
        int y_size = data_y.size();

        double x_avr = getAvr(data_x);
        double y_avr = getAvr(data_y);

        double x_d = data_x.stream().mapToDouble(z -> Math.pow(z - x_avr, 2)).sum();
        double y_d = data_y.stream().mapToDouble(z -> Math.pow(z - y_avr, 2)).sum();

        double sigma = Math.sqrt((x_d + y_d) / (x_size + y_size - 2));

        double sqrt = Math.sqrt((x_size + y_size) * 1.0 / (x_size * y_size));

        double t_stat = (x_avr - y_avr) / (sigma * sqrt);
        System.out.println(" " + t_stat);

        //A3 t(126, 0,05)=1,9789706
        //http://old.exponenta.ru/educat/referat/XIkonkurs/student5/tabt-st.pdf

        //A4
        System.out.println("t statistics:" + t_stat + " " + ((Math.abs(t_stat) > 1.9789706) ? "rejected (module is more than 1,9789706)" : "accepted (is less or equals to 1,9789706)"));
    }


    public static void main(String[] args) {
        String x = "0,0004086\t-0,000574\t-0,0007217\t0,0012408\t0,0002007\t0,0017155\t0,0006044\t0,0016047\t0,0015286\t0,0007868\t0,0001381\t0,0011805\t0,000518\t0,0020961\t0,0003439\t-0,000656\t0,0013404\t0,000409\t0,0002933\t0,0008552\t0,002179\t0,0016658\t0,0000762\t-0,0000251\t-0,0003999\t-0,0008344\t0,0031851\t0,0002456\t0,0007316\t0,0000937\t0,0003158\t0,0009268\t0,0024746\t0,0019535\t0,0013906\t-0,0010556\t0,0015383\t0,0004326\t0,0006653\t0,0013492\t0,0002549\t0,0001369\t0,0014441\t0,0013478\t0,000138\t0,0001407\t0,0003199\t-0,0003453\t0,0006113\t-0,00029\t0,0009523\t0,0015136\t-0,0014778\t0,0007741\t0,0007308\t0,0016039\t0,0022365\t-0,0008889\t0,0023363\t-0,0007337\t0,0014734\t-0,0002044\t-0,0004228\t0,000356\n";
        String y = "0,0013324\t-0,0016763\t0,0030333\t0,0005248\t0,0021415\t0,0006712\t0,0018863\t0,0024842\t0,001964\t0,0004396\t0,0015009\t0,0000958\t0,000091\t0,0020582\t0,0011396\t0,0007923\t0,0007823\t0,0005934\t0,000561\t0,001054\t0,0020426\t-0,0006337\t0,0026646\t0,0015331\t0,0028891\t0,0034444\t0,0032585\t-0,0002147\t0,0003833\t0,0010444\t0,0007967\t0,0007514\t0,0035265\t0,0005562\t0,0008686\t0,0029362\t0,000675\t0,0019311\t0,0000844\t0,0013705\t0,0009659\t0,0000111\t0,0005648\t-0,0002123\t0,001033\t0,0006271\t-0,0001848\t0,0021777\t0,0004286\t0,000425\t0,0014037\t0,0016781\t0,0000443\t0,0006911\t0,0007701\t0,0008956\t-0,0005799\t0,0015734\t-0,0006948\t0,0006016\t0,0022734\t0,0021693\t0,0026041\t0,0008749\n";
        x = x.replace(',', '.');
        y = y.replace(',', '.');

        ArrayList<Double> data_y = new ArrayList<>();
        ArrayList<Double> data_x = new ArrayList<>();

        Arrays.stream(x.split("\t")).mapToDouble(Double::parseDouble).forEach(data_x::add);
        Arrays.stream(y.split("\t")).mapToDouble(Double::parseDouble).forEach(data_y::add);

        System.out.println("~ ~ ~ A ~ ~ ~");
        partA(data_x, data_y);
        System.out.println("~ ~ ~ B ~ ~ ~");
        partB(data_x, data_y);
    }

    public static void partB(ArrayList<Double> data_x, ArrayList<Double> data_y) {

        //B1
        double x_avr = getAvr(data_x);
        double y_avr = getAvr(data_y);

        double up = getUp(data_x, data_y, x_avr, y_avr);
        double down = getDown(data_x, data_y, x_avr, y_avr);

        double corr = up / down;
        System.out.println("Corr= " + corr);

        //B2
        double t_stat = corr * Math.sqrt(data_x.size() + data_y.size() - 2) / Math.sqrt(1 - Math.pow(corr, 2));
        System.out.println("t statistics: " + t_stat);

        //B3 t(126, 0,05)=1,9789706

        //B4
        System.out.println("t statistics:" + t_stat + " " + ((Math.abs(t_stat) > 1.9789706) ? "rejected (module is more than 1,9789706)" : "accepted (is less or equals to 1,9789706)"));
    }

    static double getUp(ArrayList<Double> data_x, ArrayList<Double> data_y, double x_avr, double y_avr) {
        double res = 0;

        for (int i = 0; i < data_x.size(); i++) {
            res += (data_x.get(i) - x_avr) * (data_y.get(i) - y_avr);
        }

        return res;
    }

    static double getDown(ArrayList<Double> data_x, ArrayList<Double> data_y, double x_avr, double y_avr) {
        double x_d = data_x.stream().mapToDouble(z -> Math.pow(z - x_avr, 2)).sum();
        double y_d = data_y.stream().mapToDouble(z -> Math.pow(z - y_avr, 2)).sum();

        return Math.sqrt(x_d * y_d);
    }
}
