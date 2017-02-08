package com.techeducation.tech_reveal;
/**
 * Created by Mitaly on 12/10/2016.
 */
public class QuesBean {
    private String ques;
    private String Opt1;
    private String Opt2;
    private String Opt3;
    private String answer;

    public QuesBean(String question, String opt1, String opt2, String opt3,String answ) {
        ques = question;
        Opt1 = opt1;
        Opt2 = opt2;
        Opt3 = opt3;
        answer = answ;
    }

    public String getQues() {
        return ques;
    }

    public String getOpt1() {
        return Opt1;
    }

    public String getOpt2() {
        return Opt2;
    }

    public String getOpt3() {
        return Opt3;
    }

    public String getAnswer() {
        return answer;
    }

}
