import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import './Login.css';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080';

function Login() {
  const navigate = useNavigate();
  const [credentials, setCredentials] = useState({ admission_no: '', password: '' });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const [showPassword, setShowPassword] = useState(false);

  const handleChange = (event) => {
    setCredentials((current) => ({
      ...current,
      [event.target.name]: event.target.value,
    }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    setError('');
    setLoading(true);

    try {
      const query = new URLSearchParams(credentials);
      const response = await fetch(`${API_BASE_URL}/v1/user/login?${query}`, {
        method: 'POST',
      });

      const responseText = await response.text();
      let result = responseText;
      try {
        result = responseText ? JSON.parse(responseText) : null;
      } catch {
        // Some endpoints return a plain text message.
      }

      if (!response.ok) {
        const message = typeof result === 'string'
          ? result
          : result?.message || result?.error;
        throw new Error(message || `Login failed (${response.status})`);
      }

      sessionStorage.setItem('hrmsEmployee', JSON.stringify(result));
      navigate('/Dashboard', { state: { employee: result } });
    } catch (requestError) {
      const isNetworkError = requestError instanceof TypeError;
      setError(isNetworkError
        ? 'Cannot reach the backend at ' + API_BASE_URL + '. Start the backend and allow requests from http://localhost:5173.'
        : requestError.message || 'Could not connect to the server.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <main className="login-page">
      <section className="login-showcase" aria-label="HRMS portal">
        <div className="login-showcase__glow login-showcase__glow--one" />
        <div className="login-showcase__glow login-showcase__glow--two" />
        <div className="login-showcase__content">
          <a className="login-brand" href="/" aria-label="HRMS home">
            <span className="login-brand__mark" aria-hidden="true">
              <svg viewBox="0 0 32 32" fill="none">
                <path d="M16 4.5 27.5 11v10L16 27.5 4.5 21V11L16 4.5Z" stroke="currentColor" strokeWidth="2" />
                <path d="M11 19.5v-7l5 3.1 5-3.1v7" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" />
              </svg>
            </span>
            <span>HRMS<span className="login-brand__dot">.</span></span>
          </a>

          <div className="login-showcase__message">
            <span className="login-eyebrow"><span /> PEOPLE OPERATIONS</span>
            <h1>Your people.<br />Moving forward.</h1>
            <p>A calmer place to manage your workday, connect with your team, and keep everything in sync.</p>
            <div className="login-showcase__note">
              <span className="login-showcase__note-icon" aria-hidden="true">✦</span>
              <span>Everything you need, all in one place.</span>
            </div>
          </div>

          <div className="login-showcase__footer">A better workday starts here.</div>
        </div>
      </section>

      <section className="login-panel">
        <div className="login-panel__inner">
          <div className="login-mobile-brand" aria-hidden="true">HRMS<span>.</span></div>
          <div className="login-heading">
            <span className="login-heading__eyebrow">WELCOME BACK</span>
            <h2>Sign in to your account</h2>
            <p>Enter your details below to continue.</p>
          </div>

          <form className="login-form" onSubmit={handleSubmit}>
            <div className="login-field">
              <label htmlFor="login-admission-number">Admission number</label>
              <div className="login-input-wrap">
                <svg className="login-field__icon" viewBox="0 0 24 24" fill="none" aria-hidden="true">
                  <rect x="3.5" y="5" width="17" height="14" rx="3" stroke="currentColor" strokeWidth="1.7" />
                  <circle cx="9" cy="11" r="2" stroke="currentColor" strokeWidth="1.7" />
                  <path d="M6.5 16c.6-1.3 1.4-2 2.5-2s1.9.7 2.5 2M14 10h3.5M14 14h3.5" stroke="currentColor" strokeWidth="1.7" strokeLinecap="round" />
                </svg>
                <input
                  type="text"
                  id="login-admission-number"
                  name="admission_no"
                  autoComplete="username"
                  inputMode="numeric"
                  placeholder="Enter your admission number"
                  value={credentials.admission_no}
                  onChange={handleChange}
                  required
                />
              </div>
            </div>

            <div className="login-field">
              <div className="login-field__label-row">
                <label htmlFor="login-password">Password</label>
              </div>
              <div className="login-input-wrap">
                <svg className="login-field__icon" viewBox="0 0 24 24" fill="none" aria-hidden="true">
                  <rect x="4.5" y="10" width="15" height="10" rx="2.5" stroke="currentColor" strokeWidth="1.7" />
                  <path d="M8 10V7.5a4 4 0 1 1 8 0V10M12 14v2" stroke="currentColor" strokeWidth="1.7" strokeLinecap="round" />
                </svg>
                <input
                  type={showPassword ? 'text' : 'password'}
                  id="login-password"
                  name="password"
                  autoComplete="current-password"
                  placeholder="Enter your password"
                  value={credentials.password}
                  onChange={handleChange}
                  required
                />
                <button
                  className="login-password-toggle"
                  type="button"
                  onClick={() => setShowPassword((visible) => !visible)}
                  aria-label={showPassword ? 'Hide password' : 'Show password'}
                >
                  {showPassword ? 'Hide' : 'Show'}
                </button>
              </div>
            </div>

            {error && (
              <div className="login-error" role="alert">
                <span aria-hidden="true">!</span>{error}
              </div>
            )}

            <button className="login-submit" type="submit" disabled={loading}>
              <span>{loading ? 'Signing you in…' : 'Sign in'}</span>
              {!loading && <span className="login-submit__arrow" aria-hidden="true">→</span>}
              {loading && <span className="login-spinner" aria-hidden="true" />}
            </button>
          </form>

          <p className="login-help">Having trouble signing in? <span>Contact your administrator</span></p>
          <div className="login-panel__bottom">© {new Date().getFullYear()} HRMS <span>·</span> Secure employee portal</div>
        </div>
      </section>
    </main>
  );
}

export default Login;
