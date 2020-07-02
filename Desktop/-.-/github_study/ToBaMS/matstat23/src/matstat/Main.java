package matstat;

import java.util.*;

public class Main {

    static Random rnd = new Random();

    static double getAvr(ArrayList<Double> election) {
        return election.stream().mapToDouble(x -> x).sum() / election.size() * 1.0;
    }

    static void getIntervals(ArrayList<Double> data, double q) {

        double average = getAvr(data);

        System.out.println("avr= " + average);

        double x = data.stream().mapToDouble(y -> Math.pow(y - average, 2)).sum();

        double d = Math.sqrt(x / data.size());

        System.out.println("disp= " + d);

        double interval1 = average - (d * q) / Math.sqrt(data.size());
        double interval2 = average + (d * q) / Math.sqrt(data.size());
        System.out.println(interval1 + " < tetta < " + interval2 + "\n");
    }

    static void task1() {
        int[] data_a = new int[]{20, 19, 4, 16, 31, 13, 3, 15, 9, 14, 19, 8, 15, 21, 11, 23, 20};
        ArrayList<Double> data = new ArrayList<>();

        for (var item : data_a
        ) {
            data.add(item * 1.0);
        }

        ////////A
        System.out.println("~ ~ ~ A ~ ~ ~");
        getIntervals(data, 1.745884);
        /////////end of A


        ////C
        System.out.println("~ ~ ~ C ~ ~ ~");
        ArrayList<Double> re_el_avrs = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            ArrayList<Double> re_elections = new ArrayList<>();
            for (int j = 0; j < data.size(); j++) {
                re_elections.add(data.get(rnd.nextInt(data.size())) * 1.0);
            }
            re_el_avrs.add(getAvr(re_elections));
        }
        getIntervals(re_el_avrs, 1.6449);
        //// End of C
    }

    public static void main(String[] args) {
        task1();
        task2();
    }

    //Вариант 8. Степенной остаточный №2, z1 = 7724
    //Назначаем начальное число z1 < 10000. Последующие числа получаем из соотношения
    //zi=((z(i−1)+17)^(2.2) div 100) mod 10000
    //div — целая часть от деления, mod — остаток от деления.
    //Полученная последовательность укладывается в пределы от 0 до 1 так: xi=zi/10000
    static void task2() {
        System.out.println("~ ~ ~ TASK 2 ~ ~ ~");

        //1. Рассчитайте 100 псевдослучайных чисел методом, соответствующим вашему варианту.
        ArrayList<Double> rands = getRands(100);

        //2. Приведите первые 10 чисел этой последовательности.
        System.out.println("~ ~ ~ 2 ~ ~ ~");
        for (int i = 0; i < 10; i++) {
            System.out.print(rands.get(i) + " ");
        }

        //3. Постройте гистограмму с 10 столбцами для полученной последовательности.
        System.out.println("\n~ ~ ~ 3 ~ ~ ~");
        rands.sort(Double::compareTo);
        ArrayList<Integer> frequencies = getFrequenciesAndIntervals(rands);

        //4. Проверьте гипотезу о том, что последовательность имеет распределение R(0, 1) критерием
        //хи-квадрат, разбив интервал [0; 1) на десять равных интервалов
        System.out.println("~ ~ ~ 4 ~ ~ ~");
        checkHypothesis(frequencies, 100);

        //5. Повторите шаги 3 и 4 для последовательности длиной в 10000 чисел.
        System.out.println("~ ~ ~ 5 ~ ~ ~");
        rands = getRands(10000);
        rands.sort(Double::compareTo);
        frequencies = getFrequenciesAndIntervals(rands);
        checkHypothesis(frequencies, 10000);

        //experiment. Повторите шаги 3 и 4 для последовательности длиной в xxx чисел.
        System.out.println("~ ~ ~ experiment ~ ~ ~");
        rands = getRands(10);
        rands.sort(Double::compareTo);
        frequencies = getFrequenciesAndIntervals(rands);
        checkHypothesis(frequencies, 10);
    }

    static ArrayList<Double> getRands(int numb) {
        ArrayList<Double> rands = new ArrayList<>();
        double z = 7724;
        rands.add(z / 10000);
        //rands.add(rnd.nextDouble());
        for (int i = 1; i < numb; i++) {
            z = ((int) (Math.pow(z + 17, 2.2) / 100)) % 10000;
            rands.add(z / 10000);
            //rands.add(rnd.nextDouble());
        }

        return rands;
    }

    static ArrayList<Integer> getFrequenciesAndIntervals(ArrayList<Double> rands) {
        ArrayList<Integer> frequencies = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            double a1 = i * 1.0 / 10;
            double a2 = (i + 1) * 1.0 / 10;
            frequencies.add((int) rands.stream().filter(x -> x >= a1 && x < a2).count());
            System.out.println(a1 + " - " + a2 + " : " + frequencies.get(i));
        }

        return frequencies;
    }

    static void checkHypothesis(ArrayList<Integer> frequencies, int n) {
        ArrayList<Integer> expected_freqs = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            expected_freqs.add(n / 10);
        }

        double hi = 0;

        for (int i = 0; i < 10; i++) {
            hi += Math.pow(frequencies.get(i) - expected_freqs.get(i), 2) / expected_freqs.get(i);
        }

        //hi^2(9;0,05)=3,32
        System.out.println("hi= " + hi + " " + ((hi > 3.32) ? "rejected (is more than 3,32)" : "accepted (is less equals to 3,32)"));
    }
}
