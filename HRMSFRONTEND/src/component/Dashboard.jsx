import { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import './Dashboard.css';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080';

function readSavedEmployee() {
  try {
    const savedEmployee = sessionStorage.getItem('hrmsEmployee');
    return savedEmployee ? JSON.parse(savedEmployee) : null;
  } catch {
    return null;
  }
}

function formatDate(date) {
  if (!date) return '—';
  const parsedDate = new Date(`${date}T00:00:00`);
  return Number.isNaN(parsedDate.getTime())
    ? date
    : new Intl.DateTimeFormat(undefined, { month: 'short', day: 'numeric', year: 'numeric' }).format(parsedDate);
}

function formatTime(time) {
  if (!time) return '—';
  const [hours, minutes] = time.split(':').map(Number);
  if (Number.isNaN(hours) || Number.isNaN(minutes)) return time;
  const date = new Date();
  date.setHours(hours, minutes, 0, 0);
  return new Intl.DateTimeFormat(undefined, { hour: 'numeric', minute: '2-digit' }).format(date);
}

function getLocalDateString(date = new Date()) {
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  return `${year}-${month}-${day}`;
}

function Dashboard() {
  const location = useLocation();
  const navigate = useNavigate();
  const [employee] = useState(() => location.state?.employee || readSavedEmployee());
  const [attendance, setAttendance] = useState([]);
  const [attendanceLoading, setAttendanceLoading] = useState(true);
  const [attendanceError, setAttendanceError] = useState('');

  useEffect(() => {
    if (!employee?.empId) {
      navigate('/Login', { replace: true });
      return;
    }

    const loadAttendance = async () => {
      try {
        const response = await fetch(
          `${API_BASE_URL}/v1/user/attendance/by-employee/${encodeURIComponent(employee.empId)}`,
        );
        const responseText = await response.text();
        let result = [];
        try {
          result = responseText ? JSON.parse(responseText) : [];
        } catch {
          result = [];
        }

        if (!response.ok) {
          throw new Error(typeof result === 'string'
            ? result
            : result?.message || `Could not load attendance (${response.status})`);
        }

        setAttendance(Array.isArray(result) ? result : []);
      } catch (requestError) {
        setAttendanceError(requestError instanceof TypeError
          ? 'Could not connect to the attendance service.'
          : requestError.message || 'Could not load attendance.');
      } finally {
        setAttendanceLoading(false);
      }
    };

    loadAttendance();
  }, [employee, navigate]);

  const handleSignOut = () => {
    sessionStorage.removeItem('hrmsEmployee');
    navigate('/Login', { replace: true });
  };

  const employeeName = employee?.empName || 'Employee';
  const initials = employeeName.split(/\s+/).filter(Boolean).slice(0, 2).map((part) => part[0]).join('').toUpperCase();
  const today = getLocalDateString();
  const todayAttendance = attendance.find((record) => record.attendancedate === today);

  return (
    <main className="hrms-dashboard">
      <aside className="dashboard-sidebar">
        <a className="dashboard-brand" href="/Dashboard">
          <span className="dashboard-brand__mark" aria-hidden="true">H</span>
          <span>HRMS<span>.</span></span>
        </a>
        <div className="dashboard-sidebar__section">WORKSPACE</div>
        <nav className="dashboard-nav" aria-label="Main navigation">
          <a className="dashboard-nav__item dashboard-nav__item--active" href="#overview">
            <span aria-hidden="true">⌂</span> Overview
          </a>
          <a className="dashboard-nav__item" href="#attendance">
            <span aria-hidden="true">◷</span> Attendance
          </a>
          <a className="dashboard-nav__item" href="#employee-details">
            <span aria-hidden="true">▤</span> My details
          </a>
        </nav>
        <div className="dashboard-sidebar__bottom">
          <span className="dashboard-sidebar__avatar">{initials || 'E'}</span>
          <div className="dashboard-sidebar__identity">
            <strong>{employeeName}</strong>
            <span>Employee account</span>
          </div>
        </div>
      </aside>

      <section className="dashboard-main" id="overview">
        <header className="dashboard-topbar">
          <div className="dashboard-breadcrumb">Workspace <span>/</span> Overview</div>
          <button type="button" className="dashboard-signout" onClick={handleSignOut}>
            <span aria-hidden="true">↪</span> Sign out
          </button>
        </header>

        <div className="dashboard-content">
          <div className="dashboard-greeting">
            <div>
              <span className="dashboard-eyebrow">EMPLOYEE PORTAL</span>
              <h1>Good to see you, {employeeName.split(' ')[0]}.</h1>
              <p>Here’s your employee overview and attendance at a glance.</p>
            </div>
            <div className="dashboard-date">
              <span className="dashboard-date__icon" aria-hidden="true">▦</span>
              <span>{new Intl.DateTimeFormat(undefined, { weekday: 'long', month: 'long', day: 'numeric' }).format(new Date())}</span>
            </div>
          </div>

          <div className="dashboard-summary-grid">
            <article className="dashboard-summary-card dashboard-summary-card--attendance">
              <div className="dashboard-summary-card__top">
                <span className="dashboard-summary-card__label">TODAY’S ATTENDANCE</span>
                <span className="dashboard-summary-card__icon" aria-hidden="true">◷</span>
              </div>
              {attendanceLoading ? (
                <div className="dashboard-summary-card__value dashboard-skeleton" aria-label="Loading attendance" />
              ) : (
                <>
                  <strong className="dashboard-summary-card__value">{todayAttendance ? 'Present' : 'Not marked'}</strong>
                  <span className="dashboard-summary-card__hint">
                    {todayAttendance ? `Checked in at ${formatTime(todayAttendance.inTime)}` : 'No attendance recorded today'}
                  </span>
                </>
              )}
            </article>

            <article className="dashboard-summary-card">
              <div className="dashboard-summary-card__top">
                <span className="dashboard-summary-card__label">EMPLOYEE ID</span>
                <span className="dashboard-summary-card__icon dashboard-summary-card__icon--sage" aria-hidden="true">▤</span>
              </div>
              <strong className="dashboard-summary-card__value">{employee.empId}</strong>
              <span className="dashboard-summary-card__hint">Your registered ID</span>
            </article>

            <article className="dashboard-summary-card">
              <div className="dashboard-summary-card__top">
                <span className="dashboard-summary-card__label">ATTENDANCE RECORDS</span>
                <span className="dashboard-summary-card__icon dashboard-summary-card__icon--sage" aria-hidden="true">▥</span>
              </div>
              <strong className="dashboard-summary-card__value">{attendanceLoading ? '—' : attendance.length}</strong>
              <span className="dashboard-summary-card__hint">Recorded check-ins</span>
            </article>
          </div>

          <div className="dashboard-details-grid">
            <section className="dashboard-card" id="employee-details">
              <div className="dashboard-card__heading">
                <div>
                  <span className="dashboard-card__eyebrow">YOUR PROFILE</span>
                  <h2>Employee details</h2>
                </div>
                <span className="dashboard-profile-avatar">{initials || 'E'}</span>
              </div>
              <dl className="dashboard-profile-list">
                <div><dt>Full name</dt><dd>{employee.empName || '—'}</dd></div>
                <div><dt>Employee ID</dt><dd>{employee.empId || '—'}</dd></div>
                <div><dt>Designation</dt><dd>{employee.designation || '—'}</dd></div>
                <div><dt>Tech stack</dt><dd>{employee.stack || '—'}</dd></div>
              </dl>
            </section>

            <section className="dashboard-card dashboard-attendance-card" id="attendance">
              <div className="dashboard-card__heading dashboard-card__heading--attendance">
                <div>
                  <span className="dashboard-card__eyebrow">YOUR RECENT ACTIVITY</span>
                  <h2>Attendance</h2>
                </div>
                <span className="dashboard-attendance-count">{attendanceLoading ? '…' : attendance.length} records</span>
              </div>

              {attendanceLoading ? (
                <div className="dashboard-attendance-state"><span className="dashboard-loading-dot" /> Loading attendance…</div>
              ) : attendanceError ? (
                <div className="dashboard-attendance-state dashboard-attendance-state--error" role="alert">
                  <strong>Attendance isn’t available yet.</strong>
                  <span>{attendanceError}</span>
                </div>
              ) : attendance.length === 0 ? (
                <div className="dashboard-attendance-state">
                  <span className="dashboard-empty-icon" aria-hidden="true">◷</span>
                  <strong>No attendance records yet</strong>
                  <span>Your check-ins will appear here.</span>
                </div>
              ) : (
                <div className="dashboard-attendance-table-wrap">
                  <table className="dashboard-attendance-table">
                    <thead><tr><th>Date</th><th>Check-in</th><th>Status</th></tr></thead>
                    <tbody>
                      {attendance.map((record) => (
                        <tr key={record.attendanceId ?? `${record.attendancedate}-${record.inTime}`}>
                          <td>{formatDate(record.attendancedate)}</td>
                          <td>{formatTime(record.inTime)}</td>
                          <td><span className="dashboard-status"><span /> Present</span></td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </div>
              )}
            </section>
          </div>
          <footer className="dashboard-footer">HRMS <span>·</span> Employee workspace</footer>
        </div>
      </section>
    </main>
  );
}

export default Dashboard;
