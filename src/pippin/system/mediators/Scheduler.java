package pippin.system.mediators;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

import pippin.system.data.Job;
//import pippin.pippin.system.data.ProcessQueue;

public class Scheduler {

	private Queue<Job> queue;
	private int actualLife;
	private boolean control;

	public Scheduler() {

		this.queue = new LinkedList<Job>();
		this.actualLife = 0;
	}

	public void addJob(Job process) {

		this.queue.add(process);
	}

	public int getStackPointer() {

		return queue.peek().getStackPointer();
	}

	public Vector<String> nextJob() throws Exception {

		if (!queue.isEmpty()) {
			this.actualLife = queue.peek().getCode().size();
			return queue.remove().getCode();
		}
		throw new Exception("no jobs availables");
	}

	public int getProcessLife() {

		return actualLife;
	}

	public void decLife() {

		--actualLife;
		// &&save program counter flag
	}

	public boolean queryControl() {
		
		if(this.getControl()) {
			this.setControl(false);
			return true;
		}
		return false;
	}
	
	public boolean getControl() {

		return this.control;
	}

	public void setControl(boolean control) {

		this.control = control;
	}

	public Queue<Job> getQueue() {
		return queue;
	}

	public void setQueue(Queue<Job> queue) {
		this.queue = queue;
	}
}
