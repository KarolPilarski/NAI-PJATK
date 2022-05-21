import java.util.LinkedList;

public class Perceptron {
    double[] importance;
    String name;
    double t,a;

    public Perceptron(String name,double a){
        importance = new double[26];
        for(int i=0;i<26;i++){
            importance[i] =((Math.random()*2)-1);
        }
        this.name = name;
        t=0.0;
        this.a=a;
    }


    public void learn(NormalizedData normalizedData){
        int expectedValue=((normalizedData.getLanguageName().equals(name))?1:0);
        int calculatedValue=0;
        double sum=0.0;
        for(int i=0;i<26;i++){
            sum+=importance[i]*(normalizedData.getShareData()[i]);
        }

        if(sum>=t){
            calculatedValue=1;
        }

        for(int i=0;i<26;i++){
            importance[i]+=(expectedValue-calculatedValue)*a*normalizedData.getShareData()[i];
        }
        t=(t-((expectedValue-calculatedValue)*a));
    }


    public double calculate(NormalizedData normalizedData){
        double sum=0.0;
        for(int i=0;i<26;i++){
            sum+=importance[i]*(normalizedData.getShareData()[i]);
        }
        double result=Math.pow(Math.exp(-sum),-1);
        return result;
    }

    public static String calculateBest(LinkedList<Perceptron> perceptrons,NormalizedData normalizedData){
        String bestName="Wpisz tekst";
        double bestValue=-100;

        for(Perceptron p : perceptrons){
            if(p.calculate(normalizedData)>bestValue){
                bestValue=p.calculate(normalizedData);
                bestName=p.name;
            }
        }
        return bestName;
    }


    public static String showResults(LinkedList<Perceptron> perceptrons,NormalizedData normalizedData){
        String best=Perceptron.calculateBest(perceptrons,normalizedData);
        String result="<html><center><p style=\"color:#3bff58\">"+best+"</p><br><br><br>";
        for(Perceptron p:perceptrons){
            if(!p.name.equals(best)){
                Double tmp = p.calculate(normalizedData);
                if(tmp<=0.80){
                    result=result+"<p style=\"color:#991400\">"+p.name+"</p><br>";
                }else if(tmp<=0.90){
                    result=result+"<p style=\"color:#db1d00\">"+p.name+"</p><br>";
                }else if(tmp<=0.95){
                    result=result+"<p style=\"color:#8f8f8f\">"+p.name+"</p><br>";
                }else{
                    result=result+"<p style=\"color:#16a120\">"+p.name+"</p><br>";
                }
            }
        }

        result=result+"</center></html>";
        return result;
    }

    public static void normalizeImportance(LinkedList<Perceptron> perceptrons){
        for(Perceptron p : perceptrons){
            double vectorLength=0;
            for(int i=0;i<26;i++){
                vectorLength+=(p.importance[i]*p.importance[i]);
            }
            vectorLength=Math.sqrt(vectorLength);
            for(int i=0;i<26;i++){
                p.importance[i]=(p.importance[i]/vectorLength);
            }
        }
    }
}
