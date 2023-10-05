import { randomIntBetween } from 'https://jslib.k6.io/k6-utils/1.2.0/index.js';
import exec from 'k6/execution';

const getRandomByRange = (max) => Math.floor(Math.random() * max);

export const getOneFromList = (list) => list[getRandomByRange(list.length)];

export const isSuccess = (r) => r.status >= 200 && r.status < 300;
export const isFail = (r) => !isSuccess(r);
export const randomInt = (start, end) => randomIntBetween(start, end);

export const getUserIDFromExec = (VU_COUNT) => exec.vu.idInTest + (VU_COUNT * exec.vu.iterationInScenario)