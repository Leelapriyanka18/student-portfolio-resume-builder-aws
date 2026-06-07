document.addEventListener('DOMContentLoaded', () => {
  const navLinks = document.getElementById('navLinks');
  const menuToggle = document.getElementById('menuToggle');
  const themeToggle = document.getElementById('themeToggle');
  const currentPage = window.location.pathname.split('/').pop() || 'index.html';

  document.querySelectorAll('.nav-links a').forEach((link) => {
    const href = link.getAttribute('href');
    if (
      href === currentPage ||
      href === `./${currentPage}` ||
      href === `../${currentPage}` ||
      (currentPage === 'index.html' && href === '#home')
    ) {
      link.classList.add('active');
    }
  });

  if (menuToggle && navLinks) {
    menuToggle.addEventListener('click', () => {
      navLinks.classList.toggle('open');
    });
  }

  if (themeToggle) {
    const updateThemeIcon = () => {
      themeToggle.textContent = document.body.classList.contains('dark-theme') ? '☀️' : '🌙';
    };
    updateThemeIcon();

    themeToggle.addEventListener('click', () => {
      document.body.classList.toggle('dark-theme');
      updateThemeIcon();
    });
  }

  document.addEventListener('click', (event) => {
    if (
      navLinks &&
      menuToggle &&
      navLinks.classList.contains('open') &&
      !navLinks.contains(event.target) &&
      !menuToggle.contains(event.target)
    ) {
      navLinks.classList.remove('open');
    }
  });

  const resumeForm = document.getElementById('resumeForm');
  const previewContent = document.getElementById('previewContent');
  const downloadBtn = document.getElementById('downloadBtn');

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

    <div class="preview-row"><strong>Certificates:</strong> ${certificates}</div>
  `;
};

 if (resumeForm) {
  resumeForm.addEventListener("submit", (event) => {
    event.preventDefault();

   

    renderPreview();
  });
}

  
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

    let y = 20;

    doc.setFontSize(22);
    doc.setFont(undefined, "bold");
    doc.text(name, 20, y);

    y += 8;

    doc.setFontSize(14);
    doc.setFont(undefined, "normal");
    doc.text(role, 20, y);

    y += 12;

    doc.setFontSize(11);
    doc.text(`Email: ${email}`, 20, y);
    y += 6;

    doc.text(`Phone: ${phone}`, 20, y);
    y += 6;

    doc.text(`Address: ${address}`, 20, y);
    y += 12;

    doc.setFontSize(14);
    doc.setFont(undefined, "bold");
    doc.text("PROFESSIONAL SUMMARY", 20, y);

    y += 8;

    doc.setFontSize(11);
    doc.setFont(undefined, "normal");

    const summaryLines = doc.splitTextToSize(summary, 170);
    doc.text(summaryLines, 20, y);

    y += summaryLines.length * 6 + 8;

   doc.setFontSize(14);
doc.setFont(undefined, "bold");
doc.text("EDUCATION", 20, y);

y += 8;

doc.setFontSize(11);
doc.setFont(undefined, "normal");

doc.text(`${degree}`, 20, y);
y += 6;

doc.text(`${branch}`, 20, y);
y += 6;

doc.text(`${college}`, 20, y);
y += 6;

doc.text(`CGPA: ${cgpa}`, 20, y);
y += 6;

doc.text(`Graduation Year: ${graduationYear}`, 20, y);

y += 12;

    doc.setFontSize(14);
    doc.setFont(undefined, "bold");
    doc.text("SKILLS", 20, y);

    y += 8;

    doc.setFontSize(11);
    doc.setFont(undefined, "normal");

    skills.split(",").forEach(skill => {
      doc.text(`• ${skill.trim()}`, 25, y);
      y += 6;
    });

    y += 4;

    doc.setFontSize(14);
    doc.setFont(undefined, "bold");
    doc.text("PROJECTS", 20, y);

    y += 8;

    doc.setFontSize(11);
    doc.setFont(undefined, "normal");

    projects.split(",").forEach(project => {
      doc.text(`• ${project.trim()}`, 25, y);
      y += 6;
    });

    y += 4;

    doc.setFontSize(14);
    doc.setFont(undefined, "bold");
    doc.text("CERTIFICATIONS", 20, y);

    y += 8;

    doc.setFontSize(11);
    doc.setFont(undefined, "normal");

    certificates.split(",").forEach(certificate => {
      doc.text(`• ${certificate.trim()}`, 25, y);
      y += 6;
    });

    doc.save("Resume.pdf");
  });
}

});