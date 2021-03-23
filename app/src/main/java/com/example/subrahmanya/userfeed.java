package com.example.subrahmanya;

public class userfeed {
    public String fname,frating,feedback;

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFrating() {
        return frating;
    }

    public void setFrating(String frating) {
        this.frating = frating;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public userfeed(String fname, String frating, String feedback) {
        this.fname = fname;
        this.frating = frating;
        this.feedback = feedback;
    }
    userfeed()
    {}

}
