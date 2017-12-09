package zenrus.com.container.exception;

public class FileException extends Exception{

	public FileException(String string) {
		super(string);
	}
	
	public FileException(String string, Throwable e) {
		e.printStackTrace();
		new FileException(string);
	}

	private static final long serialVersionUID = -7923748255785481241L;

	
}
