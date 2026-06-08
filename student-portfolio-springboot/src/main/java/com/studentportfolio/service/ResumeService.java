package com.studentportfolio.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.studentportfolio.dao.ResumeDAO;
import com.studentportfolio.dto.ResumeRequest;
import com.studentportfolio.model.Resume;

@Service
public class ResumeService {

    private final ResumeDAO resumeDAO;

    public ResumeService(ResumeDAO resumeDAO) {
        this.resumeDAO = resumeDAO;
    }

    @Transactional
    public void saveResume(ResumeRequest request) {

        Resume resume = new Resume();

        // Temporary user id
        resume.setUserId(1);

        resume.setResumeName(request.getResumeName().trim());
        resume.setFilePath(request.getFilePath());
        resume.setEmail(request.getEmail());
        resume.setPhone(request.getPhone());
        resume.setAddress(request.getAddress());
        resume.setRole(request.getRole());
        resume.setSummary(request.getSummary());
        resume.setCollege(request.getCollege());
        resume.setDegree(request.getDegree());
        resume.setBranch(request.getBranch());
        resume.setGraduationYear(request.getGraduationYear());
        resume.setCgpa(request.getCgpa());
        resume.setSkills(request.getSkills());
        resume.setProjects(request.getProjects());
        resume.setProjectDescription(request.getProjectDescription());
        resume.setCertificates(request.getCertificates());
        resume.setCertificateDetails(request.getCertificateDetails());
        resume.setLanguages(request.getLanguages());
        resume.setHobbies(request.getHobbies());

        boolean saved = resumeDAO.saveResume(resume);

        if (!saved) {
            throw new IllegalStateException("Unable to save resume");
        }
    }

    public List<Resume> getAllResumes() {
        return resumeDAO.getAllResumes();
    }
}