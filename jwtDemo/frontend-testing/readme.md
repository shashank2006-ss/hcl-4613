# Testing

Software testing is commonly grouped into three levels:

1. **Unit testing** checks a small piece of code in isolation.
2. **Integration testing** checks that multiple parts work together.
3. **End-to-end testing** checks a complete user flow through the application.

## Node.js built-in test runner

Node.js includes a built-in test runner, available through the `node:test` module. Create a simple Node.js project:

```sh
mkdir node-test
cd node-test
npm init -y
```

Create `app.js`:

```js
export const greet = (name) => {
  return `Hello, ${name}`;
};
```

The `export` keyword makes `greet` available to other modules. You can import and test it from another file.

## Jest with React and Vite

Create a React app with Vite:

```sh
npm create vite@latest
```

From the project directory, install Jest, Babel, and the React Testing Library packages as development dependencies:

```sh
npm install --save-dev jest babel-jest @babel/core @babel/preset-env @babel/preset-react
npm install --save-dev jest-environment-jsdom @testing-library/react @testing-library/jest-dom @testing-library/user-event
npm install --save-dev identity-obj-proxy
```

Add the following configuration files:

1. `babel.config.js` in the project root
2. `jest.config.js` in the project root
3. `src/setupTests.js`

Update the `scripts` section in `package.json`:

```json
"scripts": {
  "dev": "vite",
  "test": "jest",
  "test:watch": "jest --watch",
  "test:coverage": "jest --coverage",
  "build": "vite build",
  "lint": "eslint .",
  "preview": "vite preview"
}
```
