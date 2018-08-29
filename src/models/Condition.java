package models;

import com.google.gson.JsonObject;

public class Condition {
    private String opLeft;
    private String opRight;
    private String comparison;

    public Condition(JsonObject condition) {
        this.opLeft = condition.get("op_left").getAsString();
        this.opRight = condition.get("op_right").getAsString();
        this.comparison = condition.get("comparison").getAsString();
    }

    public Condition(String opLeft, String opRight, String comparison) {
        this.opLeft = opLeft;
        this.opRight = opRight;
        this.comparison = comparison;
    }

    public String getOpLeft() {
        return opLeft;
    }

    public void setOpLeft(String opLeft) {
        this.opLeft = opLeft;
    }

    public String getOpRight() {
        return opRight;
    }

    public void setOpRight(String opRight) {
        this.opRight = opRight;
    }

    public String getComparison() {
        return comparison;
    }

    public void setComparison(String comparison) {
        this.comparison = comparison;
    }
}
