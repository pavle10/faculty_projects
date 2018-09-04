package stego;

import javax.swing.SwingUtilities;

public class Application {
	
	private static Application mApp;
	
	private Application() {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new AppController().start();
				
			}
		});
	}
	
	public static Application getInstance() {
		if (mApp == null)
			mApp = new Application();
		return mApp;
	}

}
