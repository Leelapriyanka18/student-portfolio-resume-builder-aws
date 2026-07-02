// ============================================================
// Student Portfolio & Resume Builder — script.js
// ============================================================

document.addEventListener("DOMContentLoaded", () => {

  // ── API Base ──
  // To use a real backend, set this before this script tag in index.html:
  // <script>window.APP_CONFIG = { apiBase: "https://your-api.example.com" };</script>
  const API_BASE = window.getApiBase ? window.getApiBase() : window.API_BASE;
  // ── XSS-safe escape helper ──
  const esc = (str) =>
    String(str ?? "").replace(/[&<>"']/g, (c) => ({
      "&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;", "'": "&#39;",
    })[c]);

  // ────────────────────────────────────────────────────────
  // TOAST NOTIFICATIONS  (replaces alert())
  // ────────────────────────────────────────────────────────
  const showToast = window.showToast;

  // ────────────────────────────────────────────────────────
  // NAVIGATION
  // ────────────────────────────────────────────────────────
  const navLinks   = document.getElementById("navLinks");
  const menuToggle = document.getElementById("menuToggle");
  const themeToggle = document.getElementById("themeToggle");

  // Mobile menu toggle
  if (menuToggle && navLinks) {
    menuToggle.addEventListener("click", () => {
      const isOpen = navLinks.classList.toggle("open");
      menuToggle.setAttribute("aria-expanded", isOpen ? "true" : "false");
      menuToggle.textContent = isOpen ? "✕" : "☰";
    });
  }

  // Close menu on outside click
  document.addEventListener("click", (e) => {
    if (
      navLinks && menuToggle &&
      navLinks.classList.contains("open") &&
      !navLinks.contains(e.target) &&
      !menuToggle.contains(e.target)
    ) {
      navLinks.classList.remove("open");
      menuToggle.setAttribute("aria-expanded", "false");
      menuToggle.textContent = "☰";
    }
  });

  // Close menu when a nav link is clicked
  if (navLinks) {
    navLinks.querySelectorAll("a").forEach((link) => {
      link.addEventListener("click", () => {
        navLinks.classList.remove("open");
        if (menuToggle) {
          menuToggle.setAttribute("aria-expanded", "false");
          menuToggle.textContent = "☰";
        }
      });
    });
  }

  // ────────────────────────────────────────────────────────
  // THEME TOGGLE  (persisted to localStorage)
  // ────────────────────────────────────────────────────────
  const updateThemeIcon = () => {
    if (themeToggle) {
      themeToggle.textContent = document.body.classList.contains("dark-theme") ? "☀️" : "🌙";
    }
  };

  if (themeToggle) {
    // Restore saved preference
    if (localStorage.getItem("theme") === "dark") {
      document.body.classList.add("dark-theme");
    }
    updateThemeIcon();

    themeToggle.addEventListener("click", () => {
      document.body.classList.toggle("dark-theme");
      updateThemeIcon();
      localStorage.setItem(
        "theme",
        document.body.classList.contains("dark-theme") ? "dark" : "light"
      );
    });
  }

  // ────────────────────────────────────────────────────────
  // HELPERS
  // ────────────────────────────────────────────────────────
  /** Safely reads a trimmed value from an input by its id. */
  const getValue = (id, fallback = "") => {
    const el = document.getElementById(id);
    return el ? (el.value || "").trim() || fallback : fallback;
  };

  // ────────────────────────────────────────────────────────
  // PROFILE PHOTO  — live mini-preview inside the form
  // ────────────────────────────────────────────────────────
  const profilePhotoInput = document.getElementById("profilePhoto");
  const photoPreviewWrap  = document.getElementById("photoPreviewWrap");
  const photoPreviewImg   = document.getElementById("photoPreviewImg");

  if (profilePhotoInput) {
    profilePhotoInput.addEventListener("change", () => {
      const file = profilePhotoInput.files[0];
      if (file && photoPreviewWrap && photoPreviewImg) {
        const url = URL.createObjectURL(file);
        photoPreviewImg.src = url;
        photoPreviewWrap.style.display = "block";
      } else if (photoPreviewWrap) {
        photoPreviewWrap.style.display = "none";
      }
      renderPreview(); // refresh main preview too
    });
  }

  // ────────────────────────────────────────────────────────
  // LIVE PREVIEW
  // ────────────────────────────────────────────────────────
  const previewContent = document.getElementById("previewContent");

  function renderPreview() {
    if (!previewContent) return;

    const name            = getValue("name",              "Your Name");
    const email           = getValue("email",             "example@gmail.com");
    const phone           = getValue("phone",             "");
    const address         = getValue("address",           "");
    const role            = getValue("role",              "BTech Student");
    const summary         = getValue("summary",           "Profile summary will appear here.");
    const college         = getValue("college",           "");
    const degree          = getValue("degree",            "");
    const branch          = getValue("branch",            "");
    const graduationYear  = getValue("graduationYear",    "");
    const cgpa            = getValue("cgpa",              "");
    const skills          = getValue("skillsInput",       "");
    const projects        = getValue("projectsInput",     "");
    const projectDesc     = getValue("projectDescription","");
    const certificates    = getValue("certificatesInput", "");
    const certDetails     = getValue("certificateDetails","");
    const experience      = getValue("experience",        "");
    const github          = getValue("github",            "");
    const linkedin        = getValue("linkedin",          "");
    const languages       = getValue("languages",         "");
    const hobbies         = getValue("hobbies",           "");

    // Profile photo
    let photoHTML = "";
    if (profilePhotoInput && profilePhotoInput.files && profilePhotoInput.files[0]) {
      const url = URL.createObjectURL(profilePhotoInput.files[0]);
      photoHTML = `<div style="text-align:center;margin-bottom:14px;">
        <img src="${url}" alt="Profile photo"
          style="width:80px;height:80px;border-radius:50%;object-fit:cover;border:2px solid #10b981;" />
      </div>`;
    }

    const row = (label, val) =>
      val
        ? `<div class="preview-row"><strong>${label}:</strong> <span>${esc(val)}</span></div>`
        : "";

    previewContent.innerHTML = `
      ${photoHTML}
      ${row("Name",             name)}
      ${row("Email",            email)}
      ${row("Phone",            phone)}
      ${row("Address",          address)}
      ${row("Role",             role)}
      ${row("Summary",          summary)}
      ${college || degree || branch ? `<div class="preview-row"><strong>Education:</strong>
        <span>${esc([degree, branch].filter(Boolean).join(" — "))}</span></div>` : ""}
      ${row("College",          college)}
      ${row("Graduation Year",  graduationYear)}
      ${row("CGPA",             cgpa)}
      ${row("Skills",           skills)}
      ${row("Experience",       experience)}
      ${row("Projects",         projects)}
      ${row("Project Description", projectDesc)}
      ${row("Certificates",     certificates)}
      ${row("Certificate Details", certDetails)}
      ${row("Languages",        languages)}
      ${row("Hobbies / Interests", hobbies)}
      ${row("GitHub",           github)}
      ${row("LinkedIn",         linkedin)}
    `;
  }

  // ────────────────────────────────────────────────────────
  // FORM EVENTS  — live preview on every keystroke
  // ────────────────────────────────────────────────────────
  const resumeForm = document.getElementById("resumeForm");

  if (resumeForm) {
    resumeForm.querySelectorAll("input, textarea, select").forEach((el) => {
      el.addEventListener("input",  renderPreview);
      el.addEventListener("change", renderPreview);
    });
    renderPreview(); // initial render
  }

  // ────────────────────────────────────────────────────────
  // FORM SUBMIT  → Spring Boot API
  // ────────────────────────────────────────────────────────
  if (resumeForm) {
    resumeForm.addEventListener("submit", async (e) => {
      e.preventDefault();

      const name  = getValue("name");
      const email = getValue("email");
      const phone = getValue("phone");

      if (!name)  { showToast("Please enter your full name.");  return; }
      if (!email) { showToast("Please enter your email.");      return; }
      if (!email.includes("@")) { showToast("Enter a valid email address."); return; }
      if (phone && !/^\+?[0-9()\-\s.]{7,30}$/.test(phone)) {
        showToast("Enter a valid phone number.");
        return;
      }

      renderPreview();

      const payload = {
        userId: Auth.getUser().userId,
        resumeName:         name,
        email:              email,
        phone:              phone,
        address:            getValue("address"),
        role:               getValue("role"),
        summary:            getValue("summary"),
        college:            getValue("college"),
        degree:             getValue("degree"),
        branch:             getValue("branch"),
        graduationYear:     getValue("graduationYear"),
        cgpa:               getValue("cgpa"),
        skills:             getValue("skillsInput"),
        projects:           getValue("projectsInput"),
        projectDescription: getValue("projectDescription"),
        certificates:       getValue("certificatesInput"),
        certificateDetails: getValue("certificateDetails"),
        experience:         getValue("experience"),
        github:             getValue("github"),
        linkedin:           getValue("linkedin"),
        languages:          getValue("languages"),
        hobbies:            getValue("hobbies"),
        filePath:           "resume.pdf",
      };

      try {
        const res = await Auth.authFetch(`${API_BASE}/api/resume`, {
          method:  "POST",
          headers: { "Content-Type": "application/json" },
          body:    JSON.stringify(payload),
        });
        const msg = await res.text();
        showToast(msg, res.ok ? "success" : "error");
      } catch (error) {
        showToast(
          window.getNetworkErrorMessage
            ? window.getNetworkErrorMessage(error)
            : "Unable to save resume. Check your connection.",
          "error"
        );
      }
    });
  }

  // ────────────────────────────────────────────────────────
  // DOWNLOAD PDF BUTTON
  // ────────────────────────────────────────────────────────
  const downloadBtn = document.getElementById("downloadBtn");

  if (downloadBtn) {
    downloadBtn.addEventListener("click", () => {

      // ── Guard: jsPDF must be loaded ──
      // The UMD bundle from unpkg.com/jspdf exposes window.jspdf.jsPDF
      const jsPDF = window.jspdf && window.jspdf.jsPDF;
      if (!jsPDF) {
        showToast(
          "PDF library not loaded. Please check your internet connection and refresh the page.",
          "error"
        );
        return;
      }

      // Disable to prevent duplicate clicks
      downloadBtn.disabled = true;
      const originalLabel = downloadBtn.textContent;
      downloadBtn.textContent = "⏳ Generating…";

      const restoreButton = () => {
        downloadBtn.disabled = false;
        downloadBtn.textContent = originalLabel;
      };

      // Collect all values once
      const fields = {
        name:            getValue("name",              "Your Name"),
        email:           getValue("email",             ""),
        phone:           getValue("phone",             ""),
        address:         getValue("address",           ""),
        role:            getValue("role",              ""),
        summary:         getValue("summary",           ""),
        college:         getValue("college",           ""),
        degree:          getValue("degree",            ""),
        branch:          getValue("branch",            ""),
        graduationYear:  getValue("graduationYear",    ""),
        cgpa:            getValue("cgpa",              ""),
        skills:          getValue("skillsInput",       ""),
        projects:        getValue("projectsInput",     ""),
        projectDesc:     getValue("projectDescription",""),
        certificates:    getValue("certificatesInput", ""),
        certDetails:     getValue("certificateDetails",""),
        experience:      getValue("experience",        ""),
        github:          getValue("github",            ""),
        linkedin:        getValue("linkedin",          ""),
        languages:       getValue("languages",         ""),
        hobbies:         getValue("hobbies",           ""),
      };

      const photoFile = profilePhotoInput ? profilePhotoInput.files[0] : null;

      if (photoFile) {
        const reader = new FileReader();
        reader.onload = (ev) => {
          try {
            buildPDF(jsPDF, fields, ev.target.result, photoFile.type);
          } catch (err) {
            console.error("PDF generation error:", err);
            showToast("PDF generation failed. Please try again.", "error");
          } finally {
            restoreButton();
          }
        };
        reader.onerror = () => {
          showToast("Could not read photo file.", "error");
          restoreButton();
        };
        reader.readAsDataURL(photoFile);
      } else {
        try {
          buildPDF(jsPDF, fields, null, null);
        } catch (err) {
          console.error("PDF generation error:", err);
          showToast("PDF generation failed. Please try again.", "error");
        } finally {
          restoreButton();
        }
      }
    });
  }

  // ────────────────────────────────────────────────────────
  // BUILD PDF  (extracted to top-level function — no re-declaration per click)
  // ────────────────────────────────────────────────────────
  function buildPDF(jsPDF, f, imgData, imgMimeType) {

    const doc = new jsPDF({ unit: "mm", format: "a4" });

    // ── Layout ──
    const PAGE_W  = 210;
    const PAGE_H  = 297;
    const SB_W    = 62;
    const MAIN_X  = SB_W + 8;
    const MAIN_W  = PAGE_W - MAIN_X - 10;
    const MARGIN_B = 15;

    // ── Colour palette ──
    const ACCENT   = [16, 185, 129];
    const DARK     = [31,  41,  55];
    const WHITE    = [255, 255, 255];
    const BLACK    = [17,  17,  17];
    const GRAY     = [90,  90,  90];
    const LIGHT_BG = [245, 252, 249];

    // ── Helpers ──

    function drawPageBackground() {
      doc.setFillColor(...DARK);
      doc.rect(0, 0, SB_W, PAGE_H, "F");
      doc.setFillColor(...LIGHT_BG);
      doc.rect(SB_W, 0, PAGE_W - SB_W, PAGE_H, "F");
    }

    /** Clamp sidebar Y so text never runs off the page */
    function leftSafe(y) {
      return Math.min(y, PAGE_H - 10);
    }

    /** Add a new page and redraw the two-column background */
    function newPage() {
      doc.addPage();
      drawPageBackground();
    }

    /** Check if the right column needs a new page */
    function checkPageBreak(y, needed) {
      if (y + needed > PAGE_H - MARGIN_B) {
        newPage();
        return 15;
      }
      return y;
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
      y = checkPageBreak(y, 12);
      doc.setFillColor(...ACCENT);
      doc.rect(MAIN_X - 2, y, MAIN_W + 4, 7, "F");
      doc.setTextColor(...WHITE);
      doc.setFontSize(9);
      doc.setFont("helvetica", "bold");
      doc.text(title.toUpperCase(), MAIN_X + MAIN_W / 2, y + 5, { align: "center" });
      return y + 12;
    }

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

    // ── PAGE 1 BACKGROUND ──
    drawPageBackground();

    // ══════════════════════════════════════════════════
    // LEFT SIDEBAR
    // ══════════════════════════════════════════════════
    let leftY = 10;

    // Profile photo OR initials circle
    if (imgData) {
      let fmt = "JPEG";
      if (imgMimeType === "image/png")  fmt = "PNG";
      else if (imgMimeType === "image/webp") fmt = "WEBP";
      else if (imgData.startsWith("data:image/png")) fmt = "PNG";
      try {
        doc.addImage(imgData, fmt, 13, leftY, 36, 36);
      } catch {
        // Fall back to initial circle if image fails
        doc.setDrawColor(...ACCENT);
        doc.setLineWidth(1);
        doc.circle(SB_W / 2, leftY + 18, 16);
        doc.setTextColor(...ACCENT);
        doc.setFontSize(20);
        doc.text(f.name.charAt(0).toUpperCase(), SB_W / 2, leftY + 22, { align: "center" });
      }
    } else {
      doc.setDrawColor(...ACCENT);
      doc.setLineWidth(1);
      doc.circle(SB_W / 2, leftY + 18, 16);
      doc.setTextColor(...ACCENT);
      doc.setFontSize(20);
      doc.text(f.name.charAt(0).toUpperCase(), SB_W / 2, leftY + 22, { align: "center" });
    }
    leftY = leftSafe(leftY + 42);

    // Name
    doc.setTextColor(...WHITE);
    doc.setFontSize(12);
    doc.setFont("helvetica", "bold");
    doc.splitTextToSize(f.name, SB_W - 10).forEach((line) => {
      leftY = leftSafe(leftY);
      doc.text(line, SB_W / 2, leftY, { align: "center" });
      leftY = leftSafe(leftY + 6);
    });

    // Role
    if (f.role) {
      doc.setFontSize(8);
      doc.setFont("helvetica", "normal");
      doc.setTextColor(...ACCENT);
      doc.splitTextToSize(f.role, SB_W - 10).forEach((line) => {
        leftY = leftSafe(leftY);
        doc.text(line, SB_W / 2, leftY, { align: "center" });
        leftY = leftSafe(leftY + 5);
      });
    }
    leftY = leftSafe(leftY + 6);

    // ── CONTACT ──
    leftY = leftSectionHeading("Contact", leftY);
    const contactItems = [
      { label: "Email",   value: f.email   },
      { label: "Phone",   value: f.phone   },
      { label: "Address", value: f.address },
    ].filter((c) => c.value);

    contactItems.forEach((item) => {
      leftY = leftSafe(leftY);
      doc.setFontSize(7.5);
      doc.setTextColor(...ACCENT);
      doc.setFont("helvetica", "bold");
      doc.text(item.label + ":", 8, leftY);
      leftY = leftSafe(leftY + 4);
      doc.setTextColor(...WHITE);
      doc.setFont("helvetica", "normal");
      doc.splitTextToSize(item.value, SB_W - 14).forEach((l) => {
        leftY = leftSafe(leftY);
        doc.text(l, 8, leftY);
        leftY = leftSafe(leftY + 4);
      });
      leftY = leftSafe(leftY + 2);
    });
    leftY = leftSafe(leftY + 4);

    // ── SKILLS ──
    if (f.skills) {
      leftY = leftSectionHeading("Skills", leftY);
      doc.setFontSize(7.5);
      doc.setFont("helvetica", "normal");
      doc.setTextColor(...WHITE);
      f.skills.split(",").forEach((skill) => {
        if (skill.trim()) {
          leftY = leftSafe(leftY);
          doc.text("•  " + skill.trim(), 8, leftY);
          leftY = leftSafe(leftY + 5.5);
        }
      });
      leftY = leftSafe(leftY + 4);
    }

    // ── LANGUAGES ──
    if (f.languages) {
      leftY = leftSectionHeading("Languages", leftY);
      doc.setFontSize(7.5);
      doc.setFont("helvetica", "normal");
      doc.setTextColor(...WHITE);
      f.languages.split(",").forEach((lang) => {
        if (lang.trim()) {
          leftY = leftSafe(leftY);
          doc.text("•  " + lang.trim(), 8, leftY);
          leftY = leftSafe(leftY + 5.5);
        }
      });
      leftY = leftSafe(leftY + 4);
    }

    // ── INTERESTS ──
    if (f.hobbies) {
      leftY = leftSectionHeading("Interests", leftY);
      doc.setFontSize(7.5);
      doc.setFont("helvetica", "normal");
      doc.setTextColor(...WHITE);
      f.hobbies.split(",").forEach((h) => {
        if (h.trim()) {
          leftY = leftSafe(leftY);
          doc.text("•  " + h.trim(), 8, leftY);
          leftY = leftSafe(leftY + 5.5);
        }
      });
      leftY = leftSafe(leftY + 4);
    }

    // ── PROFILES (sidebar — truncated) ──
    if (f.github || f.linkedin) {
      leftY = leftSectionHeading("Profiles", leftY);
      const trunc = (url, max = 28) =>
        url.length > max ? url.slice(0, max) + "…" : url;

      if (f.github) {
        leftY = leftSafe(leftY);
        doc.setFontSize(7);
        doc.setTextColor(...ACCENT);
        doc.setFont("helvetica", "bold");
        doc.text("GitHub:", 8, leftY);
        leftY = leftSafe(leftY + 4);
        doc.setTextColor(...WHITE);
        doc.setFont("helvetica", "normal");
        doc.splitTextToSize(trunc(f.github), SB_W - 14).forEach((l) => {
          leftY = leftSafe(leftY);
          doc.text(l, 8, leftY);
          leftY = leftSafe(leftY + 4);
        });
        leftY = leftSafe(leftY + 2);
      }
      if (f.linkedin) {
        leftY = leftSafe(leftY);
        doc.setFontSize(7);
        doc.setTextColor(...ACCENT);
        doc.setFont("helvetica", "bold");
        doc.text("LinkedIn:", 8, leftY);
        leftY = leftSafe(leftY + 4);
        doc.setTextColor(...WHITE);
        doc.setFont("helvetica", "normal");
        doc.splitTextToSize(trunc(f.linkedin), SB_W - 14).forEach((l) => {
          leftY = leftSafe(leftY);
          doc.text(l, 8, leftY);
          leftY = leftSafe(leftY + 4);
        });
      }
    }

    // ══════════════════════════════════════════════════
    // RIGHT MAIN CONTENT
    // ══════════════════════════════════════════════════
    let y = 15;

    // ── ABOUT ME ──
    if (f.summary) {
      y = checkPageBreak(y, 20);
      y = rightSectionHeading("About Me", y);
      y = addWrappedText(f.summary, MAIN_X, y, MAIN_W, 9, "normal", BLACK, 5);
      y += 5;
    }

    // ── EDUCATION ──
    if (f.college || f.degree || f.branch || f.graduationYear || f.cgpa) {
      y = checkPageBreak(y, 28);
      y = rightSectionHeading("Education", y);

      const degLine = [f.degree, f.branch].filter(Boolean).join(" — ");
      if (degLine) {
        y = addWrappedText(degLine, MAIN_X, y, MAIN_W, 9.5, "bold", BLACK, 5);
      }
      if (f.college) {
        y = addWrappedText(f.college, MAIN_X, y, MAIN_W, 9, "normal", GRAY, 5);
      }
      const eduMeta = [
        f.cgpa            ? "CGPA: " + f.cgpa            : "",
        f.graduationYear  ? "Graduation: " + f.graduationYear : "",
      ].filter(Boolean).join("   |   ");
      if (eduMeta) {
        y = addWrappedText(eduMeta, MAIN_X, y, MAIN_W, 9, "normal", GRAY, 5);
      }
      y += 5;
    }

    // ── PROJECTS ──
    if (f.projects) {
      y = checkPageBreak(y, 20);
      y = rightSectionHeading("Projects", y);

      const projectList = f.projects.split(",").map((p) => p.trim()).filter(Boolean);
      const descList    = f.projectDesc
        ? f.projectDesc.split("\n").map((d) => d.trim()).filter(Boolean)
        : [];

      projectList.forEach((proj, idx) => {
        y = checkPageBreak(y, 16);

        // Project title bar
        doc.setFillColor(232, 250, 244);
        doc.rect(MAIN_X - 2, y - 4, MAIN_W + 4, 6, "F");
        doc.setFillColor(...ACCENT);
        doc.rect(MAIN_X - 2, y - 4, 2, 6, "F");

        doc.setFontSize(9.5);
        doc.setFont("helvetica", "bold");
        doc.setTextColor(...BLACK);
        doc.text("  " + proj, MAIN_X, y);
        y += 6;

        const thisDesc = descList[idx] || "";
        if (thisDesc) {
          y = addWrappedText(thisDesc, MAIN_X + 3, y, MAIN_W - 3, 8.5, "normal", GRAY, 4.5);
        }
        y += 4;
      });
      y += 3;
    }

    // ── CERTIFICATIONS ──
    if (f.certificates) {
      y = checkPageBreak(y, 20);
      y = rightSectionHeading("Certifications", y);

      const certList    = f.certificates.split(",").map((c) => c.trim()).filter(Boolean);
      const detailsList = f.certDetails
        ? f.certDetails.split("\n").map((d) => d.trim()).filter(Boolean)
        : [];

      certList.forEach((cert, idx) => {
        y = checkPageBreak(y, 14);

        doc.setFontSize(9.5);
        doc.setFont("helvetica", "bold");
        doc.setTextColor(...ACCENT);
        doc.text("✓", MAIN_X, y);
        doc.setTextColor(...BLACK);

        doc.splitTextToSize(cert, MAIN_W - 6).forEach((cl) => {
          doc.text(cl, MAIN_X + 5, y);
          y += 5.5;
        });

        const detail = detailsList[idx] || "";
        if (detail) {
          y = addWrappedText(detail, MAIN_X + 5, y, MAIN_W - 5, 8.5, "normal", GRAY, 4.5);
        }
        y += 4;
      });
      y += 3;
    }

    // ── EXPERIENCE ──
    if (f.experience) {
      y = checkPageBreak(y, 20);
      y = rightSectionHeading("Experience", y);
      y = addWrappedText(f.experience, MAIN_X, y, MAIN_W, 9, "normal", BLACK, 5);
      y += 5;
    }

    // ── PROFESSIONAL PROFILES (full URLs in right column) ──
    if (f.github || f.linkedin) {
      y = checkPageBreak(y, 20);
      y = rightSectionHeading("Professional Profiles", y);

      if (f.github) {
        y = checkPageBreak(y, 10);
        doc.setFontSize(9);
        doc.setFont("helvetica", "bold");
        doc.setTextColor(...BLACK);
        doc.text("GitHub:", MAIN_X, y);
        doc.setFont("helvetica", "normal");
        doc.setTextColor(...ACCENT);
        const ghLines = doc.splitTextToSize(f.github, MAIN_W - 22);
        doc.text(ghLines, MAIN_X + 22, y);
        y += ghLines.length * 5 + 3;
      }
      if (f.linkedin) {
        y = checkPageBreak(y, 10);
        doc.setFontSize(9);
        doc.setFont("helvetica", "bold");
        doc.setTextColor(...BLACK);
        doc.text("LinkedIn:", MAIN_X, y);
        doc.setFont("helvetica", "normal");
        doc.setTextColor(...ACCENT);
        const liLines = doc.splitTextToSize(f.linkedin, MAIN_W - 24);
        doc.text(liLines, MAIN_X + 24, y);
        y += liLines.length * 5 + 3;
      }
    }

    // ── SAVE FILE ──
    const filename = (f.name || "Resume").replace(/\s+/g, "_") + "_Resume.pdf";
    doc.save(filename);
  } // end buildPDF

  // ────────────────────────────────────────────────────────
  // CONTACT SECTION — send button feedback
  // ────────────────────────────────────────────────────────
  const contactSendBtn = document.getElementById("contactSendBtn");
  if (contactSendBtn) {
    contactSendBtn.addEventListener("click", () => {
      const email = (document.getElementById("contactEmail")?.value || "").trim();
      if (!email || !email.includes("@")) {
        showToast("Please enter a valid email address.", "error");
        return;
      }
      showToast("Thank you! Your message details have been noted.", "success");
    });
  }

}); // end DOMContentLoaded
