package com.catalyst.sonar.score.batch;

import java.util.ArrayList;
import java.util.List;

public class Trophy {
	
	private String name;
	private List<Criteria> criteria = new ArrayList<Criteria>();
	
	public Trophy(String name, List<Criteria> criteria){
		this.setName(name);
		this.setCriteria(criteria);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Criteria> getCriteria() {
		return criteria;
	}

	public void setCriteria(List<Criteria> criteria) {
		this.criteria = criteria;
	}
	

}
