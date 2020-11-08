package com.xgoding.springboot.websocket.model.info;

/**
 * <p>
 * Cpu信息
 * </p>
 *
 * @package: com.xgoding.springboot.websocket.model.info
 * @description: Cpu信息
 * @author: yxguang
 * @date: 2020/11/5
 * @version: V1.0
 * @modified: yxguang
 */
public class Cpu {
    /**
     * 核心数
     */
    private int cpuNum;
    /**
     * Cpu 总使用率
     */
    private double total;
    /**
     * Cpu 系统使用率
     */
    private double sys;
    /**
     * CPU 用户使用率
     */
    private double used;
    /**
     * CPU 等待率
     */
    private double wait;
    /**
     * 闲置率
     */
    private double free;
    /**
     * 模式
     */
    private String model;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getCpuNum() {
        return cpuNum;
    }

    public void setCpuNum(int cpuNum) {
        this.cpuNum = cpuNum;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getSys() {
        return sys;
    }

    public void setSys(double sys) {
        this.sys = sys;
    }

    public double getUsed() {
        return used;
    }

    public void setUsed(double used) {
        this.used = used;
    }

    public double getWait() {
        return wait;
    }

    public void setWait(double wait) {
        this.wait = wait;
    }

    public double getFree() {
        return free;
    }

    public void setFree(double free) {
        this.free = free;
    }
}
