
import { useState, useEffect } from 'react';

function SampleCode({ empdata }) {
  const [counter, setCounter] = useState(20);
  const [pointer, setPointer] = useState(110);

  useEffect(() => {
    console.log('Component rendered or counter changed');
    console.log('Counter value:', counter);
    console.log('Pointer value:', pointer);
  }, [ pointer]);


  function changeCounter() {
    setCounter(counter + 2);
    
  }

  function changePointer() {
    setPointer(pointer - 5);
  }

  return (
    <div>
      <h2>Counter Value = {counter}</h2>
      <h2>Pointer Value = {pointer}</h2>
      <button onClick={changeCounter}>Increment</button>
      <button onClick={changePointer}>Decrement</button>
    </div>
  );
}

export default SampleCode;
