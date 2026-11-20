import { useEffect, useState } from "react";
import { deleteStudent, getStudents } from "../service/studentService.js";

const getErrorMessage = (error, fallback) =>
  error instanceof Error ? error.message : fallback;

const StudentList = () => {
  const [students, setStudents] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [deletingStudentId, setDeletingStudentId] = useState(null);

  useEffect(() => {
    let isMounted = true;

    const loadStudents = async () => {
      try {
        const result = await getStudents();
        if (!Array.isArray(result)) {
          throw new Error("Invalid student data received.");
        }
        if (isMounted) setStudents(result);
      } catch (loadError) {
        if (isMounted) {
          setError(getErrorMessage(loadError, "Failed to load students."));
        }
      } finally {
        if (isMounted) setLoading(false);
      }
    };

    loadStudents();
    return () => {
      isMounted = false;
    };
  }, []);

  const handleDelete = async (studentId) => {
    setError("");
    setDeletingStudentId(studentId);

    try {
      const response = await deleteStudent(studentId);
      if (response?.ok === false) {
        throw new Error("Failed to delete student.");
      }
      setStudents((currentStudents) =>
        currentStudents.filter((student) => student.id !== studentId),
      );
    } catch (deleteError) {
      setError(getErrorMessage(deleteError, "Failed to delete student."));
    } finally {
      setDeletingStudentId(null);
    }
  };

  if (loading) {
    return <p role="status">Loading students...</p>;
  }

  return (
    <section aria-labelledby="student-list-heading">
      <h2 id="student-list-heading">Student List</h2>

      {error && <p role="alert">{error}</p>}

      {students.length === 0 ? (
        <p>No students found.</p>
      ) : (
        <ul>
          {students.map((student) => (
            <li key={student.id}>
              {student.name}
              <button
                type="button"
                aria-label={`Delete ${student.name}`}
                disabled={deletingStudentId === student.id}
                onClick={() => handleDelete(student.id)}
              >
                {deletingStudentId === student.id ? "Deleting..." : "Delete"}
              </button>
            </li>
          ))}
        </ul>
      )}
    </section>
  );
};

export default StudentList;
