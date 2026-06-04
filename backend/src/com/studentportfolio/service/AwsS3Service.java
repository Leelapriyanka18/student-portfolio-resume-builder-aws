package com.studentportfolio.service;

public class AwsS3Service {

    private static final String BUCKET_NAME = "student-portfolio-resume-bucket";

    public AwsS3Service() {
    }

    public String uploadFile(String filePath) {

        System.out.println("Uploading file to S3: " + filePath);

        // AWS S3 upload logic later add chestham

        return "File uploaded successfully";
    }

    public boolean deleteFile(String fileKey) {

        System.out.println("Deleting file from S3: " + fileKey);

        // AWS S3 delete logic later add chestham

        return true;
    }

    public String getBucketName() {
        return BUCKET_NAME;
    }
}