const getRandomByRange = (max) => Math.floor(Math.random() * max);

export const getOneFromList = (list) => list[getRandomByRange(list.length)];

export const isSuccess = (r) => r.status >= 200 && r.status < 300;
export const isFail = (r) => !isSuccess(r);