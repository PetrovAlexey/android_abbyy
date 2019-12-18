package com.abbyy.hw3;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CharCounter {

    private Map<Character, Integer> states = new HashMap<>();

    public CharCounter(String inputFilePath) {
        try(BufferedReader br = new BufferedReader (new FileReader(inputFilePath)))
        {
            char c;
            int input;
            while((input = br.read()) != -1){
                c = (char) input;
                if (states.containsKey(c)) {
                    states.put(c , (Integer) (states.get(c) + 1));
                } else {
                    states.put(c, 1);
                }
            }
        }
    }

    public void GetStates(String outputFilePath){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath)))
        {
            for (Map.Entry<Character, Integer> iter : states.entrySet()) {
                bw.write(iter.getKey() + ": " + iter.getValue() + "\n");
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }


}
