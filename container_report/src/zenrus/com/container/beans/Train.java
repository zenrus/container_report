package zenrus.com.container.beans;

import java.util.List;

public class Train {

	private String title;
	
	private String index;
	
	private List<OutputBean> containers;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<OutputBean> getContainers() {
		return containers;
	}

	public void setContainers(List<OutputBean> containers) {
		this.containers = containers;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}
	
}
