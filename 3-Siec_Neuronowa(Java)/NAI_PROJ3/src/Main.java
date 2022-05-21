import javax.swing.*;
import java.io.*;
import java.util.Collections;
import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {

        LinkedList<NormalizedData> trainingData = new LinkedList<NormalizedData>();
        LinkedList<Perceptron> perceptrons = new LinkedList<Perceptron>();

        File maindir = new File("data");
        File dirs[] = maindir.listFiles();
        double alfa=0.8;

        for (File languageName : dirs) {
           perceptrons.add(new Perceptron(languageName.getName(),alfa));

            File files[] = languageName.listFiles();

            for(File file : files) {
                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String temp="";
                    String st=null;
                    while ((st = br.readLine()) != null)
                        temp+=st;
                    trainingData.add(new NormalizedData(temp,languageName.getName()));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        for(int i=0;i<10000;i++) {
            Collections.shuffle(trainingData);

            for (NormalizedData normalizedData : trainingData) {
                for (Perceptron perceptron : perceptrons) {
                    perceptron.learn(normalizedData);
                }
            }
        }

        Perceptron.normalizeImportance(perceptrons);

        //System.out.println(Perceptron.calculateBest(perceptrons,new NormalizedData("Grzegorz brzeczyszykiewicz",null)));

        SwingUtilities.invokeLater(()->{
            new MainFrame(perceptrons);
        });
    }
}
