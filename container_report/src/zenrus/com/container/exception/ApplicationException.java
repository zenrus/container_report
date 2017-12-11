package zenrus.com.container.exception;

public class ApplicationException extends Exception{

	public ApplicationException(Exception e) {
		super(e);
	}

	public ApplicationException(String string, Exception e) {
		
		new Exception(e);
	}

	private static final long serialVersionUID = -7923748255785481241L;

	
}
