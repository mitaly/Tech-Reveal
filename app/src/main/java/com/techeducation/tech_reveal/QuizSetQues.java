package com.techeducation.tech_reveal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mitaly on 12/10/2016.
 */
public class QuizSetQues {
    List<QuesBean> quesBeans;

    public QuizSetQues() {
        quesBeans=new ArrayList<>();
        addQuesBean();
    }

    private void addQuesBean() {
        quesBeans.add(new QuesBean("Which among following is Volatile?", "ROM", "E-PROM", "RAM", "RAM"));
        quesBeans.add(new QuesBean("Where the result of an arithmetic and logical operation are stored ?", "In Accumulator", "In Cache Memory", "In ROM", "In Accumulator"));
        quesBeans.add(new QuesBean("Which determines the address of I/O interface ?", "Register select", "Chip select", "Both of above", "Both of above"));
        quesBeans.add(new QuesBean("An exception condition in a computer system caused by an event external to the CPU is known as ?", "Halt", "Process", "Interrupt", "Interrupt"));
        quesBeans.add(new QuesBean("The performance of the cache memory is measured in terms of ?", "Hit Ratio", "Copy Ratio", "Data Ratio", "Hit Ratio"));
        quesBeans.add(new QuesBean("Program subroutines are", "called by other programs", "fixed variable", "default constants", "called by other programs"));
        quesBeans.add(new QuesBean("Loop statement which is repeated to some given number of times is classified as", "FOR loop", "GO loop", "REPEAT loop", "FOR loop"));
        quesBeans.add(new QuesBean("Size of an array is declared by", "programmer", "program users", "software", "programmer"));
        quesBeans.add(new QuesBean("An assembler translates", "machine code into assembly code", "assembly code into machine code", "processing time into manual time", "assembly code into machine code"));
        quesBeans.add(new QuesBean("IPSec is designed to provide the security at the", "transport layer", "network layer", "application layer", "network layer"));
        quesBeans.add(new QuesBean("How many digits of the DNIC (Data Network Identification Code) identify the country?", "first three", "first four", "first five", "first three"));
        quesBeans.add(new QuesBean("Frames from one LAN can be transmitted to another LAN via the device", "Router", "Bridge", "Modem", "Router"));
        quesBeans.add(new QuesBean("To avoid the race condition, the number of processes that may be simultaneously inside their critical section is", "8", "1", "0", "1"));
        quesBeans.add(new QuesBean("Process is:", "a program in execution", "contents of main memory", "a job in secondary memory", "a program in execution"));
        quesBeans.add(new QuesBean("The memory allocation scheme subject to external fragmentation is", "segmentation", "swapping", "paging", "segmentation"));
        quesBeans.add(new QuesBean("A quality objective for a software team is achieve in how many DRE approaches?", "2", "1", "3", "1"));
        quesBeans.add(new QuesBean("Which software works strictly according to defined specifications and solutions? ", "Static-type", "Embedded-type", "Practical-type", "Static-type"));
        quesBeans.add(new QuesBean("In the requirement analysis which model depicts the information domain for the problem?", "Data models", "Class-Oriented models", "Scenario-based models", "Data models"));
        quesBeans.add(new QuesBean("Which of the following types of channels moves data relatively slowly?", "wideband channel", "voiceband channel", "narrowband channel", "narrowband channel"));
        quesBeans.add(new QuesBean("Tower of hanoi is a classic example of", "divide and conquer", "recursive approach", "both of them", "both of them"));
        quesBeans.add(new QuesBean("Minimum number of spanning tree in a connected graph is", "n", "1", "2", "1"));
        quesBeans.add(new QuesBean("Which of the below mentioned sorting algorithms are not stable?", "selection sort", "merge sort", "bubble sort", "selection sort"));
        quesBeans.add(new QuesBean("Stack is used for", "CPU resource allocation ", "Recursion", "traversal", "Recursion"));
        quesBeans.add(new QuesBean("The _____ houses the switches in token ring.", "transceiver", "nine pin connector", "MAU", "MAU"));
        quesBeans.add(new QuesBean("Bug means", "A logical error in a program", "A difficult syntax error in a program", "both of them", "A logical error in a program"));
        quesBeans.add(new QuesBean("Line of code(LOC) can be used to normalize quality and/or productivity measure for _______ .", "Extended function point metrics", "Function point metrics", "Size oriented metrics", "Size oriented metrics"));
        quesBeans.add(new QuesBean("What is the level of metrics and indicators that are available to measure the process and quality?", "Optimized", "Quantitatively Managed", "Defined", "Quantitatively Managed"));
        quesBeans.add(new QuesBean("Travelling salesman problem is an example of", "Dynamic algorithm", "Greedy algorithm", "recursive approach", "Greedy algorithm"));
        quesBeans.add(new QuesBean("Graph traversal is different from a tree traversal, because", "Graphs may have loop", "trees have root", "trees are not connected", "trees have root"));
        quesBeans.add(new QuesBean("Heap is an example of", "complete binary tree", "spanning tree", "sparse tree", "complete binary tree"));
    }

    public List<QuesBean> getAllQuesBeans() {
        return quesBeans;
    }

}

