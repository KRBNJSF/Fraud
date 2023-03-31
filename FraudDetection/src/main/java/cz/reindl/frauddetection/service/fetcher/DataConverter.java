package cz.reindl.frauddetection.service.fetcher;

import cz.reindl.frauddetection.Application;
import cz.reindl.frauddetection.controller.Controller;
import cz.reindl.frauddetection.service.conf.ApplicationConfiguration;
import cz.reindl.frauddetection.service.conf.Settings;
import cz.reindl.frauddetection.utils.Utils;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.*;
import java.util.*;

public class DataConverter {

    Controller controller;
    Application application;

    static UserData userData;
    List<Integer> firstNums;

    public DataConverter(Controller controller, Application application) {
        this.controller = controller;
        this.application = application;
    }

    static ApplicationConfiguration config = new ApplicationConfiguration();

    public List<Integer> getFirst() throws IOException {

        Map<String, Double> user = new HashMap<>();
        user.put(userData.getFrom(), userData.getValue());
        String c = String.valueOf(user.get(userData.getValue()));
        char first = c.charAt(0);
        int firstNum = first;
        //System.out.println(user.get(userData.getValue()[0]));


        firstNums = new ArrayList<>();
        firstNums.add(firstNum);
        String line;
        //String[] row = line.split(",");
        //double count = Double.parseDouble(row[0]);
        //int firstNum = (int) (count / Math.pow(10, Math.floor(Math.log10(count))));
        //firstNums.add(firstNum);
        return firstNums;
    }

    public static Map<UserData, Double> convertData(String path) throws IOException {
        //BufferedReader reader = new BufferedReader(new FileReader(path));
        //String line;
        //int i = 0;

        HashMap<UserData, Double> map = new HashMap();
        Scanner sc = new Scanner(new File(path));

        sc.nextLine();
        while (sc.hasNextLine()) {
            String[] regexer = sc.nextLine().split(";");
            userData = new UserData(regexer[1]);
            userData.setTo(regexer[2]);
            userData.setValue(Double.parseDouble(regexer[3]));
            map.put(userData, Double.parseDouble(regexer[3]));
            //i++;
            System.out.println(userData.toString());
            //System.out.println(userData.getFrom());
        }

        //System.out.println(user.get(userData.getValue()[0]));


//        while ((line = reader.readLine()) != null) {
//            reader.lines().skip(1);
//            if (line.equals("value") || line.equals("transation_id") || line.equals("from") || line.equals("to") || line.equals("data")) {
//                continue;
//            }
//            i++;
//            String[] row = line.split(";");
//            //System.out.println(list);
//            //System.out.println(row[i]);
//            //System.out.println(line);
//
//        }
        /*String regex = "[0-9]+";
        HashMap<String, List<Double>> userData = new HashMap<>();
        List<Integer> data = new LinkedList<>();
        String line;
        BufferedReader reader = new BufferedReader(new FileReader(path));
        while ((line = reader.readLine()) != null) {
            if (line.equals("value")) {
                continue;
            }
            String[] row = line.split(",");
            for (int i = 1; i < row.length; i++) {
                String[] mapper = row[i].split(" ");
                if (mapper[i].matches(regex)) {
                    data.add(Integer.parseInt(mapper[i]));
                    userData.put(mapper[0], data);
                }
            }
        }
        return userData;*/
        return map;
    }

    public static void generateData(String path) {
        System.out.println("Number of transactions: " + ApplicationConfiguration.getProperty("transactions"));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write("Transaction\n");
            for (int i = 0; i < Settings.TRANSACTIONS; i++) {
                double amount = Settings.MIN_AMOUNT + (Settings.MAX_AMOUNT - Settings.MIN_AMOUNT) * Utils.randomNum();
                writer.write(String.format("%.2f\n", amount));
            }
            writer.close();
            String line;
            BufferedReader reader = new BufferedReader(new FileReader(path));
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {

        }
    }

    public void checkFrequency() {
        int[] counts = new int[9];
        for (int i = 0; i < firstNums.size(); i++) {
            int digit = firstNums.get(i);
            if (digit != 0) {
                counts[digit - 1]++;
            }
        }

        double[] expectedNums = new double[9];
        for (int i = 0; i < 9; i++) {
            expectedNums[i] = Math.log10(1 + 1.0 / (i + 1)) * firstNums.size();
        }

        double[] diff = new double[9];
        for (int i = 0; i < 9; i++) {
            diff[i] = 100.0 * Math.abs(counts[i] - expectedNums[i]) / expectedNums[i];
        }
    }

}
