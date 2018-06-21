package com.migu.schedule.info;

public class Task {
	
	private Integer  taskNo;
	private Integer  load;
	
	
	public Task(Integer taskNo, Integer load) {
		super();
		this.taskNo = taskNo;
		this.load = load;
	}
	public Integer getTaskNo() {
		return taskNo;
	}
	public void setTaskNo(Integer taskNo) {
		this.taskNo = taskNo;
	}
	public Integer getLoad() {
		return load;
	}
	public void setLoad(Integer load) {
		this.load = load;
	}

}
