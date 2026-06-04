package com.studentportfolio.controller;

import com.studentportfolio.model.Resume;
import com.studentportfolio.service.ResumeService;

import java.util.List;

public class ResumeController {

    private ResumeService resumeService;

    public ResumeController() {
        resumeService = new ResumeService();
    }

    public boolean uploadResume(int userId,
                                String resumeName,
                                String filePath) {

        Resume resume = new Resume();

        resume.setUserId(userId);
        resume.setResumeName(resumeName);
        resume.setFilePath(filePath);

        return resumeService.addResume(resume);
    }

    public List<Resume> getResumes(int userId) {
        return resumeService.getResumesByUserId(userId);
    }
}