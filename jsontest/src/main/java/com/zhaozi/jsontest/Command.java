package com.zhaozi.jsontest;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Command implements Serializable {
    public Command(int commandType, String commandTypeStr, String status, int memberCount, List<Member> memberList) {
        this.commandType = commandType;
        this.commandTypeStr = commandTypeStr;
        this.status = status;
        this.memberCount = memberCount;
        this.memberList = memberList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Command command = (Command) o;
        return commandType == command.commandType &&
                memberCount == command.memberCount &&
                commandTypeStr.equals(command.commandTypeStr) &&
                status.equals(command.status) &&
                memberList.equals(command.memberList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commandType, commandTypeStr, status, memberCount, memberList);
    }

    @Override
    public String toString() {
        return "Command{" +
                "commandType=" + commandType +
                ", commandTypeStr='" + commandTypeStr + '\'' +
                ", status='" + status + '\'' +
                ", memberCount=" + memberCount +
                ", memberList=" + memberList +
                '}';
    }

    public int getCommandType() {
        return commandType;
    }

    public void setCommandType(int commandType) {
        this.commandType = commandType;
    }

    public String getCommandTypeStr() {
        return commandTypeStr;
    }

    public void setCommandTypeStr(String commandTypeStr) {
        this.commandTypeStr = commandTypeStr;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public List<Member> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<Member> memberList) {
        this.memberList = memberList;
    }

    private int commandType;
    private String commandTypeStr;
    private String status;
    private int memberCount;
    private List<Member> memberList;
}
