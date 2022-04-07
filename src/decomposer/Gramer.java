package decomposer;

public class Gramer {
	
	public Gramer() {
	}
	
	public Object[][] getGramsArrFromObjArr(Object[] objArr_Arg) {
		Object[][] ret = new Object[getCapacityOfArrGram(objArr_Arg.length)][];
		int n = 1;	// N-gram
		int offSet = 0;	// offset of gram in array
		int j = 0;
		int k = 0;
		while(n <= objArr_Arg.length) {	
			Object[] nGram = new Object[n];
			for (int i = offSet; i < offSet+n; i++) {
				nGram[j] = objArr_Arg[i];
				j++;
				}
			ret[k++] = nGram;
			offSet++;
			j = 0;
			if (offSet > objArr_Arg.length-n) {
				n++;
				offSet = 0;
				}
			}
		return ret;
	}
	
	public int getCapacityOfArrGram(int lengthWord_Arg) {
		int ret = 0;
		for (int i = 1; i <= lengthWord_Arg; i++) 
			ret = ret + i;
		return (ret > 0)?ret:1;	//default result 1
	}

}
