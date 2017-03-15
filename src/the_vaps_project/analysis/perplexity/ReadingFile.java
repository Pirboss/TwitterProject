package the_vaps_project.analysis.perplexity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ReadingFile {

	public static Object[] readFileForObjectTab(File f)
	{
		ArrayList<String> al = new ArrayList<String>();
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream(f));
			BufferedReader br = new BufferedReader(isr);
			  String strLine;
			  //Read File Line By Line
			  while ((strLine = br.readLine()) != null)   
			  {
					  al.add( strLine);
			  }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	  return al.toArray();
		
	}

	
	public static String readFile(File f)
	{
		StringBuffer al=new StringBuffer();
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream(f));
			BufferedReader br = new BufferedReader(isr);
			  String strLine;
			  //Read File Line By Line
			  while ((strLine = br.readLine()) != null)   
			  {
					  al.append(strLine).append(" ");
			  }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	  return al.toString();
		
	}
	
}
