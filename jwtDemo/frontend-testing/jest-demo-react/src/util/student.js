export const isEligibleForExam = (attendance) => {
  if(attendance<0 || attendance>100){
    throw new Error("Invalid attendance");
  }
  return attendance >= 75;
};
