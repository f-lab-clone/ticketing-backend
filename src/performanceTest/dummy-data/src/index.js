const fs = require('fs')
const generate = require('./generate.js')
const config = require('./config.js')
const { tables } = require('./schema.js')


try {
    fs.unlinkSync(SQL_FILE_NAME)
} catch (e) {

}

fs.appendFileSync(config.SQL_FILE_NAME, `USE ticketingdb;`)

for (const table of tables) {
    generate(table)
}