package com.migu.schedule;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.migu.schedule.constants.ReturnCodeKeys;
import com.migu.schedule.info.NodeInfo;
import com.migu.schedule.info.TaskInfo;


/*
*类名和方法不能修改
 */
public class Schedule {
	
	private  List<NodeInfo>  nodes = new ArrayList<NodeInfo>();
	private  List<TaskInfo> taskInfos =new ArrayList<TaskInfo>();
	private final static int HangupFlag =-1;

	/**
	 * 系统初始化
	 * @return
	 */
    public int init() {
    	nodes.clear();
    	taskInfos.clear();
        return ReturnCodeKeys.E001;
    }

    /**
     * 服务节点注册
     * @param nodeId
     * @return
     */
    public int registerNode(int nodeId) {
        if(!checkNodeId(nodeId)) {
        	return ReturnCodeKeys.E004;
        }
        if(!checkUnique(nodeId)) 
        {
        	return ReturnCodeKeys.E005;
        }
        
        NodeInfo node = new NodeInfo(nodeId); 
        nodes.add(node);
        
        //注册节点成功
        return ReturnCodeKeys.E003;
    }

    /**
     * 服务节点注销
     * @param nodeId
     * @return
     */
	public int unregisterNode(int nodeId) {
		if(!checkNodeId(nodeId)) {
        	return ReturnCodeKeys.E004;
        }
		if(!removeNode(nodeId)) 
		{
			//节点不存在
		   return ReturnCodeKeys.E007;
		}
		//挂起相关任务
		hangup(nodeId);
		//节点删除成功
        return ReturnCodeKeys.E006;
    }


	 /**
	  * 添加任务 
	  * @param taskId
	  * @param consumption
	  * @return
	  */
    public int addTask(int taskId, int consumption) {
         if(!checkTaskId(taskId)) 
         {
        	 //任务id不合规
        	 return ReturnCodeKeys.E009;
         }
         else if(!checTaskkUnique(taskId)) {
        	 //任务id已经存在
        	 return ReturnCodeKeys.E010;
         }
    	TaskInfo taskInfo = new  TaskInfo(taskId,HangupFlag,consumption);	
    	
    	taskInfos.add(taskInfo);
    	// 任务添加成功
        return ReturnCodeKeys.E008;
    }


     /**
      * 删除任务
      * @param taskId
      * @return
      */
    public int deleteTask(int taskId) {
    	if(!checkTaskId(taskId)) 
        {
       	 //任务id不合规
       	 return ReturnCodeKeys.E009;
        }
    	if(!removeTask(taskId)) 
		{
    		 //任务不存在
		   return ReturnCodeKeys.E012;
		}
    	//删除任务成功
        return ReturnCodeKeys.E011;
    }


    public int scheduleTask(int threshold) {
    	
    	if(!checkThreshold(threshold)) 
    	{
    		return ReturnCodeKeys.E002;
    	}
    	//如果任务数能平均分配到各个节点，且负载相同
    	if(checkSplit() && checkSplitTasks()) 
    	{
    		return ReturnCodeKeys.E013;
    	}
    	// 负载能均匀分配
    	else if(checkSplit())
    	{
    		return ReturnCodeKeys.E013;
    	}
    	// 分配后节点差值大于threshold
    	else if(!checkNodeThreshold(threshold)) 
    	{
    		return ReturnCodeKeys.E015;
    	}
    	else 
    	{
    		return ReturnCodeKeys.E013;
    	}
    }


    public int queryTaskStatus(List<TaskInfo> tasks) {
    	if(null==tasks) 
    	{
    		return ReturnCodeKeys.E016;
    	}
    	tasks = taskInfos;
    	Collections.sort(tasks); 
        return ReturnCodeKeys.E015;
    }
    
    /**
     * 检查nodeid是否非法
     * @param nodeId
     * @return
     */
    private boolean checkNodeId(int nodeId) {
		if(nodeId<=0) 
		{
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
    
    /**
     * 检查taskid是否非法
     * @param taskId
     * @return
     */
    private boolean checkTaskId(int taskId) {
		if(taskId<=0) 
		{
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
    
    /**
     * 校验服务id是否唯一
     * @param nodeId
     * @return
     */
    private boolean checkUnique(int nodeId) {
         for(NodeInfo node:nodes) 
         {
        	if(node.getNodeNo()==nodeId) 
        	{
        		return Boolean.FALSE;
        	} 
         }
		return Boolean.TRUE;
	}
    
    
    /**
     * 校验服务id是否唯一
     * @param nodeId
     * @return
     */
    private boolean checTaskkUnique(int taskId) {
         for(TaskInfo taskinfo:taskInfos) 
         {
        	if(taskinfo.getTaskId()==taskId) 
        	{
        		return Boolean.FALSE;
        	} 
         }
		return Boolean.TRUE;
	}
    
    /**
     * 删除任务
     * @param taskid
     * @return
     */
    private  boolean  removeTask(int taskid) 
    {
       TaskInfo  task = null;
       for(TaskInfo t:taskInfos) 
       {
      	if(t.getTaskId()==taskid) 
      	{
      		task = t;
      		break;
      	} 
       } 
       if(task==null) 
       {
    	  return Boolean.FALSE;
       }
       taskInfos.remove(task);
       return  Boolean.TRUE;
    }

    /**
     * 删除节点
     * @param nodeId
     * @return
     */
    private  boolean  removeNode(int nodeId) 
    {
       NodeInfo  node = null;
       for(NodeInfo n:nodes) 
       {
      	if(n.getNodeNo()==nodeId) 
      	{
      		node = n;
      		break;
      	} 
       } 
       if(node==null) 
       {
    	  return Boolean.FALSE;
       }
       nodes.remove(node);
       return  Boolean.TRUE;
    }

    private void hangup(int nodeId) 
    {
    	for(TaskInfo taskinfo :taskInfos) 
    	{
    		if(taskinfo.getNodeId()==nodeId) 
    		{
    			taskinfo.setNodeId(HangupFlag);
    		}
    	}
    }
    
    //任务能够平分
    private  boolean  checkSplit() 
    {
    	int  sumload =sumload();
    	return  sumload%nodes.size()==0;
    }
    
    //各个节点可以分配相同的任务数
    private  boolean  checkSplitTasks() 
    {
    	return  taskInfos.size()%nodes.size()==0;
    }
    
    
    private int sumload()
    {
    	int sumload = 0;
    	for(TaskInfo t :taskInfos)
    	{
    		sumload= sumload+t.getConsumption();
    	}
    	return sumload;
    }
    
    
   private  boolean  checkNodeThreshold(int threshold) 
   {
	   for (int i = 0; i <nodes.size(); i++) {
		   NodeInfo  temp= nodes.get(i);
           for (int j = 1; i <nodes.size(); j++) { 
        	  return !(Math.abs(temp.getLoadNum()-nodes.get(j).getLoadNum())>=10);
           }  
	   }
	   return  Boolean.TRUE;
   }
   
   private  boolean  checkThreshold(int threshold) 
   {
	   if(threshold<=0)
	   return  Boolean.FALSE;
	   else
	  return  Boolean.TRUE;  
   }
   
  
    
}
