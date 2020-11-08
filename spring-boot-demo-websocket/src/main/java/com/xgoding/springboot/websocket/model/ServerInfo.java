package com.xgoding.springboot.websocket.model;

import cn.hutool.system.oshi.OshiUtil;
import com.xgoding.springboot.websocket.model.info.Cpu;
import com.xgoding.springboot.websocket.model.info.Sys;
import com.xgoding.springboot.websocket.utils.IpUtil;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.util.Util;

import java.text.DecimalFormat;
import java.util.Properties;

/**
 * <p>
 * 服务器监控信息
 * </p>
 *
 * @package: com.xgoding.springboot.websocket.model
 * @description: 服务器监控信息
 * @author: yxguang
 * @date: 2020/11/5
 * @version: V1.0
 * @modified: yxguang
 */
public class ServerInfo {
    /**
     * 系统信息
     */
    private Sys sys = new Sys();

    /**
     * CPU 信息
     */
    private Cpu cpu = new Cpu();


    public Sys getSys() {
        return sys;
    }

    public Cpu getCpu() {
        return cpu;
    }

    public void setCpu(Cpu cpu) {
        this.cpu = cpu;
    }

    /**
     * 复制监控信息
     */
    public void copyInfo() {
        setSysInfo();
        setCpuInfo();
    }

    /**
     * 获取CPU信息
     */
    private void setCpuInfo() {
//        OshiUtil.getCpuInfo()
        SystemInfo systemInfo = new SystemInfo();
        HardwareAbstractionLayer hardware = systemInfo.getHardware();
        CentralProcessor processor = hardware.getProcessor();
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        Util.sleep(1000);
        long[] ticks = processor.getSystemCpuLoadTicks();
        long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
        long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long softIrq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
        long cSys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
        long ioWait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long totalCpu = Math.max(user + nice + cSys + idle + ioWait + irq + softIrq + steal, 0L);
        DecimalFormat format = new DecimalFormat("#.00");
        cpu.setCpuNum(processor.getLogicalProcessorCount());
        cpu.setTotal((double)totalCpu);
        cpu.setSys(Double.parseDouble(format.format(cSys <= 0L ? 0.0D : 100.0D * (double)cSys / (double)totalCpu)));
        cpu.setUsed(Double.parseDouble(format.format(user <= 0L ? 0.0D : 100.0D * (double)user / (double)totalCpu)));
        if (totalCpu == 0L) {
            cpu.setWait(0.0D);
        } else {
            cpu.setWait(Double.parseDouble(format.format(100.0D * (double)ioWait / (double)totalCpu)));
        }

        cpu.setFree(Double.parseDouble(format.format(idle <= 0L ? 0.0D : 100.0D * (double)idle / (double)totalCpu)));
        cpu.setModel(processor.toString());
    }

    /**
     * 设置系统信息
     */
    private void setSysInfo() {
        Properties properties = System.getProperties();
        String osName = properties.getProperty("os.name");
        String osVersion = properties.getProperty("os.version");
        String osArch = properties.getProperty("os.arch");
        sys.setOsName(osName);
        sys.setOsArch(osArch);
        sys.setOsVersion(osVersion);
        sys.setHostIp(IpUtil.getHostIp());
        sys.setHostName(IpUtil.getHostName());
        sys.setFileSeparator(properties.getProperty("file.separator"));
        sys.setLineSeparator(properties.getProperty("line.separator"));
        sys.setPathSeparator(properties.getProperty("path.separator"));
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }
}
