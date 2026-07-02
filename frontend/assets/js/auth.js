// ============================================================
// auth.js — shared API base, toast, session & route-guard helpers
// Loaded BEFORE script.js on every page.
// ============================================================

// ── Single source of truth for backend URL ──
window.API_BASE = ((window.APP_CONFIG && window.APP_CONFIG.apiBase) || "").replace(/\/$/, "");

if (!window.API_BASE) {
  console.warn("Backend API base URL is not configured. Update frontend/assets/js/config.js.");
}

window.getApiBase = function () {
  const apiBase = ((window.APP_CONFIG && window.APP_CONFIG.apiBase) || window.API_BASE || "").replace(/\/$/, "");
  if (!apiBase) {
    throw new Error("API_BASE_NOT_CONFIGURED");
  }
  return apiBase;
};

window.getNetworkErrorMessage = function (error) {
  if (error && error.message === "API_BASE_NOT_CONFIGURED") {
    return "Backend API URL is not configured. Please redeploy frontend/assets/js/config.js.";
  }
  return `Unable to connect to backend at ${window.API_BASE || "the configured API URL"}. Confirm the EC2 app is running, listening on 0.0.0.0:8080, and the security group allows TCP 8080.`;
};

// ── Toast (shared implementation; script.js reuses this if present) ──
window.showToast = window.showToast || function (msg, type = "error") {
  let container = document.getElementById("toast-container");
  if (!container) {
    container = document.createElement("div");
    container.id = "toast-container";
    container.style.cssText =
      "position:fixed;top:1rem;right:1rem;z-index:9999;" +
      "display:flex;flex-direction:column;gap:.5rem;max-width:320px;";
    document.body.appendChild(container);
  }
  const bg    = type === "success" ? "#EAF3DE" : type === "info" ? "#E6F1FB" : "#FCEBEB";
  const color = type === "success" ? "#3B6D11" : type === "info" ? "#185FA5" : "#A32D2D";
  const toast = document.createElement("div");
  toast.style.cssText =
    `background:${bg};color:${color};padding:.75rem 1.25rem;border-radius:8px;` +
    `font-size:14px;box-shadow:0 2px 8px rgba(0,0,0,.14);word-break:break-word;` +
    `border-left:3px solid ${color};transition:opacity .3s;`;
  toast.textContent = msg;
  container.appendChild(toast);
  setTimeout(() => {
    toast.style.opacity = "0";
    setTimeout(() => toast.remove(), 300);
  }, 4000);
};

// ── Session helpers ──
const Auth = {
  isLoggedIn() {
    return localStorage.getItem("loggedIn") === "true" && !!localStorage.getItem("userId");
  },
  getUser() {
    return {
      userId:    localStorage.getItem("userId"),
      userEmail: localStorage.getItem("userEmail"),
      userName:  localStorage.getItem("userName"),
    };
  },
  setSession({ userId, email, fullName }) {
    localStorage.setItem("loggedIn", "true");
    localStorage.setItem("userId", String(userId));
    localStorage.setItem("userEmail", email || "");
    localStorage.setItem("userName", fullName || "");
  },
  logout(redirectTo = "login.html") {
    localStorage.removeItem("loggedIn");
    localStorage.removeItem("userId");
    localStorage.removeItem("userEmail");
    localStorage.removeItem("userName");
    window.location.href = redirectTo;
  },
  /** Call at the top of any protected page. Redirects to login if not authenticated. */
  requireAuth(loginPage = "login.html") {
    if (!this.isLoggedIn()) {
      window.location.href = loginPage;
      return false;
    }
    return true;
  },
  /** Toggles nav links based on session state. Call on every page that has the shared nav. */
  refreshNav() {
    const loggedIn = this.isLoggedIn();
    document.querySelectorAll("[data-auth='guest']").forEach((el) => {
      el.style.display = loggedIn ? "none" : "";
    });
    document.querySelectorAll("[data-auth='user']").forEach((el) => {
      el.style.display = loggedIn ? "" : "none";
    });
    const logoutBtn = document.getElementById("logoutBtn");
    if (logoutBtn) {
      logoutBtn.addEventListener("click", (e) => {
        e.preventDefault();
        Auth.logout();
      });
    }
  },
};

window.Auth = Auth;

document.addEventListener("DOMContentLoaded", () => Auth.refreshNav());
