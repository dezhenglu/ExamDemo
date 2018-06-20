package com.migu.schedule;


import com.migu.schedule.constants.ReturnCodeKeys;
import com.migu.schedule.info.NodeInfo;
import com.migu.schedule.info.TaskInfo;

import java.util.*;

/*
*类名和方法不能修改
 */
public class Schedule {

    // 节点集合
    Vector<NodeInfo> nodes = null;

    // 挂起队列
    Hashtable<Integer,TaskInfo> hangQueue = null;

    // 任务列表
    Vector<TaskInfo> taskInfos = null;

    public int init() {
        // 初始化节点信息
        nodes = new Vector<NodeInfo>();
        // 初始化任务挂起队列
        hangQueue = new Hashtable<Integer,TaskInfo>();
        // 初始化任务列表
        taskInfos = new Vector<TaskInfo>();
        return ReturnCodeKeys.E001;
    }


    public int registerNode(int nodeId) {

        // 校验服务节点编号
        if(!checkId(nodeId)){
            return ReturnCodeKeys.E004;
        }
        // 节点是否注册
        if (nodeExists(nodeId)){
            return ReturnCodeKeys.E005;
        }
        // 添加节点
        NodeInfo nodeInfo = new NodeInfo();
        nodeInfo.setNodeId(nodeId);
        nodeInfo.setThreshold(0);
        nodes.add(nodeInfo);
        return ReturnCodeKeys.E003;
    }

    public int unregisterNode(int nodeId) {

        // 校验任务编号
        if(!checkId(nodeId)){
            return ReturnCodeKeys.E004;
        }
        // 节点是否注册
        if (!nodeExists(nodeId)){
            return ReturnCodeKeys.E007;
        }

        int len = nodes.size();
        int removeIndex = -1;
        for(int i = 0; i < len; i++){
            NodeInfo nodeInfo = nodes.get(i);
            if(nodeInfo.getNodeId() == nodeId){
                removeIndex = i;
                break;
            }
        }
        if(removeIndex != -1){
            nodes.remove(nodeId);
            return ReturnCodeKeys.E006;
        }

        return ReturnCodeKeys.E016;
    }


    public int addTask(int taskId, int consumption) {

        // 校验任务编号
        if(!checkId(taskId)){
            return ReturnCodeKeys.E009;
        }
        // 检查是否存在
        if(taskExists(taskId)){
            return ReturnCodeKeys.E010;
        }
        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setTaskId(taskId);
        taskInfo.setConsumption(consumption);
        hangQueue.put(taskId, taskInfo);
        return ReturnCodeKeys.E008;
    }


    public int deleteTask(int taskId) {
        // 校验任务编号
        if(!checkId(taskId)){
            return ReturnCodeKeys.E009;
        }
        // 检查是否存在
        if(!taskExists(taskId)){
            return ReturnCodeKeys.E012;
        }
        hangQueue.remove(taskId);
        return ReturnCodeKeys.E011;
    }


    public static void main(String[] args){
        System.out.println(10 / 4);
    }

    public int scheduleTask(int threshold) {
        // 校验任务编号
        if(!checkId(threshold)){
            return ReturnCodeKeys.E002;
        }
        if(hangQueue.size() > 0){
            // 平均分配任务
            int nodeSize = nodes.size();
            int taskSize = hangQueue.size();
            int avgTaskNum = taskSize / nodeSize + 1;
            int taskNum = 0;
            int nodeIndex = 0;
            for(Iterator<Integer> iterator = hangQueue.keySet().iterator(); iterator.hasNext();){
                if(taskNum >= avgTaskNum){
                    nodeIndex++;
                    taskNum = 0;
                }
                NodeInfo nodeInfo = nodes.get(nodeIndex);
                int nodeId = nodeInfo.getNodeId();
                Integer taskId = iterator.next();
                TaskInfo taskInfo = hangQueue.get(taskId);
                taskInfo.setNodeId(nodeId);
                taskInfos.add(taskInfo);
                nodeInfo.setThreshold(nodeInfo.getThreshold() + taskInfo.getConsumption());
                taskNum++;
            }

            int len = nodes.size();
            NodeInfo nodeInfo1 = null;
            NodeInfo nodeInfo2 = null;
            int temp = 0;
            boolean needSchedule = false;
            for(int i = 0; i < len; i++){

                nodeInfo1 = nodes.get(i);

                for(int j = 0; j < len; j++){

                    nodeInfo2 = nodes.get(j);
                    int difference = Math.abs(nodeInfo1.getThreshold() - nodeInfo2.getThreshold());
                    if(difference > threshold){

                    }
                }

            }

        }
        return ReturnCodeKeys.E014;
    }


    public int queryTaskStatus(List<TaskInfo> tasks) {
        // TODO 方法未实现
        return ReturnCodeKeys.E000;
    }

    /**
     * 校验编号
     * @param id
     * @return
     */
    boolean checkId(int id){
        return id > 0 ? true:false;
    }

    /**
     * 校验节点是否存在
     * @param nodeId
     * @return
     */
    boolean nodeExists(int nodeId){
        int len = nodes.size();
        boolean exists = false;
        for(int i = 0; i < len; i++){
            NodeInfo nodeInfo = nodes.get(i);
            if(nodeInfo.getNodeId() == nodeId){
                exists = true;
                break;
            }
        }
        return exists;
    }

    /**
     * 校验任务是否存在
     * @param taskId
     * @return
     */
    boolean taskExists(int taskId){
        return this.hangQueue.containsKey(taskId);
    }
}
