import { add, subtract, multiply, divide } from './math.js';

describe('math utility functions', () => {
  describe('add', () => {
    test('adds two numbers', () => {
      expect(add(2, 3)).toBe(5);
    });
  });

  describe('subtract', () => {
    test('subtracts the second number from the first', () => {
      expect(subtract(5, 3)).toBe(2);
    });
  });

  describe('multiply', () => {
    test('multiplies two numbers', () => {
      expect(multiply(4, 3)).toBe(12);
    });
  });

  describe('divide', () => {
    test('divides the first number by the second', () => {
      expect(divide(12, 3)).toBe(4);
    });
  });
});
