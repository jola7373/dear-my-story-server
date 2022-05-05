package com.qnnect.cafe.domain;

public enum EQuestionCycle {
    everyDay(1), threeDay(3), fiveDay(5), sevenDay(7);

    private final int value;

    EQuestionCycle(int value) {this.value = value;}

    public int getValue() { return value; }
}
