public class NormalizedData {
    int[] quantityData;
    double[] shareData;
    String languageName;

    public NormalizedData(String input,String languageName){
        this.languageName = languageName;

        input=input.replaceAll("[^A-Za-z]", "").toLowerCase();
        quantityData=new int[26];
        shareData=new double[26];

        for(char ch:input.toCharArray()){
            quantityData[(ch-97)]++;
        }

        double vectorLength=0;

        for (int i=0;i<26;i++){
            vectorLength=vectorLength+quantityData[i]*quantityData[i];
        }
        vectorLength=Math.sqrt(vectorLength);
        
        for (int i=0;i<26;i++){
            shareData[i]=((double)quantityData[i]/vectorLength);
        }
    }

    public double[] getShareData() {
        return shareData;
    }

    public String getLanguageName() {
        return languageName;
    }
}
