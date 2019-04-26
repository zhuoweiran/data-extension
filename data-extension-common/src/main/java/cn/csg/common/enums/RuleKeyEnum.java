package cn.csg.common.enums;

public enum RuleKeyEnum {
    WINDOWN("window"),TARGET("target");
    private String key;

    RuleKeyEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
