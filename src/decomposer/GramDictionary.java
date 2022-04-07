package decomposer;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public class GramDictionary {
	
	private TreeMap<String, Status> nGrams;
	private long wholeWordsCount = 0;
	private long commonWordsCount = 0;
	
	public GramDictionary() {
		nGrams = new  TreeMap<String, Status>();
	}
	
	public void addWholeWordsCount(int count_arg) {
		wholeWordsCount += count_arg;
	}
	
	public List <Entry<String, Status>> getSortedListFromGramDict() {
		List <Entry<String, Status>> sortList = nGrams.entrySet().stream().sorted(
				new Comparator<Map.Entry<String, Status>>() {
	            public int compare(Map.Entry<String, Status> o1, Map.Entry<String, Status> o2) {
	                return (o1.getValue()).compareTo(o2.getValue());
	                }}).toList();
		return sortList;
	}
	
	public void calcPrcntConsistWordOfDict() {
		for(Object key : nGrams.keySet()) {
			Status status = nGrams.get(key);
			double gramCount = status.getCount();
			double prcnt = (gramCount*100)/(commonWordsCount);
			status.setPercentConsist( prcnt );
			nGrams.put(String.valueOf(key), status);
		}
	}
	
	public void writeInDictionary(String[] str_Arg) {
		if (str_Arg != null) {
			for (int i = 0; i < str_Arg.length-1; i++) {
				if (!nGrams.containsKey(str_Arg[i])) {
					Status status = new Status(1L, 0);
					nGrams.put(str_Arg[i], status);
				}
				else {
					Status status = nGrams.get(str_Arg[i]);
					status.addCount(1L);
					nGrams.put(str_Arg[i], status);
					}
			commonWordsCount += 1;	
			}
		}
	}
	
	public void printNgramMap() {
		nGrams.entrySet().stream().sorted(
				new Comparator<Map.Entry<String, Status>>() {
		            public int compare(Map.Entry<String, Status> o1, Map.Entry<String, Status> o2) {
		                return (o1.getValue()).compareTo(o2.getValue());
		                }
		        }).forEach(System.out::println);
		System.out.println("printNgramMap():the Whole Words count = "+wholeWordsCount);
		System.out.println("printNgramMap():Common Words Count = "+commonWordsCount);
	}
	
	public TreeMap<String, Status> getNgramsMap() {
		return nGrams; 
	}

}
