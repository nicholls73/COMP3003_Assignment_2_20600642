package edu.curtin.lib;

public class Script {
    private String code;

    public Script() {
        code = "";
    }

    public void setCode(String code) {
        code = code.substring(1, code.length() - 1);
        code = code.replaceAll("\"\"", "\"");
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
