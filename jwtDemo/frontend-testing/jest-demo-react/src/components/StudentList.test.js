import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import StudentList from "./StudentList";
import { deleteStudent, getStudents } from "../service/studentService.js";

jest.mock("../service/studentService.js", () => ({
  getStudents: jest.fn(),
  deleteStudent: jest.fn(),
}));

const sampleStudents = [
  { id: 1, name: "Rahul" },
  { id: 2, name: "Amit" },
  { id: 3, name: "Priya" },
];

describe("StudentList", () => {
  beforeEach(() => {
    jest.clearAllMocks();
    getStudents.mockResolvedValue(sampleStudents);
    deleteStudent.mockResolvedValue({ ok: true });
  });

  test("shows a loading message while students are being fetched", () => {
    getStudents.mockReturnValue(new Promise(() => {}));

    render(<StudentList />);

    expect(screen.getByRole("status")).toHaveTextContent("Loading students...");
  });

  test("displays the students returned by the service", async () => {
    render(<StudentList />);

    expect(await screen.findByText("Rahul")).toBeInTheDocument();
    expect(screen.getByText("Amit")).toBeInTheDocument();
    expect(screen.getByText("Priya")).toBeInTheDocument();
    expect(getStudents).toHaveBeenCalledTimes(1);
  });

  test("shows an empty state when no students are returned", async () => {
    getStudents.mockResolvedValue([]);

    render(<StudentList />);

    expect(await screen.findByText("No students found.")).toBeInTheDocument();
  });

  test("shows an error when students cannot be loaded", async () => {
    getStudents.mockRejectedValue(new Error("Network error"));

    render(<StudentList />);

    expect(await screen.findByRole("alert")).toHaveTextContent("Network error");
  });

  test("deletes a student and removes them from the list", async () => {
    render(<StudentList />);

    fireEvent.click(await screen.findByRole("button", { name: "Delete Rahul" }));

    await waitFor(() => expect(deleteStudent).toHaveBeenCalledWith(1));
    expect(screen.queryByText("Rahul")).not.toBeInTheDocument();
  });

  test("keeps the student and shows an error when deletion fails", async () => {
    deleteStudent.mockRejectedValue(new Error("Delete failed"));

    render(<StudentList />);
    fireEvent.click(await screen.findByRole("button", { name: "Delete Rahul" }));

    expect(await screen.findByRole("alert")).toHaveTextContent("Delete failed");
    expect(screen.getByText("Rahul")).toBeInTheDocument();
  });
});
