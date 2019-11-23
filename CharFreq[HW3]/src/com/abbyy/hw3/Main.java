package com.abbyy.hw3;

public class Main {

    public static void main(String[] args) {
        CharCounter counter = new CharCounter("C:\\Users\\Administrator\\IdeaProjects\\CharFreq[HW3]\\src\\resources\\text.txt");
        counter.GetStates("output.txt");
    }
}
