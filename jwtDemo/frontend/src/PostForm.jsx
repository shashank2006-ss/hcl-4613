
import { useActionState } from "react";
import SubmitButton from "./SubmitButton";
import api from "./api";

const PostForm = ({ onCreated }) => {
  const submitAction = async (prev, formData) => {
    const title = formData.get("title")?.trim();
    const content = formData.get("content")?.trim();

    if (!title || !content) {
      return {
        error: "Title and content are required.",
        msg: "",
        title: title || "",
        content: content || "",
      };
    }

    try {
      await api.post("/posts", { title, content });
      await onCreated?.();

      return {
        error: null,
        msg: "Post created successfully!",
        title: "",
        content: "",
      };
    } catch (error) {
      return {
        error:
          error.response?.data?.error ||
          error.response?.data?.message ||
          "Failed to create post. Please try again.",
        msg: "",
        title,
        content,
      };
    }
  };

  const [state, formAction, isPending] = useActionState(
    submitAction,
    {
      error: null,
      msg: "",
      title: "",
      content: "",
    }
  );

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-950 via-slate-900 to-indigo-950 px-4 py-10 sm:px-6">
      <div className="mx-auto max-w-xl">

        {/* Card header */}
        <div className="mb-6">
          <p className="text-sm font-semibold uppercase tracking-[0.2em] text-indigo-400">
            Community
          </p>
          <h1 className="mt-2 text-3xl font-extrabold tracking-tight text-white sm:text-4xl">
            Share your thoughts.
          </h1>
          <p className="mt-3 text-sm leading-6 text-slate-400">
            Create a post and start a conversation with your community.
          </p>
        </div>

        {/* Post form card */}
        <div className="rounded-3xl border border-white/10 bg-white/5 p-5 shadow-2xl backdrop-blur-xl sm:p-8">

          <div className="mb-7 flex items-center gap-4 border-b border-white/10 pb-6">
            <div className="flex h-12 w-12 items-center justify-center rounded-2xl bg-indigo-500/20 text-indigo-300">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="25"
                height="25"
                viewBox="0 0 24 24"
                fill="none"
                stroke="currentColor"
                strokeWidth="1.8"
                strokeLinecap="round"
                strokeLinejoin="round"
              >
                <path d="M12 20h9" />
                <path d="M16.5 3.5a2.12 2.12 0 013 3L9 17l-4 1 1-4Z" />
              </svg>
            </div>

            <div>
              <h2 className="text-xl font-bold text-white">
                Write a Post
              </h2>
              <p className="mt-1 text-sm text-slate-400">
                What's on your mind today?
              </p>
            </div>
          </div>

          <form action={formAction} className="space-y-6">

            {/* Title */}
            <div>
              <label
                htmlFor="title"
                className="mb-2 block text-sm font-semibold text-slate-200"
              >
                Post title
              </label>

              <input
                type="text"
                name="title"
                id="title"
                placeholder="Give your post a title..."
                defaultValue={state.title}
                required
                disabled={isPending}
                className="w-full rounded-xl border border-slate-700 bg-slate-950/70 px-4 py-3.5 text-white outline-none transition placeholder:text-slate-500 focus:border-indigo-400 focus:ring-4 focus:ring-indigo-500/15 disabled:cursor-not-allowed disabled:opacity-50"
              />
            </div>

            {/* Content */}
            <div>
              <label
                htmlFor="content"
                className="mb-2 block text-sm font-semibold text-slate-200"
              >
                Content
              </label>

              <textarea
                name="content"
                id="content"
                placeholder="Write something interesting..."
                rows={6}
                defaultValue={state.content}
                required
                disabled={isPending}
                className="w-full resize-y rounded-xl border border-slate-700 bg-slate-950/70 px-4 py-3.5 leading-7 text-white outline-none transition placeholder:text-slate-500 focus:border-indigo-400 focus:ring-4 focus:ring-indigo-500/15 disabled:cursor-not-allowed disabled:opacity-50"
              />

              <p className="mt-2 text-right text-xs text-slate-500">
                Share an update with your community
              </p>
            </div>

            {/* Submit */}
            <SubmitButton />

            {/* Success message */}
            {state.msg && (
              <div
                role="status"
                className="flex items-start gap-3 rounded-xl border border-emerald-500/20 bg-emerald-500/10 p-4 text-sm text-emerald-300"
              >
                <span className="text-lg">✓</span>
                <p>{state.msg}</p>
              </div>
            )}

            {/* Error message */}
            {state.error && (
              <div
                role="alert"
                className="flex items-start gap-3 rounded-xl border border-red-500/20 bg-red-500/10 p-4 text-sm text-red-300"
              >
                <span className="text-lg">!</span>
                <p>{state.error}</p>
              </div>
            )}

          </form>
        </div>

        <p className="mt-6 text-center text-xs text-slate-500">
          Be respectful. Keep the conversation meaningful.
        </p>
      </div>
    </div>
  );
};

export default PostForm;
