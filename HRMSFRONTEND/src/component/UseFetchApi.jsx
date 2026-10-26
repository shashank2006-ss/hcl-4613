
import { useState, useEffect } from 'react';

const UseFetchApi = () => {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch(
          'http://localhost:8080/'
        );
        const data = await response.json();

        if (!response.ok) {
          throw new Error('Network response was not ok');
        }

        const jsonData = await response.json();
        setData(jsonData);
      } catch (err) {
        setError(err);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  if (loading) {
    return <h2>Loading...</h2>;
  }

  if (error) {
    return <h2>Error: {error.message}</h2>;
  }

  return (
    <div>
      <h2>Fetched Products</h2>

      {data.map((product) => (
        <div key={product.id}>
          <h3>{product.title}</h3>
          <p>Price: ${product.price}</p>
          <p>{product.description}</p>
          <img
            src={product.image}
            alt={product.title}
            width="100"
          />
          <hr />
        </div>
      ))}
    </div>
  );
};

export default UseFetchApi;
