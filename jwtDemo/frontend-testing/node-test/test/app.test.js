import { greet } from '../app.js';
import {test} from 'node:test';
import assert from "node:assert";
describe ("order processing functions", () => {
    test('paymentInfo function should return payment processing message', () => {
        const mockedProcessPayment = mock.fn((amount) => {
            console.log(`i am a mock  process payment function`);
            return {id:"123",amount};
        });
        const expected = 'id:123,amount:15000 ';
        const actual = processOrder({ amount: 15000 }, { processPayment: mockedProcessPayment });
        assert.strictEqual(actual, expected);
    });
});
describe("greeting functions", () => {
    test('greet function should return a greeting message', () => {
        const expected = 'Hello, world!';
        const actual = greet('world');
        assert.strictEqual(actual, expected);
    });

test('greetInHindi function should return a greeting message in Hindi', () => {
    const expected = 'नमस्ते, दुनिया!';
    const actual = greetInHindi('दुनिया');
    assert.strictEqual(actual, expected);
});
});
