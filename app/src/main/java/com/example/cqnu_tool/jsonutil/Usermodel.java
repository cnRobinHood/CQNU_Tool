package com.example.cqnu_tool.jsonutil;

public class Usermodel {

    private boolean monitor;
    private int rolecount;
    private String rolekeys;
    private String rolevalues;
    private int status;
    private boolean usable;

    public void setMonitor(boolean monitor) {
        this.monitor = monitor;
    }

    public boolean getMonitor() {
        return monitor;
    }

    public void setRolecount(int rolecount) {
        this.rolecount = rolecount;
    }

    public int getRolecount() {
        return rolecount;
    }

    public void setRolekeys(String rolekeys) {
        this.rolekeys = rolekeys;
    }

    public String getRolekeys() {
        return rolekeys;
    }

    public void setRolevalues(String rolevalues) {
        this.rolevalues = rolevalues;
    }

    public String getRolevalues() {
        return rolevalues;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setUsable(boolean usable) {
        this.usable = usable;
    }

    public boolean getUsable() {
        return usable;
    }

}