package pippin.test;

import pippin.manager.AnimationManager;
import pippin.state.State;

public interface Simulation {

	State getProgramState();

	int getWidth();

	int delayValue();

	boolean isAnimating();

	long getNextTime();

	AnimationManager getCpu();

}
