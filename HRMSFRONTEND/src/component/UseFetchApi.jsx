import { useState, useEffect } from 'react';

const UseFetchApi = () => {
  const [data, setData] = useState('');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch('http://localhost:8080/');

        if (!response.ok) {
          throw new Error(`HTTP error: ${response.status}`);
        }

        const message = await response.text();
        setData(message);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  if (loading) return <h2>Loading...</h2>;

  if (error) return <h2>Error: {error}</h2>;

  return (
    <div>
      <h1>HRMS Application</h1>
      <h2>{data}</h2>
    </div>
  );
};

export default UseFetchApi;