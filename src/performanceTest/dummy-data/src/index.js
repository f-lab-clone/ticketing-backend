const fs = require('fs')
const generate = require('./generate.js')
const config = require('./config.js')
const { tables } = require('./schema.js')


try {
    fs.unlinkSync(config.SQL_FILE_NAME)
} catch (e) {

}

fs.appendFileSync(config.SQL_FILE_NAME, `
USE ticketingdb;
SET unique_checks=0;
SET foreign_key_checks=0;
`)

for (const table of tables) {
    generate(table)
}

fs.appendFileSync(config.SQL_FILE_NAME, `
\n
SET unique_checks=1;
SET foreign_key_checks=1;
`)