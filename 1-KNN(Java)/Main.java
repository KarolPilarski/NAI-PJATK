import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int k=Integer.parseInt(args[0]);
        String trainSetPath=args[1];
        String testSetPath=args[2];

        HashMap<String,LinkedList<LinkedList<Float>>> data = new HashMap<String,LinkedList<LinkedList<Float>>>();


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
                if (data.get(line.split(";")[i])==null)
                    data.put(line.split(";")[i],new LinkedList<LinkedList<Float>>());

                data.get(line.split(";")[i]).add(vector);
                line = trainReader.readLine();
            }
            trainReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int guessedCount=0;
        int allCount=0;
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

                System.out.println(line.split(";")[i]+" "+calculate(vector,data,k));

                if(line.split(";")[i].equals(calculate(vector,data,k))) guessedCount++;
                allCount++;

                line = testReader.readLine();
            }
            testReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Dokładność:" + (int)((guessedCount/allCount)*100)+"%\n");

        int vectorSize=vector.size();
        vector=new LinkedList<>();

        while(true){
            for(int i=0;i<vectorSize;i++) {
                System.out.println("Podaj wartość " + (i + 1));
                Scanner scan = new Scanner(System.in);
                Float value = scan.nextFloat();
                vector.add(value);
            }
            System.out.println("\n"+calculate(vector,data,k)+"\n");
        }

    }


    public static String calculate(LinkedList<Float> vector,HashMap<String,LinkedList<LinkedList<Float>>> data,int k){
        LinkedList<VectorDistance> distances = new LinkedList<>();
        LinkedList<GroupQuantity> quantities = new LinkedList<>();


        for(String group:data.keySet()){
            quantities.add(new GroupQuantity(group));
            for(LinkedList<Float> vec: data.get(group)){
                double result=0;
                for(int j=0;j<vec.size();j++){
                    result+=Math.pow((vector.get(j)-vec.get(j)),2);
                }
                distances.add(new VectorDistance(Math.sqrt(result),group));
            }
        }
        distances.sort(new Comparator<VectorDistance>() {
            @Override
            public int compare(VectorDistance o1, VectorDistance o2) {
                return (int)(o1.distance*100-o2.distance*100);
            }
        });
        for(int j=0;j<k;j++){
            for(GroupQuantity gq: quantities){
                if(gq.groupName.equals(distances.get(j).group)){
                    gq.count++;
                }
            }
        }
        quantities.sort(new Comparator<GroupQuantity>() {
            @Override
            public int compare(GroupQuantity o1, GroupQuantity o2) {
                return (int)(o2.count-o1.count);
            }
        });
        return quantities.get(0).groupName;
    }
}
class GroupQuantity {
    String groupName;
    int count;

    public GroupQuantity(String groupName) {
        this.groupName = groupName;
    }
}

class VectorDistance {
    Double distance;
    String group;

    public VectorDistance(Double distance, String group) {
        this.distance = distance;
        this.group = group;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}

