package pippin.system.data;

import java.util.Map;
import java.util.Vector;

public class Job {
	
	//TODO:make the final cut of job
	private Vector<String> code;
	private int priority;
	private int stackPointer;
	
	
	//TODO:make data processing homogeneous
	public Job(int stackPointer, Map<Integer, String> partialJob) {
		
		this.stackPointer = stackPointer;
		for(int i = 0; i<partialJob.size(); i+=2) {
			
			code.add(partialJob.get(i));
		}
	}
	//TODO:make data processing homogeneous
	public Job(Vector<String> strings) {
		
		this.setCode(strings);
	}
	
	
	public Vector<String> getCode() {
		return code;
	}
	public void setCode(Vector<String> code) {
		this.code = code;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public int getStackPointer() {
		return stackPointer;
	}
	public void setStackPointer(int stackPointer) {
		this.stackPointer = stackPointer;
	}
}
