const path = require('path')

const GENERATE_COUNT = process.argv[2]
const GENERATE_PER_LOOP = 10000

if (GENERATE_COUNT == null) {
    console.error('Please input generate count')
    process.exit(1)
}

const FORLDER_NAME = path.join(__dirname, '../')
module.exports = {
    FORLDER_NAME,
    SQL_FILE_NAME: FORLDER_NAME + '/initdata.sql',
    GENERATE_COUNT,
    GENERATE_PER_LOOP,
}