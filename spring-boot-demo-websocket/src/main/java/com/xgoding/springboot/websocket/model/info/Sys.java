package com.xgoding.springboot.websocket.model.info;

/**
 * <p>
 * 系统信息
 * </p>
 *
 * @package: com.xgoding.springboot.websocket.model.info
 * @description: 系统信息
 * @author: yxguang
 * @date: 2020/11/5
 * @version: V1.0
 * @modified: yxguang
 */
public class Sys {
    /**
     * 服务器名称
     */
    private String hostName;
    /**
     * 服务器IP
     */
    private String hostIp;
    /**
     * 操作系统名称
     */
    private String osName;
    /**
     * 操作系统架构
     */
    private String osArch;
    /**
     * 操作系统版本
     */
    private String osVersion;

    /**
     * 文件分隔符
     */
    private String fileSeparator;

    /**
     * 行分隔符
     */
    private String lineSeparator;

    /**
     * 路径分隔符
     */
    private String pathSeparator;


    public Sys() {
        //
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getOsArch() {
        return osArch;
    }

    public void setOsArch(String osArch) {
        this.osArch = osArch;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getFileSeparator() {
        return fileSeparator;
    }

    public void setFileSeparator(String fileSeparator) {
        this.fileSeparator = fileSeparator;
    }

    public String getLineSeparator() {
        return lineSeparator;
    }

    public void setLineSeparator(String lineSeparator) {
        this.lineSeparator = lineSeparator;
    }

    public String getPathSeparator() {
        return pathSeparator;
    }

    public void setPathSeparator(String pathSeparator) {
        this.pathSeparator = pathSeparator;
    }
}
