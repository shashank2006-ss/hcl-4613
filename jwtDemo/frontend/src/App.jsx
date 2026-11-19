import { useEffect, useState } from "react";
import api from "./api";
import AuthForm from "./AuthForm";
import PostForm from "./PostForm";

const App = () => {
  const [user, setUser] = useState(localStorage.getItem("username"));
  const [posts, setPosts] = useState([]);

  const loadPosts = async () => {
    const { data } = await api.get("/posts");
    setPosts(data);
  };

  useEffect(() => {
    loadPosts();
  }, []);

  const logout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("username");
    setUser(null);
  };

  return (
    <div className="min-h-screen bg-slate-50 text-slate-800 font-sans">
      {/* Header */}
      <header className="sticky top-0 z-10 bg-white/80 backdrop-blur-md border-b border-slate-200">
        <div className="max-w-3xl mx-auto px-4 sm:px-6 h-16 flex items-center justify-between">
          <h1 className="text-xl font-bold tracking-tight text-indigo-600">
            JWT Blog Demo
          </h1>

          {user && (
            <button
              onClick={logout}
              className="px-4 py-1.5 text-sm font-medium text-slate-600 bg-slate-100 hover:bg-red-50 hover:text-red-600 rounded-lg transition-colors cursor-pointer"
            >
              Logout
            </button>
          )}
        </div>
      </header>

      <main className="max-w-3xl mx-auto px-4 sm:px-6 py-8 space-y-10">
        {/* Authentication and Post Form */}
        <section className="bg-white p-6 rounded-2xl shadow-sm border border-slate-200">
          {user ? (
            <div className="space-y-4">
              <div className="flex items-center justify-between border-b border-slate-100 pb-3">
                <p className="text-sm text-slate-600">
                  Logged in as{" "}
                  <span className="font-semibold text-slate-900">{user}</span>
                </p>
                <span className="inline-flex items-center gap-1.5 text-xs font-medium text-emerald-700 bg-emerald-50 px-2.5 py-0.5 rounded-full">
                  <span className="w-1.5 h-1.5 rounded-full bg-emerald-500"></span>
                  Active
                </span>
              </div>

              <PostForm onCreated={loadPosts} />
            </div>
          ) : (
            <AuthForm onLogin={setUser} />
          )}
        </section>

        {/* All Posts */}
        <section className="space-y-6">
          <div className="flex items-center justify-between">
            <h2 className="text-lg font-bold text-slate-900">All Posts</h2>

            <span className="text-xs font-medium bg-indigo-50 text-indigo-700 px-3 py-1 rounded-full">
              {posts.length} {posts.length === 1 ? "Post" : "Posts"}
            </span>
          </div>

          {posts.length === 0 && (
            <div className="text-center py-12 bg-white rounded-2xl border border-dashed border-slate-300">
              <p className="text-slate-500 text-sm">
                No posts yet. Be the first to publish one!
              </p>
            </div>
          )}

          <div className="space-y-4">
            {posts.map((p) => (
              <article
                key={p.id}
                className="bg-white p-6 rounded-2xl shadow-sm border border-slate-200 hover:border-slate-300 transition-all"
              >
                <h2 className="text-xl font-semibold text-slate-900 mb-2">
                  {p.title}
                </h2>

                <p className="text-slate-600 leading-relaxed whitespace-pre-line mb-4">
                  {p.content}
                </p>

                <div className="flex items-center justify-between pt-4 border-t border-slate-100 text-xs text-slate-500">
                  <span>
                    By{" "}
                    <span className="font-medium text-slate-700">
                      {p.author}
                    </span>
                  </span>

                  <time dateTime={p.createdAt}>
                    {new Date(p.createdAt).toLocaleString()}
                  </time>
                </div>
              </article>
            ))}
          </div>
        </section>
      </main>
    </div>
  );
};

export default App;