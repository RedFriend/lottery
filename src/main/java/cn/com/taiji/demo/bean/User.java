package cn.com.taiji.demo.bean;

import java.util.LinkedList;
import java.util.List;

public class User {
    private String userName;
    private String userNumber;
    private String deptCode;
    private String deptName;
    private String workUnit;
    private String workUnitName;
    private String phoneNumber;
    private String pickNumber;
    private String prePickFlag;
    private String index;
    private Award award;
    private int pickTimes;
    private String lastRamdomNumber;
    private List<Award> pickList=new LinkedList<>();

    public String getPickNumber() {
        return pickNumber;
    }

    public void setPickNumber(String pickNumber) {
        this.pickNumber = pickNumber;
    }

    public String getLastRamdomNumber() {
        return lastRamdomNumber;
    }

    public void setLastRamdomNumber(String lastRamdomNumber) {
        this.lastRamdomNumber = lastRamdomNumber;
    }

    public int getPickTimes() {
        return pickTimes;
    }

    public void setPickTimes(int pickTimes) {
        this.pickTimes = pickTimes;
    }

    public List<Award> getPickList() {
        return pickList;
    }

    public void setPickList(List<Award> pickList) {
        this.pickList = pickList;
    }

    public String getIndex() {
        return index;
    }

    public int getIndexNo(){
        return Integer.parseInt(index);
    }
    public void setIndex(String index) {
        this.index = index;
    }

    public String getPrePickFlag() {
        return prePickFlag;
    }

    public void setPrePickFlag(String prePickFlag) {
        this.prePickFlag = prePickFlag;
    }

    public Award getAward() {
        return award;
    }

    public void setAward(Award award) {
        this.award = award;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    public String getWorkUnitName() {
        return workUnitName;
    }

    public void setWorkUnitName(String workUnitName) {
        this.workUnitName = workUnitName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", userNumber='" + userNumber + '\'' +
                ", deptCode='" + deptCode + '\'' +
                ", deptName='" + deptName + '\'' +
                ", workUnit='" + workUnit + '\'' +
                ", workUnitName='" + workUnitName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", award=" + award +
                '}';
    }
}
