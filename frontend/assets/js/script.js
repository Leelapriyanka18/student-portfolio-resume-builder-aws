document.addEventListener("DOMContentLoaded", () => {
  // ===== NAV =====
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

  // ===== RESUME FORM =====
  const resumeForm = document.getElementById("resumeForm");
  const previewContent = document.getElementById("previewContent");
  const downloadBtn = document.getElementById("downloadBtn");

  const getValue = (id, fallback = "") => {
    const element = document.getElementById(id);
    return element ? element.value.trim() || fallback : fallback;
  };

  // ===== LIVE PREVIEW =====
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
    const projectDescription = getValue("projectDescription", "Project Description");
    const certificateDetails = getValue("certificateDetails", "Certificate Details");
    const languages = getValue("languages", "English, Telugu");
    const hobbies = getValue("hobbies", "Data Science, AI");

    previewContent.innerHTML = `
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
      <div class="preview-row"><strong>Project Description:</strong> ${projectDescription}</div>
      <div class="preview-row"><strong>Certificates:</strong> ${certificates}</div>
      <div class="preview-row"><strong>Certificate Details:</strong> ${certificateDetails}</div>
      <div class="preview-row"><strong>Languages:</strong> ${languages}</div>
      <div class="preview-row"><strong>Hobbies:</strong> ${hobbies}</div>
      <div class="preview-row"><strong>Experience:</strong> ${experience}</div>
      <div class="preview-row"><strong>GitHub:</strong> ${github}</div>
      <div class="preview-row"><strong>LinkedIn:</strong> ${linkedin}</div>
    `;
  };

  // ===== FORM SUBMIT → SPRING BOOT API =====
  if (resumeForm) {
    resumeForm.addEventListener("submit", async (event) => {
      event.preventDefault();

      const name = getValue("name", "");
      const email = getValue("email", "");
      const phone = getValue("phone", "");

      if (name === "") { alert("Please enter your name"); return; }
      if (email === "") { alert("Please enter your email"); return; }
      if (!email.includes("@")) { alert("Please enter a valid email"); return; }
      if (!/^\d{10}$/.test(phone)) { alert("Enter a valid 10-digit phone number"); return; }

      renderPreview();

      const data = {
        userId: 1,
        resumeName: getValue("name"),
        email: getValue("email"),
        phone: getValue("phone"),
        address: getValue("address"),
        role: getValue("role"),
        summary: getValue("summary"),
        college: getValue("college"),
        degree: getValue("degree"),
        branch: getValue("branch"),
        graduationYear: getValue("graduationYear"),
        cgpa: getValue("cgpa"),
        skills: getValue("skillsInput"),
        projects: getValue("projectsInput"),
        projectDescription: getValue("projectDescription"),
        certificates: getValue("certificatesInput"),
        certificateDetails: getValue("certificateDetails"),
        experience: getValue("experience"),
        github: getValue("github"),
        linkedin: getValue("linkedin"),
        languages: getValue("languages"),
        hobbies: getValue("hobbies"),
        filePath: "resume.pdf",
      };

      try {
        const response = await fetch("http://localhost:8080/api/resume", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(data),
        });
        alert(await response.text());
      } catch (error) {
        alert("Unable to save resume");
      }
    });
  }

  // ===== DOWNLOAD PDF =====
  if (downloadBtn) {
    downloadBtn.addEventListener("click", () => {
      const { jsPDF } = window.jspdf;

      // Collect all field values
      const name             = getValue("name", "Your Name");
      const email            = getValue("email", "");
      const phone            = getValue("phone", "");
      const address          = getValue("address", "");
      const role             = getValue("role", "");
      const summary          = getValue("summary", "");
      const college          = getValue("college", "");
      const degree           = getValue("degree", "");
      const branch           = getValue("branch", "");
      const graduationYear   = getValue("graduationYear", "");
      const cgpa             = getValue("cgpa", "");
      const skills           = getValue("skillsInput", "");
      const projects         = getValue("projectsInput", "");
      const projectDesc      = getValue("projectDescription", "");
      const certificates     = getValue("certificatesInput", "");
      const certDetails      = getValue("certificateDetails", "");
      const experience       = getValue("experience", "");
      const github           = getValue("github", "");
      const linkedin         = getValue("linkedin", "");
      const languages        = getValue("languages", "");
      const hobbies          = getValue("hobbies", "");

      const photoInput = document.getElementById("profilePhoto");
      const photoFile  = photoInput ? photoInput.files[0] : null;

      // If photo selected, read it first then generate PDF
      if (photoFile) {
        const reader = new FileReader();
        reader.onload = (e) => generatePDF(e.target.result);
        reader.readAsDataURL(photoFile);
      } else {
        generatePDF(null);
      }

      // ─────────────────────────────────────────────
      // MAIN PDF GENERATION FUNCTION
      // ─────────────────────────────────────────────
      function generatePDF(imgData) {
        const doc = new jsPDF({ unit: "mm", format: "a4" });

        // ── Layout constants ──
        const PAGE_W  = 210;
        const PAGE_H  = 297;
        const SB_W    = 62;   // sidebar width
        const MAIN_X  = SB_W + 8;
        const MAIN_W  = PAGE_W - MAIN_X - 10;
        const MARGIN_B = 15;

        // ── Colors ──
        const ACCENT   = [16, 185, 129];   // teal/green
        const DARK     = [31, 41, 55];     // dark sidebar bg
        const WHITE    = [255, 255, 255];
        const BLACK    = [17, 17, 17];
        const GRAY     = [90, 90, 90];
        const LIGHT_BG = [245, 252, 249];  // very light green tint for right bg

        // ─────────────────────────────────────────────
        // HELPER FUNCTIONS
        // ─────────────────────────────────────────────

        /**
         * Check if there is enough space on the right column.
         * If not, add a new page and reset rightY.
         */
        function checkPageBreak(currentY, needed) {
          if (currentY + needed > PAGE_H - MARGIN_B) {
            doc.addPage();
            // Redraw sidebar and right background on new page
            drawPageBackground();
            return MAIN_X > 0 ? 15 : 15;
          }
          return currentY;
        }

        /**
         * Draw the sidebar background and right-side background.
         * Called once per page.
         */
        function drawPageBackground() {
          // Sidebar dark background
          doc.setFillColor(...DARK);
          doc.rect(0, 0, SB_W, PAGE_H, "F");

          // Right side light background
          doc.setFillColor(...LIGHT_BG);
          doc.rect(SB_W, 0, PAGE_W - SB_W, PAGE_H, "F");
        }

        /**
         * Draw a section heading banner on the LEFT sidebar.
         * @param {string} title - Section title text
         * @param {number} y     - Y position
         * @returns {number}     - New Y after the heading
         */
        function leftSectionHeading(title, y) {
          doc.setFillColor(...ACCENT);
          doc.rect(5, y, SB_W - 10, 7, "F");
          doc.setTextColor(...WHITE);
          doc.setFontSize(8.5);
          doc.setFont("helvetica", "bold");
          doc.text(title.toUpperCase(), SB_W / 2, y + 5, { align: "center" });
          return y + 12;
        }

        /**
         * Draw a section heading banner on the RIGHT main area.
         * @param {string} title - Section title text
         * @param {number} y     - Y position
         * @returns {number}     - New Y after the heading
         */
        function rightSectionHeading(title, y) {
          doc.setFillColor(...ACCENT);
          doc.rect(MAIN_X - 2, y, MAIN_W + 4, 7, "F");
          doc.setTextColor(...WHITE);
          doc.setFontSize(9);
          doc.setFont("helvetica", "bold");
          doc.text(title.toUpperCase(), MAIN_X + MAIN_W / 2, y + 5, { align: "center" });
          return y + 12;
        }

        /**
         * Wrap and print text, returning the new Y position.
         * Handles automatic page breaks.
         */
        function addWrappedText(text, x, y, maxWidth, fontSize, fontStyle, color, lineHeight) {
          doc.setFontSize(fontSize);
          doc.setFont("helvetica", fontStyle || "normal");
          doc.setTextColor(...(color || BLACK));
          const lines = doc.splitTextToSize(text, maxWidth);
          lines.forEach((line) => {
            y = checkPageBreak(y, lineHeight);
            doc.text(line, x, y);
            y += lineHeight;
          });
          return y;
        }

        /**
         * Print a bullet list, one item per line.
         * Returns new Y position.
         */
        function addBulletList(items, x, y, maxWidth, fontSize, lineHeight) {
          doc.setFontSize(fontSize);
          doc.setFont("helvetica", "normal");
          doc.setTextColor(...BLACK);
          items.forEach((item) => {
            y = checkPageBreak(y, lineHeight);
            doc.text("•  " + item.trim(), x, y);
            y += lineHeight;
          });
          return y;
        }

        // ─────────────────────────────────────────────
        // PAGE 1 BACKGROUND
        // ─────────────────────────────────────────────
        drawPageBackground();

        // ─────────────────────────────────────────────
        // LEFT SIDEBAR
        // ─────────────────────────────────────────────
        let leftY = 10;

        // Profile photo or circle placeholder
        if (imgData) {
          // Circular clip using ellipse trick — draw image in circle area
          doc.addImage(imgData, "JPEG", 13, leftY, 36, 36);
        } else {
          doc.setDrawColor(...ACCENT);
          doc.setLineWidth(1);
          doc.circle(SB_W / 2, leftY + 18, 16);
          doc.setTextColor(...ACCENT);
          doc.setFontSize(20);
          doc.text(name.charAt(0).toUpperCase(), SB_W / 2, leftY + 22, { align: "center" });
        }

        leftY += 40;

        // Name on sidebar
        doc.setTextColor(...WHITE);
        doc.setFontSize(12);
        doc.setFont("helvetica", "bold");
        const nameLines = doc.splitTextToSize(name, SB_W - 10);
        nameLines.forEach((line) => {
          doc.text(line, SB_W / 2, leftY, { align: "center" });
          leftY += 6;
        });

        // Role on sidebar
        doc.setFontSize(8);
        doc.setFont("helvetica", "normal");
        doc.setTextColor(...ACCENT);
        const roleLines = doc.splitTextToSize(role, SB_W - 10);
        roleLines.forEach((line) => {
          doc.text(line, SB_W / 2, leftY, { align: "center" });
          leftY += 5;
        });

        leftY += 6;

        // ── CONTACT ──
        leftY = leftSectionHeading("Contact", leftY);
        doc.setFontSize(7.5);
        doc.setFont("helvetica", "normal");
        doc.setTextColor(...WHITE);

        const contactItems = [
          { label: "Email",    value: email },
          { label: "Phone",    value: phone },
          { label: "Address",  value: address },
        ].filter((c) => c.value);

        contactItems.forEach((item) => {
          // Label in accent
          doc.setTextColor(...ACCENT);
          doc.setFont("helvetica", "bold");
          doc.text(item.label + ":", 8, leftY);
          leftY += 4;
          // Value in white, wrapped
          doc.setTextColor(...WHITE);
          doc.setFont("helvetica", "normal");
          const lines = doc.splitTextToSize(item.value, SB_W - 14);
          lines.forEach((l) => { doc.text(l, 8, leftY); leftY += 4; });
          leftY += 2;
        });

        leftY += 4;

        // ── SKILLS ──
        if (skills) {
          leftY = leftSectionHeading("Skills", leftY);
          doc.setFontSize(7.5);
          doc.setFont("helvetica", "normal");
          doc.setTextColor(...WHITE);
          skills.split(",").forEach((skill) => {
            if (skill.trim()) {
              doc.text("•  " + skill.trim(), 8, leftY);
              leftY += 5.5;
            }
          });
          leftY += 4;
        }

        // ── LANGUAGES ──
        if (languages) {
          leftY = leftSectionHeading("Languages", leftY);
          doc.setFontSize(7.5);
          doc.setFont("helvetica", "normal");
          doc.setTextColor(...WHITE);
          languages.split(",").forEach((lang) => {
            if (lang.trim()) {
              doc.text("•  " + lang.trim(), 8, leftY);
              leftY += 5.5;
            }
          });
          leftY += 4;
        }

        // ── INTERESTS ──
        if (hobbies) {
          leftY = leftSectionHeading("Interests", leftY);
          doc.setFontSize(7.5);
          doc.setFont("helvetica", "normal");
          doc.setTextColor(...WHITE);
          hobbies.split(",").forEach((h) => {
            if (h.trim()) {
              doc.text("•  " + h.trim(), 8, leftY);
              leftY += 5.5;
            }
          });
          leftY += 4;
        }

        // GitHub / LinkedIn on sidebar bottom
        if (github || linkedin) {
          leftY = leftSectionHeading("Profiles", leftY);
          doc.setFontSize(7);
          doc.setFont("helvetica", "normal");
          if (github) {
            doc.setTextColor(...ACCENT);
            doc.setFont("helvetica", "bold");
            doc.text("GitHub:", 8, leftY);
            leftY += 4;
            doc.setTextColor(...WHITE);
            doc.setFont("helvetica", "normal");
            const ghLines = doc.splitTextToSize(github, SB_W - 14);
            ghLines.forEach((l) => { doc.text(l, 8, leftY); leftY += 4; });
            leftY += 2;
          }
          if (linkedin) {
            doc.setTextColor(...ACCENT);
            doc.setFont("helvetica", "bold");
            doc.text("LinkedIn:", 8, leftY);
            leftY += 4;
            doc.setTextColor(...WHITE);
            doc.setFont("helvetica", "normal");
            const liLines = doc.splitTextToSize(linkedin, SB_W - 14);
            liLines.forEach((l) => { doc.text(l, 8, leftY); leftY += 4; });
          }
        }

        // ─────────────────────────────────────────────
        // RIGHT MAIN CONTENT
        // ─────────────────────────────────────────────
        let y = 15;

        // ── ABOUT ME ──
        if (summary) {
          y = checkPageBreak(y, 20);
          y = rightSectionHeading("About Me", y);
          y = addWrappedText(summary, MAIN_X, y, MAIN_W, 9, "normal", BLACK, 5);
          y += 5;
        }

        // ── EDUCATION ──
        if (college || degree || branch || graduationYear || cgpa) {
          y = checkPageBreak(y, 25);
          y = rightSectionHeading("Education", y);

          if (degree || branch) {
            doc.setFontSize(9.5);
            doc.setFont("helvetica", "bold");
            doc.setTextColor(...BLACK);
            const degLine = [degree, branch].filter(Boolean).join(" — ");
            y = addWrappedText(degLine, MAIN_X, y, MAIN_W, 9.5, "bold", BLACK, 5);
          }
          if (college) {
            y = addWrappedText(college, MAIN_X, y, MAIN_W, 9, "normal", GRAY, 5);
          }
          const eduMeta = [
            cgpa           ? "CGPA: " + cgpa : "",
            graduationYear ? "Graduation: " + graduationYear : "",
          ].filter(Boolean).join("   |   ");
          if (eduMeta) {
            y = addWrappedText(eduMeta, MAIN_X, y, MAIN_W, 9, "normal", GRAY, 5);
          }
          y += 5;
        }
        // ── PROJECTS ──
        if (projects) {
          y = checkPageBreak(y, 20);
          y = rightSectionHeading("Projects", y);

          const projectList = projects.split(",").map((p) => p.trim()).filter(Boolean);
          const projectDescList = projectDesc
            ? projectDesc.split("\n").map((d) => d.trim()).filter(Boolean)
            : [];

          projectList.forEach((proj, idx) => {
            y = checkPageBreak(y, 14);

            // Project title with accent left border highlight
            doc.setFillColor(232, 250, 244);
            doc.rect(MAIN_X - 2, y - 4, MAIN_W + 4, 6, "F");
            doc.setFillColor(...ACCENT);
            doc.rect(MAIN_X - 2, y - 4, 2, 6, "F");

            doc.setFontSize(9.5);
            doc.setFont("helvetica", "bold");
            doc.setTextColor(...BLACK);
            doc.text("  " + proj, MAIN_X, y);
            y += 6;

            // Each project gets its own description line if available
            // projectDescList[idx] matches description to project by index
            const thisDesc = projectDescList[idx] || "";
            if (thisDesc) {
              y = addWrappedText(thisDesc, MAIN_X + 3, y, MAIN_W - 3, 8.5, "normal", GRAY, 4.5);
            }
            y += 4;
          });
          y += 3;
        }

       
        // ── CERTIFICATIONS ──
        if (certificates) {
          y = checkPageBreak(y, 20);
          y = rightSectionHeading("Certifications", y);

          const certList = certificates.split(",").map((c) => c.trim()).filter(Boolean);
          const certDetailsList = certDetails
            ? certDetails.split("\n").map((d) => d.trim()).filter(Boolean)
            : [];

          certList.forEach((cert, idx) => {
            y = checkPageBreak(y, 14);

            // Certificate name in bold with tick
            doc.setFontSize(9.5);
            doc.setFont("helvetica", "bold");
            doc.setTextColor(...ACCENT);
            doc.text("✓", MAIN_X, y);
            doc.setTextColor(...BLACK);
            const certLines = doc.splitTextToSize(cert, MAIN_W - 6);
            certLines.forEach((cl) => {
              doc.text(cl, MAIN_X + 5, y);
              y += 5.5;
            });

            // Certificate detail matched by index — printed below its own cert name
            const thisDetail = certDetailsList[idx] || "";
            if (thisDetail) {
              y = addWrappedText(thisDetail, MAIN_X + 5, y, MAIN_W - 5, 8.5, "normal", GRAY, 4.5);
            }
            y += 4;
          });
          y += 3;
        }

        // ── EXPERIENCE ──
        if (experience) {
          y = checkPageBreak(y, 20);
          y = rightSectionHeading("Experience", y);
          y = addWrappedText(experience, MAIN_X, y, MAIN_W, 9, "normal", BLACK, 5);
          y += 5;
        }

        // ── PROFESSIONAL PROFILES (right side) ──
        if (github || linkedin) {
          y = checkPageBreak(y, 20);
          y = rightSectionHeading("Professional Profiles", y);

          if (github) {
            doc.setFontSize(9);
            doc.setFont("helvetica", "bold");
            doc.setTextColor(...BLACK);
            doc.text("GitHub:", MAIN_X, y);
            doc.setFont("helvetica", "normal");
            doc.setTextColor(...ACCENT);
            const ghLines = doc.splitTextToSize(github, MAIN_W - 22);
            doc.text(ghLines, MAIN_X + 20, y);
            y += ghLines.length * 5 + 3;
          }
          if (linkedin) {
            doc.setFontSize(9);
            doc.setFont("helvetica", "bold");
            doc.setTextColor(...BLACK);
            doc.text("LinkedIn:", MAIN_X, y);
            doc.setFont("helvetica", "normal");
            doc.setTextColor(...ACCENT);
            const liLines = doc.splitTextToSize(linkedin, MAIN_W - 22);
            doc.text(liLines, MAIN_X + 22, y);
            y += liLines.length * 5 + 3;
          }
        }

        // ─────────────────────────────────────────────
        // SAVE PDF
        // ─────────────────────────────────────────────
        doc.save((name || "Resume").replace(/\s+/g, "_") + "_Resume.pdf");
      } // end generatePDF
    }); // end downloadBtn click
  } // end if downloadBtn
}); // end DOMContentLoaded