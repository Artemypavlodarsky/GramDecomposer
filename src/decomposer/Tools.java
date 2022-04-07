package decomposer;

import java.util.Arrays;

public class Tools {
	
	public static CharSequence regEx_Stces = ".,?!:;";
	public static CharSequence regEx_Words = "\\s\\u0009\\u0085\\u2028\\u2029\\n";
	public static CharSequence negatedChars = "_&%$~*{}()[]@#^\"0123456789";	//negated characters	

	private GramDictionary gramDict;
	
	public Tools(GramDictionary gram) {
		this.gramDict = gram;
	}
	
	public void decomposeLine(String str_Arg, boolean isDecomposeWords) {
		Gramer grm = new Gramer();
		String strClearedFromGarbage = clearGarbageChars(str_Arg, "["+negatedChars+"]+", true);
		strClearedFromGarbage = clearRedudantChars(strClearedFromGarbage, 2);	// Clear on redundant character
		String[] arrOfObj_Snts = getArrStrOfSplitSntsFromLine(strClearedFromGarbage); // clear incoming String from negated characters and divide result into array of sentences
		for (int i = 0; i < arrOfObj_Snts.length; i++) {	
			Object[] arrOfObj_Words = getArrObjOfSplitWordsFromStr(arrOfObj_Snts[i]);	// divide each sentences on word
			String[] arrOfObj_GramsWords = getStrArrFromObjArr(grm.getGramsArrFromObjArr(arrOfObj_Words), ",");
			gramDict.writeInDictionary(arrOfObj_GramsWords);	//we get ngram's from array words of current sentence and write ngram in map
			gramDict.addWholeWordsCount(arrOfObj_Words.length);	//ADD count of the Whole Words
			if (isDecomposeWords)
				for (int j = 0; j < arrOfObj_Words.length; j++) {
					Object[] arrOfObj_Chars = getArrObjFromCharArr(arrOfObj_Words[j].toString().toCharArray()); // extract Chars(letters) from current word
					Object[][] arrOfObj_GramLtrs = grm.getGramsArrFromObjArr(arrOfObj_Chars);
					String[] arrOfStr_Ltrs = getStrArrFromObjArr(arrOfObj_GramLtrs, ", ");
					gramDict.writeInDictionary(arrOfStr_Ltrs);	// and write ngram in map
					}
			}
	}
	
	public String clearRedudantChars(String str_Arg, int threshold) { 
		if (str_Arg.equals("") || (threshold <= 0))
			return str_Arg;
		StringBuilder ret = new StringBuilder(str_Arg);
		int repeats = 1;
		int i = 0;
		while (i < ret.length()) {
			if ( (i > 0) && (ret.charAt(i) == ret.charAt(i-1)) ) 
				repeats++;
			if (repeats > threshold)  {	// redundancy threshold
				ret.delete(i-(repeats-threshold), i);
				i = i-(repeats);
				repeats = 1;
				} 
			i++;
			}
		return ret.toString();
	}
	
	public static String clearGarbageChars(String str_Arg, String remSymbols, boolean remDivis) {
		if ("".equals(str_Arg) || (str_Arg.isBlank())){
			return "";
		} else {StringBuilder sb = new StringBuilder(str_Arg);
				int index = -1;		// index of pos current char must be removed from Result string
				String strTmp = null;
				if (remDivis) {		//removed hyphen and apostrophe from duals words 
					while((sb.indexOf("-") > -1) || (sb.indexOf("'") > -1)) { //replacement "-" or "'" on space
						if (sb.indexOf("-") > -1)
							index = sb.indexOf("-");
							else if (sb.indexOf("'") > -1)
									index = sb.indexOf("'");
						sb.setCharAt(index, ' ');
						}
					}
				for (int i = 0; i < remSymbols.length(); i++) {//delete each negated characters
					strTmp = remSymbols.substring(i,i+1);
					index = sb.indexOf(strTmp);
					while(sb.indexOf(strTmp) > -1) {
						sb.deleteCharAt(index);	
						index = sb.indexOf(strTmp);
						}
					}
			    return sb.toString();
				}
	}
	
	public String[] getArrStrOfSplitSntsFromLine(String str_Arg) {	//str_Arg - must be cleared on customDeleteChars
		return str_Arg.strip().split("["+regEx_Stces+"]+");
	}
	
	public Object[] getArrObjOfSplitWordsFromStr(String str_Arg) {	//str_Arg - must be cleared on customDeleteChars
		String[] strArrTemp = str_Arg.strip().split("["+regEx_Words+"]+");
		Object[] ret = new Object[strArrTemp.length];
		for (int i = 0; i < strArrTemp.length; i++) 
			ret[i] = strArrTemp[i];
		return ret;
	}
	
	public String[] getStrArrFromObjArr(Object[][] obj_Arg, String negatedDelims) {
		String[] ret = new String[obj_Arg.length];
		for (int i = 0; i < obj_Arg.length; i++) 
			ret[i] = Arrays.toString(obj_Arg[i]).replace(negatedDelims, "");
		return ret;
	}
	
	public Object[] getArrObjFromCharArr(char[] chars_Arg) {
		Object[] ret=new Object[chars_Arg.length];
		for (int i=0; i < chars_Arg.length; i++) 
			ret[i]=chars_Arg[i];
		 return ret;
	}

}