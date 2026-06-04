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
    const name = getValue('name', 'Your Name');
    const title = getValue('role', 'AI & DS Student');
    const summary = getValue(
      'summary',
      'A motivated student building polished portfolios, resume tools, and cloud-aware web experiences.'
    );
    const skills = getValue('skillsInput', 'HTML, CSS, JavaScript');
    const education = getValue('education', 'BTech AI & DS, BVC Engineering College');
    const projects = getValue('projectsInput', 'Portfolio Website, Resume Builder');

    previewContent.innerHTML = `
      <div class="preview-row"><strong>Name:</strong> ${name}</div>
      <div class="preview-row"><strong>Title:</strong> ${title}</div>
      <div class="preview-row"><strong>Summary:</strong> ${summary}</div>
      <div class="preview-row"><strong>Skills:</strong> ${skills}</div>
      <div class="preview-row"><strong>Education:</strong> ${education}</div>
      <div class="preview-row"><strong>Projects:</strong> ${projects}</div>
    `;
  };

  if (resumeForm) {
    resumeForm.addEventListener('submit', (event) => {
      event.preventDefault();
      renderPreview();
    });
  }

  if (downloadBtn) {
    downloadBtn.addEventListener('click', () => {
      if (!previewContent) return;
      const lines = previewContent.innerText;
      const blob = new Blob([lines], { type: 'text/plain;charset=utf-8' });
      const link = document.createElement('a');
      link.href = URL.createObjectURL(blob);
      link.download = 'resume-preview.txt';
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
      URL.revokeObjectURL(link.href);
    });
  }
});
