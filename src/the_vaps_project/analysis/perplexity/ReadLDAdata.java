package the_vaps_project.analysis.perplexity;

import java.io.File;
import java.util.ArrayList;

public class ReadLDAdata {

	public ReadLDAdata() {
		
	}
	
	/**
	 * Read  LDA 
	 * @param path : input file with  LDA results
	 */
	public static double[][] readLDA(String path){
		Object[] phiTab = ReadingFile.readFileForObjectTab(new File(path));
		ArrayList<ArrayList<Double>> LDARes = new ArrayList<ArrayList<Double>>();
		
		for (Object o:phiTab){
			String[] LDATab= o.toString().split(" ");
			if (LDATab.length>0)
			{
				ArrayList<Double> LDAligne = new ArrayList<Double>();
				for (String s:LDATab){
					LDAligne.add(Double.parseDouble(s));
				}
				LDARes.add(LDAligne);
				
			}
		}
		
		
		
		double[][] LDAoutput = new double[LDARes.size()][LDARes.get(0).size()];
		for (int i=0;i<LDARes.size();i++){
			for (int j=0;j<LDARes.get(i).size();j++){
				LDAoutput[i][j]=LDARes.get(i).get(j);
			}
		}
		
		return LDAoutput;
	}
	
	
	public static void main(String[] args){
		String path = "/Users/laure/Desktop/soulierSIGOld/workspace/PEPS/data/LDA/sandy/LDA10/model-final.theta";
		double[][] res = readLDA(path);
		for (int i=0;i<res.length;i++){
			for (int j=0;j<res[0].length;j++){
				System.out.print(res[i][j] + "   ");
			}
			System.out.println("\n");
		}
	}

}
