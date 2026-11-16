
import React, { useState, useActionState } from "react";
import SubmitButton from "./SubmitButton";
import api from "./api";

const AuthForm = ({ onLogin }) => {
  const [mode, setMode] = useState("login");

  const submitAction = async (prevState, formData) => {
    const username = formData.get("username")?.trim();
    const password = formData.get("password");

    if (!username || !password) {
      return {
        error: "Please provide both username and password.",
        msg: "",
        username: username || "",
      };
    }

    try {
      if (mode === "register") {
        await api.post("/auth/register", { username, password });

        setMode("login");

        return {
          error: "",
          msg: "Registration successful! Please log in.",
          username,
        };
      }

      const { data } = await api.post("/auth/login", {
        username,
        password,
      });

      localStorage.setItem("token", data.token);
      localStorage.setItem("username", username);

      onLogin(username);

      return {
        error: "",
        msg: "Login successful!",
        username,
      };
    } catch (error) {
      return {
        error:
          error.response?.data?.message ||
          error.response?.data?.error ||
          "An error occurred. Please try again.",
        msg: "",
        username,
      };
    }
  };

  const [state, formAction, isPending] = useActionState(
    submitAction,
    {
      error: "",
      msg: "",
      username: "",
    }
  );

  return (
    <div className="flex min-h-screen items-center justify-center bg-gradient-to-br from-slate-950 via-slate-900 to-indigo-950 px-4 py-10">
      <div className="w-full max-w-md rounded-3xl border border-white/10 bg-white/5 p-8 shadow-2xl backdrop-blur-xl sm:p-10">

        {/* Header */}
        <div className="mb-8 text-center">
          <div className="mx-auto mb-5 flex h-16 w-16 items-center justify-center rounded-2xl bg-indigo-500/20 text-indigo-300">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="32"
              height="32"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
              strokeWidth="1.8"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                d="M12 15v2m-6 4h12a2 2 0 002-2v-5a2 2 0 00-2-2H6a2 2 0 00-2 2v5a2 2 0 002 2zm10-9V7a4 4 0 00-8 0v5h8z"
              />
            </svg>
          </div>

          <h1 className="text-3xl font-extrabold tracking-tight text-white">
            {mode === "login" ? "Welcome Back" : "Create Account"}
          </h1>

          <p className="mt-3 text-sm leading-6 text-slate-400">
            {mode === "login"
              ? "Sign in to continue to your account."
              : "Register to get started with your account."}
          </p>
        </div>

        {/* Form */}
        <form action={formAction} className="space-y-5">
          <div>
            <label
              htmlFor="username"
              className="mb-2 block text-sm font-medium text-slate-200"
            >
              Username or email
            </label>

            <input
              type="text"
              name="username"
              id="username"
              defaultValue={state.username}
              required
              disabled={isPending}
              autoComplete="username"
              placeholder="Enter your username"
              className="w-full rounded-xl border border-slate-700 bg-slate-950/70 px-4 py-3.5 text-white outline-none transition placeholder:text-slate-500 focus:border-indigo-400 focus:ring-4 focus:ring-indigo-500/15 disabled:cursor-not-allowed disabled:opacity-50"
            />
          </div>

          <div>
            <label
              htmlFor="password"
              className="mb-2 block text-sm font-medium text-slate-200"
            >
              Password
            </label>

            <input
              type="password"
              name="password"
              id="password"
              required
              disabled={isPending}
              autoComplete={
                mode === "login"
                  ? "current-password"
                  : "new-password"
              }
              placeholder="Enter your password"
              className="w-full rounded-xl border border-slate-700 bg-slate-950/70 px-4 py-3.5 text-white outline-none transition placeholder:text-slate-500 focus:border-indigo-400 focus:ring-4 focus:ring-indigo-500/15 disabled:cursor-not-allowed disabled:opacity-50"
            />
          </div>

          {/* Submit button */}
          <SubmitButton />

          {/* Status messages */}
          {state.msg && (
            <p
              role="status"
              className="rounded-xl border border-emerald-500/20 bg-emerald-500/10 p-3 text-sm text-emerald-300"
            >
              {state.msg}
            </p>
          )}

          {state.error && (
            <p
              role="alert"
              className="rounded-xl border border-red-500/20 bg-red-500/10 p-3 text-sm text-red-300"
            >
              {state.error}
            </p>
          )}
        </form>

        {/* Switch mode */}
        <div className="mt-7 border-t border-white/10 pt-6 text-center">
          <p className="text-sm text-slate-400">
            {mode === "login"
              ? "Don't have an account?"
              : "Already have an account?"}
          </p>

          <button
            type="button"
            disabled={isPending}
            onClick={() => {
              setMode(mode === "login" ? "register" : "login");
            }}
            className="mt-2 font-semibold text-indigo-400 transition hover:text-indigo-300 disabled:opacity-50"
          >
            {mode === "login"
              ? "Create an account →"
              : "← Back to login"}
          </button>
        </div>

      </div>
    </div>
  );
};

export default AuthForm;
