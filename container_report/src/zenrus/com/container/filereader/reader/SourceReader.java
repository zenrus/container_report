package zenrus.com.container.filereader.reader;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class SourceReader {

	private String fileName;
	
	private static final Logger LOG = LogManager.getLogger( SourceReader.class );
	
	protected abstract List<Map<String,Object>> read(File file, int headerRow, Class<?> beanClass) throws Throwable;
	
	public abstract String getTitleTrain();
	
	public List<Map<String,Object>> readFromFile(File file, int headerRow, Class<?> beanClass){
		List<Map<String,Object>> result = null;
		this.setFileName(file.getName());
		String str = fileName+" - are reading";
		try {
			result = read(file, headerRow, beanClass);
			str = fileName+" - has been read";
		}catch (Throwable e) {
			e.printStackTrace();
			str = fileName+" - ERROR";
		}finally {
			LOG.info(str);
		}
		
		return result;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
