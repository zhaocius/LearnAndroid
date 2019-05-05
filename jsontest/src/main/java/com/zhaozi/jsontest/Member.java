package com.zhaozi.jsontest;

import java.io.Serializable;
import java.util.Objects;

public class Member  implements Serializable {
    private String name;
    private String ip;
    private int port;
    private boolean masterFlag;
    private boolean groupFlag;
    private boolean returnFlag;

    public Member(String name, String ip, int port, boolean masterFlag, boolean groupFlag, boolean returnFlag) {
        this.name = name;
        this.ip = ip;
        this.port = port;
        this.masterFlag = masterFlag;
        this.groupFlag = groupFlag;
        this.returnFlag = returnFlag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isMasterFlag() {
        return masterFlag;
    }

    public void setMasterFlag(boolean masterFlag) {
        this.masterFlag = masterFlag;
    }

    public boolean isGroupFlag() {
        return groupFlag;
    }

    public void setGroupFlag(boolean groupFlag) {
        this.groupFlag = groupFlag;
    }

    public boolean isReturnFlag() {
        return returnFlag;
    }

    public void setReturnFlag(boolean returnFlag) {
        this.returnFlag = returnFlag;
    }

    @Override
    public String toString() {
        return "Member{" +
                "name='" + name + '\'' +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", masterFlag=" + masterFlag +
                ", groupFlag=" + groupFlag +
                ", returnFlag=" + returnFlag +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return port == member.port &&
                masterFlag == member.masterFlag &&
                groupFlag == member.groupFlag &&
                returnFlag == member.returnFlag &&
                name.equals(member.name) &&
                ip.equals(member.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, ip, port, masterFlag, groupFlag, returnFlag);
    }
}
