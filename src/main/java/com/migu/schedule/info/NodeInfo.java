package com.migu.schedule.info;

/**
 * Created by ludz on 2018/6/20/020.
 */
public class NodeInfo {
    private int nodeId;
    private int threshold;

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public String toString() {
        return "NodeInfo{" +
                "nodeId=" + nodeId +
                ", threshold=" + threshold +
                '}';
    }
}
