package pippin.system.mediators;

import java.util.HashMap;
import java.util.Map;

import pippin.system.data.Job;

// This controller supports a text editor a services monitor plus a virtual ram monitor

//TODO: this controller has a default memory management map but it must be adaptable changing hardware and paging capacity

public class Controller {

	private static Controller theController;
	private Scheduler myScheduler;

	private Map<Integer, Job> serviceSlots;
	private boolean[] serviceAvailables;
	private boolean[] servicePressed;

	private int pressed;
	private int lastAdded;
	
	private boolean edited;
	private boolean serviceRequest;

	// private constructor
	private Controller() {

		myScheduler = new Scheduler();
		serviceSlots = new HashMap<Integer, Job>();
		serviceAvailables = new boolean[8];
		servicePressed = new boolean[8];

		edited = false;
		serviceRequest = false;
	}

	// singleton pattern at lazy time
	public static Controller getControllerInstance() {
		if (theController == null) {
			synchronized (Controller.class) {
				if (theController == null) {
					theController = new Controller();
				}
			}
		}
		return theController;
	}

	public boolean isEdited() {
		return edited;
	}

	public void setEdited(boolean edited) {
		this.edited = edited;
	}

	public boolean queryEditor() {
		if (isEdited()) {
			setEdited(false);
			return true;
		}
		return false;
	}
	
	public boolean isServiceRequest() {
		return serviceRequest;
	}

	public void setServiceRequest(boolean request) {
		this.serviceRequest = request;
	}

	public boolean queryService() {
		if (isServiceRequest()) {
			this.setServiceRequest(false);
			return true;
		}
		return false;
	}

	public int getAvailableService() {

		for (int i = 0; i < serviceAvailables.length; ++i) {
			if (!serviceAvailables[i]) {
				return i;
			}
		}
		return -1;
	}

	public void setNotAvailableService(int i) {

		this.serviceAvailables[i] = true;
	}

	public int getServicePressed() {

		for (int i = 0; i < servicePressed.length; ++i) {
			if (servicePressed[i]) {
				this.setServiceRequest(true);
				return i;
			}
		}
		return -1;
	}

	public void setServicePressed(int pressed) {

		for (int i = 0; i < servicePressed.length; ++i) {
			if (i == pressed) {
				servicePressed[i] = true;
			} else {
				servicePressed[i] = false;
			}
		}
	}

	public void setLastAdded(int lastAddedJob) {

		this.lastAdded = lastAddedJob;
	}
	
	public int getLastAdded() {

		return this.lastAdded;
	}
	
	public Job getSlot(int slot) {

		return this.serviceSlots.get(slot);
	}

	public void setInSlot(Job thisJob) {

		this.serviceSlots.put(this.getAvailableService(), thisJob);
		this.setLastAdded(this.getAvailableService());
		this.setNotAvailableService(this.getAvailableService());
	}

	public Map<Integer, Job> getServiceSlots() {
		return serviceSlots;
	}

	public int getPressed() {
		return pressed;
	}

	public void setPressed(int pressed) {
		this.pressed = pressed;
	}

	public Scheduler getMyScheduler() {
		return myScheduler;
	}
}
