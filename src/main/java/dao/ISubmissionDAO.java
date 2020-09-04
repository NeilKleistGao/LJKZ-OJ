package dao;

import entity.Submission;

import java.util.List;

public interface ISubmissionDAO {
    public void insertSubmission(Submission submission);
    public void updateState(Submission submission);
    public List<Submission> getSubmissionList(int start, int size);
}
