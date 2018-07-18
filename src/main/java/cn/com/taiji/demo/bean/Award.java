package cn.com.taiji.demo.bean;

public class Award {
    private String name;
    private String code;
    //被使用了的标志
    private String frozenFlag;
    private String attr1;
    private String attr2;
    private String attr3;
    private String attr4;
    private String attr5;
    private String pickFlag;
    private String pickFrozen;

    public void setPickFrozen(String pickFrozen) {
        this.pickFrozen = pickFrozen;
    }

    public String getPickFrozen() {
        return pickFrozen;
    }

    public String getPickFlag() {
        return pickFlag;
    }

    public void setPickFlag(String pickFlag) {
        this.pickFlag = pickFlag;
    }

    public String getFrozenFlag() {
        return frozenFlag;
    }

    public void setFrozenFlag(String frozenFlag) {
        this.frozenFlag = frozenFlag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAttr1() {
        return attr1;
    }

    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    public String getAttr2() {
        return attr2;
    }

    public void setAttr2(String attr2) {
        this.attr2 = attr2;
    }

    public String getAttr3() {
        return attr3;
    }

    public void setAttr3(String attr3) {
        this.attr3 = attr3;
    }

    public String getAttr4() {
        return attr4;
    }

    public void setAttr4(String attr4) {
        this.attr4 = attr4;
    }

    public String getAttr5() {
        return attr5;
    }

    public void setAttr5(String attr5) {
        this.attr5 = attr5;
    }

    @Override
    public String toString() {
        return "Award{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", attr1='" + attr1 + '\'' +
                ", attr2='" + attr2 + '\'' +
                ", attr3='" + attr3 + '\'' +
                ", attr4='" + attr4 + '\'' +
                ", attr5='" + attr5 + '\'' +
                '}';
    }
}
