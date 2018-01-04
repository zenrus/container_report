package zenrus.com.container.filereader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import zenrus.com.container.beans.InputBean;
import zenrus.com.container.exception.FileException;
import zenrus.com.container.filereader.reader.ExcelReader;

public class FileReader {

	private static final Logger LOG = LogManager.getLogger( ExcelReader.class );
	
	
	public List<File> getFilesFromFolder(String folderPath) throws FileException{
		List<File> files = new ArrayList<File>();
		final File folder = new File(folderPath);
		if(!folder.isDirectory()){
			throw new FileException("Can't find folder "+ folderPath);
		}
		listFilesForFolder(folder, files);
		return files;
	}
	
	
	public void listFilesForFolder(final File folder, List<File> list) {
		
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry, list);
	        } else {
	        	if(isExcel(fileEntry)) {
	        		list.add(fileEntry);
	        		LOG.debug(fileEntry.getName());
	        	}
	        }
	    }
	}

	private boolean isExcel(File file) {
		String extension = getFileExtension(file);
		return "xls".equals(extension) || "xlsx".equals(extension);
	}

	private String getFileExtension(File file) {
		String extension = "";
		String fileName = file.getName();
		int i = fileName.lastIndexOf('.');
		if (i > 0) {
		    extension = fileName.substring(i+1);
		}
		return extension;
	}
	
	public List<InputBean> test(String path) throws FileException{
		List<File> files = getFilesFromFolder(path);
		List<InputBean> beans = new ArrayList<InputBean>();
		for(File file : files){
			beans.addAll(SourceControl.readInputfile(file));
			LOG.debug(file.getName());
		}
		return beans;
	}

}
