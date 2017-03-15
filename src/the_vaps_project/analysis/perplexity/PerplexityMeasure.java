package the_vaps_project.analysis.perplexity;

public class PerplexityMeasure {

	public static double perplexity(String pathPhi, String pathTheta){
		// readFile phi
		// This file contains the word-topic distributions, i.e., p(wordw|topict). 
		// Each line is a topic, each column is a word in the vocabulary
		double[][] phiLDA =ReadLDAdata.readLDA(pathPhi);
		
		// readFile theta
		// This file contains the topic-document distributions, i.e., p(topict|documentm). 
		// Each line is a document and each column is a topic.
		double[][] thetaLDA =ReadLDAdata.readLDA(pathTheta);
		
		double perplexity=0;
		
		for (int nD=0;nD<thetaLDA.length;nD++){
			for (int nW=0;nW<phiLDA[0].length;nW++){
				double sum=0;
				//System.out.println("-----------------");
				for (int nT=0;nT<phiLDA.length;nT++){
					sum+=thetaLDA[nD][nT] * phiLDA[nT][nW];
					//System.out.println(thetaLDA[nD][nT] + " " + phiLDA[nT][nW]);
				}
				
				perplexity+=Math.log(sum);
				//System.out.println(perplexity + " " + phiLDA[0].length);
				if (nW==1) {
					nD=thetaLDA.length;
					nW=phiLDA[0].length;
				}
			}
		}
		
		perplexity=Math.exp((double) -perplexity /(double) phiLDA[0].length);
		
		return perplexity;
		
	}
	
	
	public static void main (String[] args){
		String base = "appdata/Twitter-LDA/data/ModelRes/"; //output folder of LDA, where found the *.phi and *.theta files
		for(int i = 2; i<=20; i++){  //number of models generated (in term of number of topic)
			String pathPhi=base+"ebola"+i+"/WordsDistributionInTopics.phi";
			String pathTheta=base+"ebola"+i+"/TopicsDistributionOnUsers.theta";
			double perplexity= perplexity(pathPhi, pathTheta);
			//write the perflexity result
			System.out.println(i+"\t"+perplexity);
		}		
	}

}
