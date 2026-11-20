import { isEligibleForExam } from './student.js';

describe('isEligibleForExam', () => {
  test('returns false when attendance is below 75%', () => {
    expect(isEligibleForExam(74)).toBe(false);
  });

  test('returns true when attendance is exactly 75%', () => {
    expect(isEligibleForExam(75)).toBe(true);
  });

  test('returns true when attendance is above 75%', () => {
    expect(isEligibleForExam(90)).toBe(true);
  });

  test('accepts the lower valid boundary', () => {
    expect(isEligibleForExam(0)).toBe(false);
  });

  test('accepts the upper valid boundary', () => {
    expect(isEligibleForExam(100)).toBe(true);
  });

  test('throws an error for attendance below 0%', () => {
    expect(() => isEligibleForExam(-1)).toThrow('Invalid attendance');
  });

  test('throws an error for attendance above 100%', () => {
    expect(() => isEligibleForExam(101)).toThrow('Invalid attendance');
  });
});
