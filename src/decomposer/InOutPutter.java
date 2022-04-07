package decomposer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;

import com.opencsv.CSVWriter;

public class InOutPutter {
	
	static String readPathFile = "voyna-i-mir-tom-1.txt";
	static String writePathFile = "data.csv";
	
	public InOutPutter() {}
	
	public void writeCSV(List <Entry<String, Status>> srcList_arg) throws Exception {
	    try {
			CSVWriter writer = new CSVWriter(new FileWriter(writePathFile, true));
		    String [] record ;
		    System.out.println("Begining wite to CSV file");
			for (Entry<String, Status> entry : srcList_arg) {
				record = entry.toString().split("=");
				writer.writeNext(record);
				}
		     
		    writer.close();
	    }
	    catch (FileNotFoundException e) { e.printStackTrace(); } 
		catch (IOException e) { e.printStackTrace(); }
	    System.out.println("--------> Write CSV file has finished. <--------"); 
	}
	
	public String startScanFile() throws Exception{
		try {
			FileReader fileReader = new FileReader(readPathFile);
			BufferedReader buffReader = new BufferedReader(fileReader);
			GramDictionary gram = new GramDictionary();
			Tools tool = new Tools(gram);
			String line;
			while ((line = buffReader.readLine()) != null) {
				if (!line.isBlank()) 
					tool.decomposeLine(line, true);
				}
			buffReader.close();
			fileReader.close();
			gram.calcPrcntConsistWordOfDict();
			gram.printNgramMap();
			writeCSV(gram.getSortedListFromGramDict());
		 } 
		catch (FileNotFoundException e) { e.printStackTrace(); } 
		catch (IOException e) { e.printStackTrace(); }
		return "--------> Decomposed file has finshed. <--------";
}

}
