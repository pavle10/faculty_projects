package les;

import javax.swing.SwingUtilities;

public class Application {
	
	private static Application mInstance;
	
	private Application() {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new Controller().run();
				
			}
		});
	}
	
	public static Application getInstance() {
		if (mInstance == null)
			mInstance = new Application();
		return mInstance;
	}

}
