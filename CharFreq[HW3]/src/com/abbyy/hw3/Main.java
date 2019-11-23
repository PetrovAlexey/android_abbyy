package com.abbyy.hw3;

public class Main {

    public static void main(String[] args) {
        CharCounter counter = new CharCounter("text.txt");
        counter.GetStates("output.txt");
    }
}
