import React, { useState } from 'react';

function Registration() {
  const [employee, setEmployee] = useState({
    emp_id: '',
    emp_name: '',
    designation: '',
    stack: ''
  });

  const [message, setMessage] = useState('');
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setEmployee({
      ...employee,
      [e.target.name]: e.target.value
    });
  };

  const sendData = async (e) => {
    e.preventDefault();
    setLoading(true);
    setMessage('');

    const employeeData = {
      emp_id: Number(employee.emp_id),
      emp_name: employee.emp_name,
      designation: employee.designation,
      stack: employee.stack
    };

    try {
      const response = await fetch(
        'http://localhost:8080/v1/user/addemp',
        {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(employeeData)
        }
      );

      const result = await response.text();

      if (!response.ok) {
        throw new Error(result || `Request failed: ${response.status}`);
      }

      setMessage(result);

      setEmployee({
        emp_id: '',
        emp_name: '',
        designation: '',
        stack: ''
      });
    } catch (error) {
      console.error('Registration error:', error);
      setMessage(`Registration failed: ${error.message}`);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container mt-4">
      <h2>Employee Registration</h2>

      <form onSubmit={sendData}>
        <div className="form-group mb-3">
          <label htmlFor="emp_id">Employee ID</label>
          <input
            type="number"
            className="form-control"
            id="emp_id"
            name="emp_id"
            value={employee.emp_id}
            onChange={handleChange}
            placeholder="Enter employee ID"
            required
          />
        </div>

        <div className="form-group mb-3">
          <label htmlFor="emp_name">Name</label>
          <input
            type="text"
            className="form-control"
            id="emp_name"
            name="emp_name"
            value={employee.emp_name}
            onChange={handleChange}
            placeholder="Enter name"
            required
          />
        </div>

        <div className="form-group mb-3">
          <label htmlFor="designation">Designation</label>
          <input
            type="text"
            className="form-control"
            id="designation"
            name="designation"
            value={employee.designation}
            onChange={handleChange}
            placeholder="Enter designation"
            required
          />
        </div>

        <div className="form-group mb-3">
          <label htmlFor="stack">Tech Stack</label>
          <input
            type="text"
            className="form-control"
            id="stack"
            name="stack"
            value={employee.stack}
            onChange={handleChange}
            placeholder="Enter tech stack"
            required
          />
        </div>

        <button
          type="submit"
          className="btn btn-primary"
          disabled={loading}
        >
          {loading ? 'Registering...' : 'Register Employee'}
        </button>
      </form>

      {message && <p className="mt-3">{message}</p>}
    </div>
  );
}

export default Registration;