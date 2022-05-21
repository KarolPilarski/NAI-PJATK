import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        double a=Double.parseDouble(args[0]);
        String trainSetPath=args[1];
        String testSetPath=args[2];

        LinkedList<Vector> data = new LinkedList<Vector>();

        LinkedList<Float> importance=new LinkedList<Float>();

        Double t=0.0;

        String defaultClass=null;
        String oppositeClass=null;



        BufferedReader trainReader;
        try {
            trainReader = new BufferedReader(new FileReader(trainSetPath));
            String line = trainReader.readLine();
            while (line != null) {
                LinkedList<Float> vector=new LinkedList<>();
                int i=0;
                while(line.split(";")[i].toCharArray()[0]>47&&line.split(";")[i].toCharArray()[0]<58){
                    vector.add(Float.parseFloat(line.split(";")[i]));
                    i++;
                }
                if(defaultClass==null)defaultClass=line.split(";")[i];
                else if(oppositeClass==null&&!defaultClass.equals(line.split(";")[i]))oppositeClass=line.split(";")[i];

                data.add(new Vector(vector,line.split(";")[i]));
                line = trainReader.readLine();
            }
            trainReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i=0;i<data.get(0).size();i++){
            importance.add((float)((Math.random()*10)-5));
        }

        t=learn(data,importance,defaultClass,oppositeClass,a,t);

        while(test(testSetPath,importance,defaultClass,oppositeClass,a,t)<90.0){
            System.out.println("Ponowna analiza zbioru treningowego.");
            t=learn(data,importance,defaultClass,oppositeClass,a,t);
        }

        while(true)newVector(importance,t,defaultClass,oppositeClass);
    }

    static double test(String testSetPath,LinkedList<Float> importance, String deafultClass, String oppositeClass, double a, double t){
        int deafultClassQuantity=0;
        int defaultClassGuessed=0;
        int oppositeClassQuantity=0;
        int oppositeClassGuessed=0;
        int allQuantity=0;
        int allGuessed=0;

        BufferedReader testReader;
        LinkedList<Float> vector=new LinkedList<>();
        try {
            testReader = new BufferedReader(new FileReader(testSetPath));
            String line = testReader.readLine();
            while (line != null) {
                vector=new LinkedList<>();
                int i=0;
                while(line.split(";")[i].toCharArray()[0]>47&&line.split(";")[i].toCharArray()[0]<58){
                    vector.add(Float.parseFloat(line.split(";")[i]));
                    i++;
                }

                int calculatedValue=calculate(vector,importance,t);

                //System.out.println("Oczekiwano: "+(line.split(";")[i])+"   Otrzymano: "+((calculatedValue==1)?deafultClass:oppositeClass));

                if((line.split(";")[i]).equals(deafultClass)){
                    deafultClassQuantity++;
                    if(calculatedValue==1){
                        defaultClassGuessed++;
                        allGuessed++;
                    }
                }else{
                    oppositeClassQuantity++;
                    if(calculatedValue==0){
                        oppositeClassGuessed++;
                        allGuessed++;
                    }
                }
                allQuantity++;

                line = testReader.readLine();
            }
            testReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Trafność dla "+deafultClass+" : "+((double)defaultClassGuessed/(double)deafultClassQuantity)*100+"%");
        System.out.println("Trafność dla "+oppositeClass+" : "+((double)oppositeClassGuessed/(double)oppositeClassQuantity)*100+"%");
        System.out.println("Ogólna trafność : "+((double)allGuessed/(double)allQuantity)*100+"%\n");

        return ((double)allGuessed/(double)allQuantity)*100;
    }

    static double learn(LinkedList<Vector> data, LinkedList<Float> importance, String deafultClass, String oppositeClass, double a, double t){
        Collections.shuffle(data);

        for(Vector vector:data){
            int expectedValue=((vector.getGroup().equals(deafultClass))?1:0);
            int calculatedValue=0;
            double result=0;
            for(int j=0;j<vector.size();j++){
                result+=(vector.vec.get(j)*importance.get(j));
            }
            if(result>=t){
                calculatedValue=1;
            }

            for(int i=0;i<importance.size();i++){
                importance.set(i,(float)((importance.get(i)+(expectedValue-calculatedValue)*a*vector.vec.get(i))));
            }
            t=(t-((expectedValue-calculatedValue)*a));
        }
        return t;
    }

    static int calculate(LinkedList<Float> vector,LinkedList<Float> importance,Double t) {
        int calculatedValue = 0;
        double result = 0;
        for (int j = 0; j < importance.size(); j++) {
            result += (vector.get(j) * importance.get(j));
        }
        if (result >= t) {
            calculatedValue = 1;
        }
        return calculatedValue;
    }

    static void newVector(LinkedList<Float> importance,Double t,String defaultClass,String oppositeClass){
        System.out.println("Podaj nowy wektor:");
        LinkedList<Float> vector=new LinkedList<>();
        for(int i=0;i<importance.size();i++) {
            System.out.println("Podaj wartość " + (i + 1));
            Scanner scan = new Scanner(System.in);
            Float value = scan.nextFloat();
            vector.add(value);
        }
        System.out.println("\n"+((calculate(vector,importance,t)==1)?defaultClass:oppositeClass)+"\n");
    }
}


class Vector{
    LinkedList<Float> vec;
    String group;

    public Vector(LinkedList<Float> vec, String group) {
        this.vec = vec;
        this.group = group;
    }

    int size(){
        return vec.size();
    }

    public String getGroup() {
        return group;
    }
}
