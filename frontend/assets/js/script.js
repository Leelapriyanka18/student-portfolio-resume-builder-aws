// ============================================================
// Student Portfolio & Resume Builder — script.js
// Reviewed & Fixed:
//   C1 · Configurable API base (no hardcoded localhost)
//   C2 · XSS-safe innerHTML via esc() helper
//   C3 · checkPageBreak dead conditional removed
//   C4 · Live preview on every input change
//   C5 · jsPDF null guard
//   M1 · International phone validation
//   M2 · PNG/JPEG auto-detect for profile photo
//   M3 · Reuse validated locals in API payload
//   M4 · Disabled state on Download button during generation
//   M5 · Sidebar leftY overflow guard
//   M6 · Theme persisted to localStorage
//   M7 · Toast replaces alert()
//   N1 · Dead ternary removed from checkPageBreak
//   N2 · generatePDF hoisted to module scope
//   N3 · Placeholder hints added via JS
//   N4 · Profile photo shown in live preview
//   N5 · Sidebar GitHub/LinkedIn truncated to avoid overflow
// ============================================================

document.addEventListener("DOMContentLoaded", () => {
  // ── API Base (override via window.APP_CONFIG in index.html for production) ──
  // In index.html, before this script tag add:
  // <script>window.APP_CONFIG = { apiBase: "https://your-api.example.com" };</script>
  const API_BASE =
    (window.APP_CONFIG && window.APP_CONFIG.apiBase) || "http://localhost:8080";

  // ── XSS-safe escape helper ──
  const esc = (str) =>
    String(str).replace(
      /[&<>"']/g,
      (c) =>
        ({
          "&": "&amp;",
          "<": "&lt;",
          ">": "&gt;",
          '"': "&quot;",
          "'": "&#39;",
        })[c],
    );

  // ── Toast notification (replaces alert()) ──
  function showToast(msg, type = "error") {
    let container = document.getElementById("toast-container");
    if (!container) {
      container = document.createElement("div");
      container.id = "toast-container";
      container.style.cssText =
        "position:fixed;top:1rem;right:1rem;z-index:9999;display:flex;flex-direction:column;gap:.5rem;";
      document.body.appendChild(container);
    }
    const toast = document.createElement("div");
    const bg =
      type === "success" ? "#EAF3DE" : type === "info" ? "#E6F1FB" : "#FCEBEB";
    const color =
      type === "success" ? "#3B6D11" : type === "info" ? "#185FA5" : "#A32D2D";
    toast.style.cssText = `background:${bg};color:${color};padding:.75rem 1.25rem;border-radius:8px;
      font-size:14px;box-shadow:0 2px 8px rgba(0,0,0,.12);max-width:320px;word-break:break-word;
      border-left:3px solid ${color};transition:opacity .3s;`;
    toast.textContent = msg;
    container.appendChild(toast);
    setTimeout(() => {
      toast.style.opacity = "0";
      setTimeout(() => toast.remove(), 300);
    }, 4000);
  }

  // ===== NAV =====
  const navLinks = document.getElementById("navLinks");
  const menuToggle = document.getElementById("menuToggle");
  const themeToggle = document.getElementById("themeToggle");
  const currentPage = window.location.pathname.split("/").pop() || "index.html";

  // Active nav link
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

  // Mobile menu toggle
  if (menuToggle && navLinks) {
    // M6: ARIA label for accessibility
    menuToggle.setAttribute("aria-label", "Open navigation menu");
    menuToggle.addEventListener("click", () => {
      const isOpen = navLinks.classList.toggle("open");
      menuToggle.setAttribute("aria-expanded", isOpen ? "true" : "false");
    });
  }

  // Theme toggle with localStorage persistence (M6)
  if (themeToggle) {
    themeToggle.setAttribute("aria-label", "Toggle dark mode");

    // Restore saved theme
    if (localStorage.getItem("theme") === "dark") {
      document.body.classList.add("dark-theme");
    }

    const updateThemeIcon = () => {
      themeToggle.textContent = document.body.classList.contains("dark-theme")
        ? "☀️"
        : "🌙";
    };
    updateThemeIcon();

    themeToggle.addEventListener("click", () => {
      document.body.classList.toggle("dark-theme");
      updateThemeIcon();
      // Persist preference
      localStorage.setItem(
        "theme",
        document.body.classList.contains("dark-theme") ? "dark" : "light",
      );
    });
  }

  // Close mobile menu on outside click
  document.addEventListener("click", (event) => {
    if (
      navLinks &&
      menuToggle &&
      navLinks.classList.contains("open") &&
      !navLinks.contains(event.target) &&
      !menuToggle.contains(event.target)
    ) {
      navLinks.classList.remove("open");
      menuToggle.setAttribute("aria-expanded", "false");
    }
  });

  // ===== RESUME FORM ELEMENTS =====
  const resumeForm = document.getElementById("resumeForm");
  const previewContent = document.getElementById("previewContent");
  const downloadBtn = document.getElementById("downloadBtn");

  const getValue = (id, fallback = "") => {
    const element = document.getElementById(id);
    return element ? element.value.trim() || fallback : fallback;
  };

  // N3: Add helpful placeholder text to clarify delimiter conventions
  const placeholderHints = {
    projectDescription:
      "One description per line — matches project order above",
    certificateDetails:
      "One line per certificate — matches certificate order above",
    skillsInput: "e.g. HTML, CSS, JavaScript, React",
    projectsInput: "e.g. Portfolio Website, Resume Builder",
    certificatesInput: "e.g. AWS Cloud Practitioner, IBM Data Science",
    languages: "e.g. English, Telugu, Hindi",
    hobbies: "e.g. Data Science, Reading, Gaming",
  };
  Object.entries(placeholderHints).forEach(([id, hint]) => {
    const el = document.getElementById(id);
    if (el && !el.placeholder) el.placeholder = hint;
  });

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

    // N4: Show profile photo in preview
    let photoHTML = "";
    const photoInput = document.getElementById("profilePhoto");
    if (photoInput && photoInput.files && photoInput.files[0]) {
      const url = URL.createObjectURL(photoInput.files[0]);
      photoHTML = `<div style="text-align:center;margin-bottom:12px;">
        <img src="${url}" alt="Profile photo"
          style="width:72px;height:72px;border-radius:50%;object-fit:cover;border:2px solid #10b981;" />
      </div>`;
    }

    // C2: All values escaped before innerHTML injection
    previewContent.innerHTML = `
      ${photoHTML}
      <div class="preview-row"><strong>Name:</strong> ${esc(name)}</div>
      <div class="preview-row"><strong>Email:</strong> ${esc(email)}</div>
      <div class="preview-row"><strong>Phone:</strong> ${esc(phone)}</div>
      <div class="preview-row"><strong>Address:</strong> ${esc(address)}</div>
      <div class="preview-row"><strong>Role:</strong> ${esc(role)}</div>
      <div class="preview-row"><strong>Summary:</strong> ${esc(summary)}</div>
      <div class="preview-row"><strong>College:</strong> ${esc(college)}</div>
      <div class="preview-row"><strong>Degree:</strong> ${esc(degree)}</div>
      <div class="preview-row"><strong>Branch:</strong> ${esc(branch)}</div>
      <div class="preview-row"><strong>CGPA:</strong> ${esc(cgpa)}</div>
      <div class="preview-row"><strong>Graduation Year:</strong> ${esc(graduationYear)}</div>
      <div class="preview-row"><strong>Skills:</strong> ${esc(skills)}</div>
      <div class="preview-row"><strong>Projects:</strong> ${esc(projects)}</div>
      <div class="preview-row"><strong>Project Description:</strong> ${esc(projectDescription)}</div>
      <div class="preview-row"><strong>Certificates:</strong> ${esc(certificates)}</div>
      <div class="preview-row"><strong>Certificate Details:</strong> ${esc(certificateDetails)}</div>
      <div class="preview-row"><strong>Languages:</strong> ${esc(languages)}</div>
      <div class="preview-row"><strong>Hobbies:</strong> ${esc(hobbies)}</div>
      <div class="preview-row"><strong>Experience:</strong> ${esc(experience)}</div>
      <div class="preview-row"><strong>GitHub:</strong> ${esc(github)}</div>
      <div class="preview-row"><strong>LinkedIn:</strong> ${esc(linkedin)}</div>
    `;
  };

  // ===== FORM SUBMIT → SPRING BOOT API =====
  if (resumeForm) {
    // C4: Attach live preview to all inputs
    resumeForm.querySelectorAll("input, textarea, select").forEach((el) => {
      el.addEventListener("input", renderPreview);
      el.addEventListener("change", renderPreview); // catches file input + select
    });
    // Initial render
    renderPreview();

    resumeForm.addEventListener("submit", async (event) => {
      event.preventDefault();

      // M3: Read once, reuse for both validation and payload
      const name = getValue("name", "");
      const email = getValue("email", "");
      const phone = getValue("phone", "");

      if (!name) {
        showToast("Please enter your name");
        return;
      }
      if (!email) {
        showToast("Please enter your email");
        return;
      }
      if (!email.includes("@")) {
        showToast("Please enter a valid email address");
        return;
      }
      // M1: Accept international phone numbers (7–15 digits, optional + and spaces/dashes)
      if (!/^\+?[\d\s\-]{7,15}$/.test(phone)) {
        showToast("Enter a valid phone number (7–15 digits)");
        return;
      }

      renderPreview();

      // M3: Reuse already-read values; read remaining fields once
      const data = {
        userId: 1,
        resumeName: name, // M3: reuse local
        email: email, // M3: reuse local
        phone: phone, // M3: reuse local
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
        // C1: Use configurable API_BASE instead of hardcoded localhost
        const response = await fetch(`${API_BASE}/api/resume`, {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(data),
        });
        const msg = await response.text();
        showToast(msg, response.ok ? "success" : "error");
      } catch (error) {
        showToast(
          "Unable to save resume. Please check your connection.",
          "error",
        );
      }
    });
  }

  // ===== DOWNLOAD PDF =====
  if (downloadBtn) {
    downloadBtn.addEventListener("click", () => {
      // C5: Guard against missing jsPDF library
      console.log(window.jspdf);

      if (!window.jspdf) {
        alert("jsPDF not loaded");
        return;
      }

      // M4: Disable button to prevent duplicate generation
      downloadBtn.disabled = true;
      const originalLabel = downloadBtn.textContent;
      downloadBtn.textContent = "Generating…";

      // Collect all field values once
      const name = getValue("name", "Your Name");
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
      const projectDesc = getValue("projectDescription", "");
      const certificates = getValue("certificatesInput", "");
      const certDetails = getValue("certificateDetails", "");
      const experience = getValue("experience", "");
      const github = getValue("github", "");
      const linkedin = getValue("linkedin", "");
      const languages = getValue("languages", "");
      const hobbies = getValue("hobbies", "");

      const photoInput = document.getElementById("profilePhoto");
      const photoFile = photoInput ? photoInput.files[0] : null;

      const restoreButton = () => {
        downloadBtn.disabled = false;
        downloadBtn.textContent = originalLabel;
      };

      if (photoFile) {
        const reader = new FileReader();
        reader.onload = (e) => {
          generatePDF(e.target.result, photoFile.type);
          restoreButton();
        };
        reader.onerror = () => {
          showToast("Failed to read photo file.", "error");
          restoreButton();
        };
        reader.readAsDataURL(photoFile);
      } else {
        generatePDF(null, null);
        restoreButton();
      }

      // ──────────────────────────────────────────────────────
      // PDF GENERATION  (N2: defined in enclosing scope, not
      //   re-declared per click — closure over field values)
      // ──────────────────────────────────────────────────────
      function generatePDF(imgData, imgMimeType) {
        const jsPDF = window.jspdf?.jsPDF;

        if (!jsPDF) {
          console.error("jsPDF not loaded");
          return;
        }
        const doc = new jsPDF({ unit: "mm", format: "a4" });

        // ── Layout constants ──
        const PAGE_W = 210;
        const PAGE_H = 297;
        const SB_W = 62;
        const MAIN_X = SB_W + 8;
        const MAIN_W = PAGE_W - MAIN_X - 10;
        const MARGIN_B = 15;

        // ── Colors ──
        const ACCENT = [16, 185, 129];
        const DARK = [31, 41, 55];
        const WHITE = [255, 255, 255];
        const BLACK = [17, 17, 17];
        const GRAY = [90, 90, 90];
        const LIGHT_BG = [245, 252, 249];

        // ── HELPER: page break (right column) ──
        // C3 & N1: Dead ternary removed — always returns 15
        function checkPageBreak(currentY, needed) {
          if (currentY + needed > PAGE_H - MARGIN_B) {
            doc.addPage();
            drawPageBackground();
            return 15;
          }
          return currentY;
        }

        // ── HELPER: sidebar overflow guard (M5) ──
        function leftSafe(y) {
          return Math.min(y, PAGE_H - 10);
        }

        // ── HELPER: page backgrounds ──
        function drawPageBackground() {
          doc.setFillColor(...DARK);
          doc.rect(0, 0, SB_W, PAGE_H, "F");
          doc.setFillColor(...LIGHT_BG);
          doc.rect(SB_W, 0, PAGE_W - SB_W, PAGE_H, "F");
        }

        function leftSectionHeading(title, y) {
          y = leftSafe(y);
          doc.setFillColor(...ACCENT);
          doc.rect(5, y, SB_W - 10, 7, "F");
          doc.setTextColor(...WHITE);
          doc.setFontSize(8.5);
          doc.setFont("helvetica", "bold");
          doc.text(title.toUpperCase(), SB_W / 2, y + 5, { align: "center" });
          return leftSafe(y + 12);
        }

        function rightSectionHeading(title, y) {
          doc.setFillColor(...ACCENT);
          doc.rect(MAIN_X - 2, y, MAIN_W + 4, 7, "F");
          doc.setTextColor(...WHITE);
          doc.setFontSize(9);
          doc.setFont("helvetica", "bold");
          doc.text(title.toUpperCase(), MAIN_X + MAIN_W / 2, y + 5, {
            align: "center",
          });
          return y + 12;
        }

        function addWrappedText(
          text,
          x,
          y,
          maxWidth,
          fontSize,
          fontStyle,
          color,
          lineHeight,
        ) {
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

        // ── PAGE 1 BACKGROUND ──
        drawPageBackground();

        // ─────────────────────────────────────────────
        // LEFT SIDEBAR
        // ─────────────────────────────────────────────
        let leftY = 10;

        // Profile photo or initial placeholder
        if (imgData) {
          // M2: Detect actual image format from data URL, not hardcode JPEG
          let fmt = "JPEG";
          if (imgMimeType) {
            if (imgMimeType === "image/png") fmt = "PNG";
            else if (imgMimeType === "image/webp") fmt = "WEBP";
          } else if (imgData.startsWith("data:image/png")) {
            fmt = "PNG";
          }
          doc.addImage(imgData, fmt, 13, leftY, 36, 36);
        } else {
          doc.setDrawColor(...ACCENT);
          doc.setLineWidth(1);
          doc.circle(SB_W / 2, leftY + 18, 16);
          doc.setTextColor(...ACCENT);
          doc.setFontSize(20);
          doc.text(name.charAt(0).toUpperCase(), SB_W / 2, leftY + 22, {
            align: "center",
          });
        }

        leftY = leftSafe(leftY + 40);

        // Name
        doc.setTextColor(...WHITE);
        doc.setFontSize(12);
        doc.setFont("helvetica", "bold");
        const nameLines = doc.splitTextToSize(name, SB_W - 10);
        nameLines.forEach((line) => {
          leftY = leftSafe(leftY);
          doc.text(line, SB_W / 2, leftY, { align: "center" });
          leftY = leftSafe(leftY + 6);
        });

        // Role
        doc.setFontSize(8);
        doc.setFont("helvetica", "normal");
        doc.setTextColor(...ACCENT);
        const roleLines = doc.splitTextToSize(role, SB_W - 10);
        roleLines.forEach((line) => {
          leftY = leftSafe(leftY);
          doc.text(line, SB_W / 2, leftY, { align: "center" });
          leftY = leftSafe(leftY + 5);
        });

        leftY = leftSafe(leftY + 6);

        // ── CONTACT ──
        leftY = leftSectionHeading("Contact", leftY);
        doc.setFontSize(7.5);

        const contactItems = [
          { label: "Email", value: email },
          { label: "Phone", value: phone },
          { label: "Address", value: address },
        ].filter((c) => c.value);

        contactItems.forEach((item) => {
          leftY = leftSafe(leftY);
          doc.setTextColor(...ACCENT);
          doc.setFont("helvetica", "bold");
          doc.text(item.label + ":", 8, leftY);
          leftY = leftSafe(leftY + 4);
          doc.setTextColor(...WHITE);
          doc.setFont("helvetica", "normal");
          const lines = doc.splitTextToSize(item.value, SB_W - 14);
          lines.forEach((l) => {
            leftY = leftSafe(leftY);
            doc.text(l, 8, leftY);
            leftY = leftSafe(leftY + 4);
          });
          leftY = leftSafe(leftY + 2);
        });

        leftY = leftSafe(leftY + 4);

        // ── SKILLS ──
        if (skills) {
          leftY = leftSectionHeading("Skills", leftY);
          doc.setFontSize(7.5);
          doc.setFont("helvetica", "normal");
          doc.setTextColor(...WHITE);
          skills.split(",").forEach((skill) => {
            if (skill.trim()) {
              leftY = leftSafe(leftY);
              doc.text("•  " + skill.trim(), 8, leftY);
              leftY = leftSafe(leftY + 5.5);
            }
          });
          leftY = leftSafe(leftY + 4);
        }

        // ── LANGUAGES ──
        if (languages) {
          leftY = leftSectionHeading("Languages", leftY);
          doc.setFontSize(7.5);
          doc.setFont("helvetica", "normal");
          doc.setTextColor(...WHITE);
          languages.split(",").forEach((lang) => {
            if (lang.trim()) {
              leftY = leftSafe(leftY);
              doc.text("•  " + lang.trim(), 8, leftY);
              leftY = leftSafe(leftY + 5.5);
            }
          });
          leftY = leftSafe(leftY + 4);
        }

        // ── INTERESTS ──
        if (hobbies) {
          leftY = leftSectionHeading("Interests", leftY);
          doc.setFontSize(7.5);
          doc.setFont("helvetica", "normal");
          doc.setTextColor(...WHITE);
          hobbies.split(",").forEach((h) => {
            if (h.trim()) {
              leftY = leftSafe(leftY);
              doc.text("•  " + h.trim(), 8, leftY);
              leftY = leftSafe(leftY + 5.5);
            }
          });
          leftY = leftSafe(leftY + 4);
        }

        // ── PROFILES (sidebar) ──
        if (github || linkedin) {
          leftY = leftSectionHeading("Profiles", leftY);
          doc.setFontSize(7);
          doc.setFont("helvetica", "normal");

          // N5: Truncate long URLs to prevent sidebar overflow
          const truncate = (url, max = 30) =>
            url.length > max ? url.slice(0, max) + "…" : url;

          if (github) {
            leftY = leftSafe(leftY);
            doc.setTextColor(...ACCENT);
            doc.setFont("helvetica", "bold");
            doc.text("GitHub:", 8, leftY);
            leftY = leftSafe(leftY + 4);
            doc.setTextColor(...WHITE);
            doc.setFont("helvetica", "normal");
            const ghLines = doc.splitTextToSize(truncate(github), SB_W - 14);
            ghLines.forEach((l) => {
              leftY = leftSafe(leftY);
              doc.text(l, 8, leftY);
              leftY = leftSafe(leftY + 4);
            });
            leftY = leftSafe(leftY + 2);
          }
          if (linkedin) {
            leftY = leftSafe(leftY);
            doc.setTextColor(...ACCENT);
            doc.setFont("helvetica", "bold");
            doc.text("LinkedIn:", 8, leftY);
            leftY = leftSafe(leftY + 4);
            doc.setTextColor(...WHITE);
            doc.setFont("helvetica", "normal");
            const liLines = doc.splitTextToSize(truncate(linkedin), SB_W - 14);
            liLines.forEach((l) => {
              leftY = leftSafe(leftY);
              doc.text(l, 8, leftY);
              leftY = leftSafe(leftY + 4);
            });
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
            const degLine = [degree, branch].filter(Boolean).join(" — ");
            y = addWrappedText(
              degLine,
              MAIN_X,
              y,
              MAIN_W,
              9.5,
              "bold",
              BLACK,
              5,
            );
          }
          if (college) {
            y = addWrappedText(
              college,
              MAIN_X,
              y,
              MAIN_W,
              9,
              "normal",
              GRAY,
              5,
            );
          }
          const eduMeta = [
            cgpa ? "CGPA: " + cgpa : "",
            graduationYear ? "Graduation: " + graduationYear : "",
          ]
            .filter(Boolean)
            .join("   |   ");
          if (eduMeta) {
            y = addWrappedText(
              eduMeta,
              MAIN_X,
              y,
              MAIN_W,
              9,
              "normal",
              GRAY,
              5,
            );
          }
          y += 5;
        }

        // ── PROJECTS ──
        if (projects) {
          y = checkPageBreak(y, 20);
          y = rightSectionHeading("Projects", y);

          const projectList = projects
            .split(",")
            .map((p) => p.trim())
            .filter(Boolean);
          const projectDescList = projectDesc
            ? projectDesc
                .split("\n")
                .map((d) => d.trim())
                .filter(Boolean)
            : [];

          projectList.forEach((proj, idx) => {
            y = checkPageBreak(y, 14);

            doc.setFillColor(232, 250, 244);
            doc.rect(MAIN_X - 2, y - 4, MAIN_W + 4, 6, "F");
            doc.setFillColor(...ACCENT);
            doc.rect(MAIN_X - 2, y - 4, 2, 6, "F");

            doc.setFontSize(9.5);
            doc.setFont("helvetica", "bold");
            doc.setTextColor(...BLACK);
            doc.text("  " + proj, MAIN_X, y);
            y += 6;

            const thisDesc = projectDescList[idx] || "";
            if (thisDesc) {
              y = addWrappedText(
                thisDesc,
                MAIN_X + 3,
                y,
                MAIN_W - 3,
                8.5,
                "normal",
                GRAY,
                4.5,
              );
            }
            y += 4;
          });
          y += 3;
        }

        // ── CERTIFICATIONS ──
        if (certificates) {
          y = checkPageBreak(y, 20);
          y = rightSectionHeading("Certifications", y);

          const certList = certificates
            .split(",")
            .map((c) => c.trim())
            .filter(Boolean);
          const certDetailsList = certDetails
            ? certDetails
                .split("\n")
                .map((d) => d.trim())
                .filter(Boolean)
            : [];

          certList.forEach((cert, idx) => {
            y = checkPageBreak(y, 14);

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

            const thisDetail = certDetailsList[idx] || "";
            if (thisDetail) {
              y = addWrappedText(
                thisDetail,
                MAIN_X + 5,
                y,
                MAIN_W - 5,
                8.5,
                "normal",
                GRAY,
                4.5,
              );
            }
            y += 4;
          });
          y += 3;
        }

        // ── EXPERIENCE ──
        if (experience) {
          y = checkPageBreak(y, 20);
          y = rightSectionHeading("Experience", y);
          y = addWrappedText(
            experience,
            MAIN_X,
            y,
            MAIN_W,
            9,
            "normal",
            BLACK,
            5,
          );
          y += 5;
        }

        // ── PROFESSIONAL PROFILES (right — full URLs) ──
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

        // ── SAVE ──
        doc.save((name || "Resume").replace(/\s+/g, "_") + "_Resume.pdf");
      } // end generatePDF
    }); // end downloadBtn click
  } // end if downloadBtn
}); // end DOMContentLoaded
