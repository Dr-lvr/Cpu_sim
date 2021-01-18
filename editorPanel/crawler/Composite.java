package pippin.editorPanel.crawler;

public class Composite<T> {
	
	Class<?> myClass;
	
	public Composite(Class<?> anotherClass) {
		
		myClass = anotherClass.getClass();
	}

	public Class<?> getMyClass() {
		return myClass;
	}

	public void setMyClass(Class<?> myClass) {
		this.myClass = myClass;
	}
}
