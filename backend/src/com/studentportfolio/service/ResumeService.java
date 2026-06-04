package com.studentportfolio.service;

import com.studentportfolio.dao.ResumeDAO;
import com.studentportfolio.model.Resume;

import java.util.List;

public class ResumeService {

    private ResumeDAO resumeDAO;

    public ResumeService() {
        resumeDAO = new ResumeDAO();
    }

    public boolean addResume(Resume resume) {
        return resumeDAO.addResume(resume);
    }

    public List<Resume> getResumesByUserId(int userId) {
        return resumeDAO.getResumesByUserId(userId);
    }
}