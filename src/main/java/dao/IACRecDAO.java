package dao;

import entity.ACRec;

import java.util.List;

public interface IACRecDAO {
    public void addACRec(ACRec rec);
    public List<String> getACRecByEmail(ACRec rec);
    public List<String> getACRecByCompetition(ACRec rec);
}
