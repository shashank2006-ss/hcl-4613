import './App.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';

import UseFetchApi from './component/UseFetchApi';
import Home from './component/Home';
import Login from './component/Login';
import Registration from './component/Registration';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<UseFetchApi />} />
        <Route path="/Home" element={<Home />} />
        <Route path="/Login" element={<Login />} />
        <Route path="/Registration" element={<Registration />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;