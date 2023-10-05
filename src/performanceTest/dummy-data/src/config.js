const path = require('path')

const GENERATE_COUNT = process.argv[2]
const GENERATE_PER_LOOP = 3

if (GENERATE_COUNT == null) {
    console.error('Please input generate count')
    process.exit(1)
}

module.exports = {
    SQL_FILE_NAME: path.join(__dirname, '../../initdb/sqls/initdata.sql'),
    GENERATE_COUNT,
    GENERATE_PER_LOOP,
}