const getRandomByRange = (max) => Math.floor(Math.random() * max);

export const getOneFromList = (list) => list[getRandomByRange(list.length)];
