package com.abbyy.hw1;

public class Main {
    public static double distance(Point a, Point b) {
        return Math.sqrt(Math.pow((a.getX()-b.getX()), 2) + Math.pow((a.getY() - b.getY()), 2));
    }
    public static void main(String[] args) {
        Point a = new Point(3,0);
        Point b = new Point(0,4);
        System.out.println(distance(a, b));
    }
}
