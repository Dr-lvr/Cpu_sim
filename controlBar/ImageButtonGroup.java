package pippin.controlBar;

public class ImageButtonGroup {

	public ImageButtonGroup() {
	}

	public ImageButton getCurrent() {
		return currentChoice;
	}

	public synchronized void setCurrent(ImageButton button) {
		if (button != null && button.group != this)
			return;
		if (button != currentChoice) {
			ImageButton oldChoice = currentChoice;
			currentChoice = button;
			if (oldChoice != null)
				oldChoice.setStateInternal(false);
			if (button != null)
				button.setStateInternal(true);
		}
	}

	public String toString() {
		if (currentChoice != null)
			return getClass().getName() + "[current=" + currentChoice.labelOn + "]";
		else
			return getClass().getName() + "[current=null]";
	}

	ImageButton currentChoice;
}
