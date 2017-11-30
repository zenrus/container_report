package zenrus.com.container.filereader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import zenrus.com.container.beans.InputBean;
import zenrus.com.container.exception.FileException;

public class FileReader {

	
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
	        	list.add(fileEntry);
	            System.out.println(fileEntry.getName());
	        }
	    }
	}

	public List<InputBean> test(String path) throws FileException{
		List<File> files = getFilesFromFolder(path);
		List<InputBean> beans = new ArrayList<InputBean>();
		for(File file : files){
			beans.addAll(ExcelControl.readInputfile(file));
			System.out.println(file.getName());
		}
		return beans;
	}

}
