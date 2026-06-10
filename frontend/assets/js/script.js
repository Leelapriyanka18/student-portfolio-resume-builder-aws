document.addEventListener("DOMContentLoaded", () => {
  const navLinks = document.getElementById("navLinks");
  const menuToggle = document.getElementById("menuToggle");
  const themeToggle = document.getElementById("themeToggle");
  const currentPage = window.location.pathname.split("/").pop() || "index.html";

  document.querySelectorAll(".nav-links a").forEach((link) => {
    const href = link.getAttribute("href");
    if (
      href === currentPage ||
      href === `./${currentPage}` ||
      href === `../${currentPage}` ||
      (currentPage === "index.html" && href === "#home")
    ) {
      link.classList.add("active");
    }
  });

  if (menuToggle && navLinks) {
    menuToggle.addEventListener("click", () => {
      navLinks.classList.toggle("open");
    });
  }

  if (themeToggle) {
    const updateThemeIcon = () => {
      themeToggle.textContent = document.body.classList.contains("dark-theme")
        ? "☀️"
        : "🌙";
    };
    updateThemeIcon();

    themeToggle.addEventListener("click", () => {
      document.body.classList.toggle("dark-theme");
      updateThemeIcon();
    });
  }

  document.addEventListener("click", (event) => {
    if (
      navLinks &&
      menuToggle &&
      navLinks.classList.contains("open") &&
      !navLinks.contains(event.target) &&
      !menuToggle.contains(event.target)
    ) {
      navLinks.classList.remove("open");
    }
  });

  const resumeForm = document.getElementById("resumeForm");
  const previewContent = document.getElementById("previewContent");
  const downloadBtn = document.getElementById("downloadBtn");

  const getValue = (id, fallback) => {
    const element = document.getElementById(id);
    return element ? element.value.trim() || fallback : fallback;
  };

  const renderPreview = () => {
    if (!previewContent) return;

    const name = getValue("name", "Your Name");
    const email = getValue("email", "example@gmail.com");
    const phone = getValue("phone", "9876543210");
    const address = getValue("address", "Your Address");
    const role = getValue("role", "AI & DS Student");
    const summary = getValue("summary", "Profile Summary");
    const college = getValue("college", "BVC Engineering College");
    const degree = getValue("degree", "BTech AI & DS");
    const branch = getValue("branch", "AI & DS");
    const graduationYear = getValue("graduationYear", "2027");
    const cgpa = getValue("cgpa", "8.5");
    const skills = getValue("skillsInput", "HTML, CSS, JavaScript");
    const projects = getValue("projectsInput", "Portfolio Website");
    const certificates = getValue("certificatesInput", "AWS, IBM");
    const experience = getValue("experience", "Fresher");
    const github = getValue("github", "Not Provided");
    const linkedin = getValue("linkedin", "Not Provided");

    const projectDescription = getValue(
      "projectDescription",
      "Project Description",
    );

    const certificateDetails = getValue(
      "certificateDetails",
      "Certificate Details",
    );

    const languages = getValue("languages", "English, Telugu");

    const hobbies = getValue("hobbies", "Data Science, AI");

    previewContent.innerHTML = `
    <h3>Live Resume Preview</h3>

    <div class="preview-row"><strong>Name:</strong> ${name}</div>

    <div class="preview-row"><strong>Email:</strong> ${email}</div>

    <div class="preview-row"><strong>Phone:</strong> ${phone}</div>

    <div class="preview-row"><strong>Address:</strong> ${address}</div>

    <div class="preview-row"><strong>Role:</strong> ${role}</div>

    <div class="preview-row"><strong>Summary:</strong> ${summary}</div>

    <div class="preview-row"><strong>College:</strong> ${college}</div>

    <div class="preview-row"><strong>Degree:</strong> ${degree}</div>

    <div class="preview-row"><strong>Branch:</strong> ${branch}</div>

    <div class="preview-row"><strong>CGPA:</strong> ${cgpa}</div>

    <div class="preview-row"><strong>Graduation Year:</strong> ${graduationYear}</div>
    <div class="preview-row"><strong>Skills:</strong> ${skills}</div>
<div class="preview-row"><strong>Projects:</strong> ${projects}</div>

<div class="preview-row">
<strong>Project Description:</strong>
${projectDescription}
</div>

<div class="preview-row">
<strong>Certificates:</strong>
${certificates}
</div>

<div class="preview-row">
<strong>Certificate Details:</strong>
${certificateDetails}
</div>

<div class="preview-row">
<strong>Languages:</strong>
${languages}
</div>

<div class="preview-row">
<strong>Hobbies:</strong>
${hobbies}
</div>
<div class="preview-row">
<strong>Experience:</strong>
${experience}
</div>
<div class="preview-row">
<strong>GitHub:</strong> ${github}
</div>

<div class="preview-row">
<strong>LinkedIn:</strong> ${linkedin}
</div>

  `;
  };

  if (resumeForm) {
    resumeForm.addEventListener("submit", async (event) => {
      event.preventDefault();
      const name = getValue("name", "");
      const email = getValue("email", "");
      const phone = getValue("phone", "");
      if (name === "") {
        alert("Please enter your name");
        return;
      }
      if (email === "") {
        alert("Please enter your email");
        return;
      }

      if (!email.includes("@")) {
        alert("Please enter a valid email");
        return;
      }
      if (!/^\d{10}$/.test(phone)) {
        alert("Enter a valid 10-digit phone number");
        return;
      }
      renderPreview();

      const data = {
        userId: 1,
        resumeName: getValue("name", ""),
        email: getValue("email", ""),
        phone: getValue("phone", ""),
        address: getValue("address", ""),
        role: getValue("role", ""),
        summary: getValue("summary", ""),
        college: getValue("college", ""),
        degree: getValue("degree", ""),
        branch: getValue("branch", ""),
        graduationYear: getValue("graduationYear", ""),
        cgpa: getValue("cgpa", ""),
        skills: getValue("skillsInput", ""),
        projects: getValue("projectsInput", ""),
        projectDescription: getValue("projectDescription", ""),
        certificates: getValue("certificatesInput", ""),
        certificateDetails: getValue("certificateDetails", ""),
        experience: getValue("experience", ""),
        github: getValue("github", ""),
        linkedin: getValue("linkedin", ""),
        languages: getValue("languages", ""),
        hobbies: getValue("hobbies", ""),
        filePath: "resume.pdf",
      };
      try {
        const response = await fetch("http://localhost:8080/api/resume", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(data),
        });

        alert(await response.text());
      } catch (error) {
        alert("Unable to save resume");
      }
    });
  } // resumeForm closes here

  if (downloadBtn) {
    downloadBtn.addEventListener("click", () => {
      const { jsPDF } = window.jspdf;
      const doc = new jsPDF();

      const name = getValue("name", "");
      const email = getValue("email", "");
      const phone = getValue("phone", "");
      const address = getValue("address", "");
      const role = getValue("role", "");
      const summary = getValue("summary", "");
      const college = getValue("college", "");
      const degree = getValue("degree", "");
      const branch = getValue("branch", "");
      const graduationYear = getValue("graduationYear", "");
      const cgpa = getValue("cgpa", "");
      const skills = getValue("skillsInput", "");
      const projects = getValue("projectsInput", "");
      const certificates = getValue("certificatesInput", "");
      const projectDescription = getValue("projectDescription", "");

      const certificateDetails = getValue("certificateDetails", "");

      const languages = getValue("languages", "");

      const hobbies = getValue("hobbies", "");
      const experience = getValue("experience", "Fresher");
      const github = getValue("github", "Not Provided");
      const linkedin = getValue("linkedin", "Not Provided");

      const photoInput = document.getElementById("profilePhoto");
      const photoFile = photoInput.files[0];

      if (photoFile) {
        const reader = new FileReader();
        reader.onload = function (event) {
          const imgData = event.target.result;
          generatePdfContent(imgData);
        };
        reader.readAsDataURL(photoFile);

        return;
      }
      doc.setFont("helvetica");
      function generatePdfContent(imgData = null) {
        // ===== PAGE =====
        doc.setFillColor(249, 250, 251);
        doc.rect(0, 0, 210, 297, "F");

        // ===== SIDEBAR =====
        doc.setFillColor(31, 41, 55);
        doc.rect(0, 0, 60, 297, "F");

        // ===== PROFILE PHOTO =====
        if (imgData) {
          doc.addImage(imgData, "PNG", 12, 10, 35, 35);
        } else {
          doc.setDrawColor(255, 255, 255);
          doc.circle(30, 30, 15);
        }
        // ===== NAME =====
        doc.setTextColor(255, 255, 255);
        doc.setFontSize(18);

        doc.setFont(undefined, "bold");
        doc.setTextColor(255, 255, 255);
        doc.text(name || "Your Name", 30, 48, { align: "center" });
        doc.setFontSize(10);
        doc.setFont(undefined, "normal");
        doc.setFontSize(14);
        doc.setTextColor(220, 220, 220);
        doc.setFont(undefined, "normal");
        doc.text(role || "Data Scientist", 30, 58, { align: "center" });
        let leftY = 70;
        // ===== CONTACT =====
        doc.setFillColor(16, 185, 129);
        doc.rect(5, leftY, 50, 8, "F");

        doc.setTextColor(255, 255, 255);
        doc.setFontSize(13);
        doc.setFont(undefined, "bold");
        doc.text("CONTACT", 30, leftY + 6, {
          align: "center",
        });

        leftY += 18;
        doc.setFontSize(8);
        doc.setFont(undefined, "normal");

        const emailLines = doc.splitTextToSize(email, 45);
        doc.text(emailLines, 8, leftY);
        leftY += emailLines.length * 5;

        doc.text(phone, 8, leftY);
        leftY += 8;

        const addressLines = doc.splitTextToSize(address, 45);
        doc.text(addressLines, 8, leftY);
        leftY += addressLines.length * 5 + 10;
        // ===== SKILLS =====
        doc.setFillColor(16, 185, 129);
        doc.rect(5, leftY, 50, 8, "F");

        doc.setFontSize(13);
        doc.setFont(undefined, "bold");
        doc.text("SKILLS", 30, leftY + 6, {
          align: "center",
        });

        leftY += 18;

        doc.setFontSize(8);
        doc.setFont(undefined, "normal");

        skills.split(",").forEach((skill) => {
          doc.text("• " + skill.trim(), 8, leftY);
          leftY += 6;
        });
        leftY += 10;

        // ===== LANGUAGES =====
        doc.setFillColor(16, 185, 129);
        doc.rect(5, leftY, 50, 8, "F");

        doc.setFontSize(13);
        doc.setFont(undefined, "bold");
        doc.text("LANGUAGES", 30, leftY + 6, {
          align: "center",
        });

        leftY += 18;
        doc.setFontSize(8);
        doc.setFont(undefined, "normal");

        languages.split(",").forEach((language) => {
          doc.text("• " + language.trim(), 8, leftY);
          leftY += 6;
        });
        leftY += 10;
        // ===== HOBBIES =====
        doc.setFillColor(16, 185, 129);
        doc.rect(5, leftY, 50, 8, "F");

        doc.setFontSize(13);
        doc.setFont(undefined, "bold");
        doc.text("INTERESTS", 30, leftY + 6, {
          align: "center",
        });

        leftY += 18;

        doc.setFontSize(8);
        doc.setFont(undefined, "normal");

        hobbies.split(",").forEach((hobby) => {
          doc.text("• " + hobby.trim(), 8, leftY);
          leftY += 6;
        });
        leftY += 10;
        // ===========================
        // RIGHT SIDE
        // ===========================

        let y = 20;

        // ABOUT ME
        doc.setFillColor(16, 185, 129);
        doc.rect(70, y, 120, 8, "F");

        doc.setTextColor(255, 255, 255);
        doc.setFontSize(13);
        doc.setFont(undefined, "bold");
        doc.text("ABOUT ME", 130, y + 6, { align: "center" });

        y += 15;

        doc.setTextColor(0, 0, 0);
        doc.setFontSize(9);
        doc.setFont(undefined, "normal");

        const summaryLines = doc.splitTextToSize(summary, 105);
        doc.text(summaryLines, 72, y);

        y += summaryLines.length * 5 + 10;

        // EDUCATION
        doc.setFillColor(16, 185, 129);
        doc.rect(70, y, 120, 8, "F");

        doc.setTextColor(255, 255, 255);
        doc.setFontSize(13);
        doc.setFont(undefined, "bold");
        doc.text("EDUCATION", 130, y + 6, { align: "center" });

        y += 15;

        doc.setTextColor(0, 0, 0);
        doc.setFontSize(9);
        doc.setFont(undefined, "normal");
        doc.text(`${degree} - ${branch}`, 70, y);
        y += 5;

        doc.text(`${college}`, 70, y);
        y += 5;

        doc.text(`CGPA : ${cgpa}`, 70, y);
        y += 5;

        doc.text(`Graduation Year : ${graduationYear}`, 70, y);

        y += 12;

        // PROJECTS
        doc.setFillColor(16, 185, 129);
        doc.rect(70, y, 120, 8, "F");

        doc.setTextColor(255, 255, 255);
        doc.setFontSize(13);
        doc.setFont(undefined, "bold");
        doc.text("PROJECTS", 130, y + 6, { align: "center" });

        y += 10;
        projects.split(",").forEach((project) => {
          doc.setFont(undefined, "bold");
          doc.text("• " + project.trim(), 72, y);
          y += 7;
        });
        doc.setFont(undefined, "normal");
        doc.setTextColor(0, 0, 0);
        doc.setFontSize(8);

        
        const projectDescLines = doc.splitTextToSize(projectDescription, 105);
        doc.text(projectDescLines, 72, y);

        y += projectDescLines.length * 5 + 10;
        if (y > 250) {
          doc.addPage();
          y = 20;
        }

        // CERTIFICATIONS
        doc.setFillColor(16, 185, 129);
        doc.rect(70, y, 120, 8, "F");

        doc.setTextColor(255, 255, 255);
        doc.setFontSize(13);
        doc.setFont(undefined, "bold");
        doc.text("CERTIFICATIONS", 130, y + 6, { align: "center" });

        y += 10;
        certificates.split(",").forEach((cert) => {
          doc.text("• " + cert.trim(), 72, y);
          y += 7;
        });
        doc.setFont(undefined, "normal");

        doc.setTextColor(0, 0, 0);
        doc.setFontSize(8);

      

        const certDetailLines = doc.splitTextToSize(certificateDetails, 105);

        doc.text(certDetailLines, 72, y);
        y += certDetailLines.length * 5 + 10;
        if (y > 250) {
          doc.addPage();
          y = 20;
        }

        // EXPERIENCE

        doc.setFillColor(16, 185, 129);
        doc.rect(70, y, 120, 8, "F");

        doc.setTextColor(255, 255, 255);
        doc.setFontSize(13);
        doc.setFont(undefined, "bold");
        doc.text("EXPERIENCE", 130, y + 6, {
          align: "center",
        });

        y += 10;


        doc.setTextColor(0, 0, 0);
        doc.setFontSize(8);
        doc.setFont(undefined, "normal");

        const expLines = doc.splitTextToSize(experience, 105);

        doc.text(expLines, 72, y);

        y += expLines.length * 5 + 10;
        y += 5;


        if (y > 250) {
          doc.addPage();
          y = 20;
        }
        doc.setFillColor(16, 185, 129);
        doc.rect(70, y, 120, 8, "F");

        doc.setTextColor(255, 255, 255);
        doc.setFontSize(13);
        doc.setFont(undefined, "bold");
       
        doc.text("PROFESSIONAL PROFILES", 130, y + 6, {
          align: "center",
        });

        y += 12;

        doc.setTextColor(0, 0, 0);
        doc.setFontSize(9);

        doc.text("GitHub:", 70, y);
        const githubLines = doc.splitTextToSize(github, 90);
        doc.text(githubLines, 95, y);

        y += githubLines.length * 5;

        y += 7;

        doc.text("LinkedIn:", 70, y);

        const linkedinLines = doc.splitTextToSize(linkedin, 90);
        doc.text(linkedinLines, 95, y);
        y += 10;

        doc.save((name || "Resume") + "_Resume.pdf");
      }
      if (!photoFile) {
        generatePdfContent();
      }
    });
  } // downloadBtn
}); // DOMContentLoaded
