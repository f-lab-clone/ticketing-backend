const tables = require('./schema.js')
const fs = require('fs')

const GENERATE_COUNT = process.argv[2]
if (GENERATE_COUNT == null) {
    console.error('Please input generate count')
    process.exit(1)
}
const SQL_FILE_NAME = 'initdata.sql'
const generateData = (table, count) => {
    const data = []
    for (let i = 0; i < count; i++) {
        const row = {}
        for (const filed of table.fields) {
            let value = filed.generate()
            if (value instanceof Date) {
                value = value.toISOString()
            }
            row[filed.name] = value
        }
        data.push(row)
    }
    return data
}

const generateSQL = (table, data) => {
    const sql = []
    for (const row of data) {
        const keys = Object.keys(row)
        const values = Object.values(row).map(v => `'${v}'`)
        const sqlRow = `INSERT INTO ${table.name} (${keys.join(', ')}) VALUES (${values.join(', ')});`
        sql.push(sqlRow)
    }
    return sql
}

const writeFileBufferSync = (sqlString) => {
    fs.appendFileSync(SQL_FILE_NAME, sqlString);
}

fs.unlinkSync(SQL_FILE_NAME)

writeFileBufferSync(`USE ticketingdb;`)
for (const table of tables) {
    console.log(`Generating ${table.name}...`)
    const data = generateData(table, GENERATE_COUNT)
    const sqls = generateSQL(table, data)
    writeFileBufferSync(`\n\n-- ${table.name}`)
    writeFileBufferSync(`\n${sqls.join('\n')}`)
    console.log(`Finisehd ${table.name}! raws: ${data.length}`)
}