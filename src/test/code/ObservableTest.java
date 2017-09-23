package test.code;

public class ObservableTest {
	public static void main(String[] args) {
		Observable observable = new Observable();
		IObserver graph = new Graph(observable);
		IObserver display = new Display(observable);
		observable.update("abc", 1);
		//observable.update("def", 2);
		//observable.update("ghi", 3);
	}
}
