package com.can.easyquiz.viewmodel.student.dashboard;

import java.util.List;

public class IndexVM {
    private List<PaperInfo> fixedPaper;
    private List<PaperInfoVM> timeLimitPaper;

    public List<PaperInfo> getFixedPaper() {
        return fixedPaper;
    }

    public void setFixedPaper(List<PaperInfo> fixedPaper) {
        this.fixedPaper = fixedPaper;
    }

    public List<PaperInfoVM> getTimeLimitPaper() {
        return timeLimitPaper;
    }

    public void setTimeLimitPaper(List<PaperInfoVM> timeLimitPaper) {
        this.timeLimitPaper = timeLimitPaper;
    }
}
