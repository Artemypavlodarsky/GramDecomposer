package decomposer;

public class Status implements Comparable<Status> {
	
	/*
	 * The status of a word in the general text includes the number and 
	 * concentration of the word in the text (as a percentage).
	 * 
	 * */
	
	private Long count;
	private double percentConsist;	
	
	Status(Long count_Arg, double percentConsist_Arg){
		setCount(count_Arg);
		setPercentConsist(percentConsist_Arg);
	}
	
	public void addCount(Long count_Arg) {
		setCount(getCount()+count_Arg); // add count value
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count_Arg) {
		count = count_Arg;
	}
	
	public double getPercentConsist() {
		return percentConsist;
	}

	public void setPercentConsist(double prcnt) {
		percentConsist = prcnt;
	}

	@Override
	public int compareTo(Status o) {
		return Double.compare(this.getPercentConsist(), o.percentConsist);
	}
	
	@Override
	public String toString() {
		return count+", "+percentConsist;
	}
	
	@Override
	public int hashCode() {
		return this.hashCode();
	}
	


}
