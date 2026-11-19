 export const greet = (name) => {
  return `Hello, ${name}!`;
};


export const greetInHindi = (name) => {
  return `नमस्ते, ${name}!`;
};
export const processOrder = (data, dependencies) => {
  const paymentInfo = dependencies.processPayment(data, dependencies);
  return  paymentInfo;
};
const processPayment = (amount) => {
  console.log(`i am original process payment function`);
  return { id: "123", amount };
};
