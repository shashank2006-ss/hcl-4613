import './App.css';
import { BrowserRouter, Routes, Route } from 'react-router-dom';

import Home from './component/Home';
import Login from './component/Login';
import Registration from './component/Registration';
import Dashboard from './component/Dashboard';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/Home" element={<Home />} />
        <Route path="/Login" element={<Login />} />
        <Route path="/Dashboard" element={<Dashboard />} />
        <Route path="/Registration" element={<Registration />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
