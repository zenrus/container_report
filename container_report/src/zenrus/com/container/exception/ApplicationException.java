package zenrus.com.container.exception;

public class ApplicationException extends Exception{

	public ApplicationException(String string) {
		super(string);
	}

	public ApplicationException(String string, Exception e) {
		e.printStackTrace();
		new ApplicationException(string);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7923748255785481241L;

	
}
