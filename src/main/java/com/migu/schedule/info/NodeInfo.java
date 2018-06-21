package com.migu.schedule.info;

import java.util.ArrayList;
import java.util.List;

public class NodeInfo {

	private  Integer nodeNo;
	private  Integer  loadNum=0;
	
	private  List<Task>  tasks = new ArrayList<Task>();
	private  List<TaskInfo> taskInfos = new ArrayList<TaskInfo>();

	public  NodeInfo(Integer nodeNo) 
	{
		this.nodeNo = nodeNo;
	}
	
	public Integer getNodeNo() {
		return nodeNo;
	}

	public void setNodeNo(Integer nodeNo) {
		this.nodeNo = nodeNo;
	}

	public Integer getLoadNum() {
		return loadNum;
	}

	public void setLoadNum(Integer loadNum) {
		this.loadNum = loadNum;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public List<TaskInfo> getTaskInfos() {
		return taskInfos;
	}
	
	public  boolean addTaskInfo(TaskInfo  taskInfo) 
	{
		taskInfos.add(taskInfo);
		//System.out.println(taskInfo);
		//计算节点总负载
		loadNum =loadNum+taskInfo.getConsumption();
		return Boolean.TRUE;
	} 
	
	
}
