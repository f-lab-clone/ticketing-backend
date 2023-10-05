const tables = require('./schema.js')
const fs = require('fs')

const GENERATE_COUNT = process.argv[2]
const GENERATE_PER_LOOP = 1000000
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
                value = value.toISOString().replace('T', ' ').replace('Z', '')
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
        const values = Object.values(row).map(v => `"${v}"`)
        const sqlRow = `INSERT INTO ${table.name} (${keys.join(', ')}) VALUES (${values.join(', ')});`
        sql.push(sqlRow)
    }
    return sql
}

const writeFileBufferSync = (sqlString) => {
    fs.appendFileSync(SQL_FILE_NAME, sqlString);
}

try {
    fs.unlinkSync(SQL_FILE_NAME)
} catch (e) {

}

writeFileBufferSync(`USE ticketingdb;`)
for (const table of tables) {
    console.log(`Generating ${table.name}...`)
    writeFileBufferSync(`\n\n-- ${table.name}`)

    // loop GENERATE_COUNT / 100000 times
    // generate 100000 rows each time
    let count = GENERATE_COUNT - GENERATE_PER_LOOP
    let total = 0
    while (count >= 0) {
        const data = generateData(table, GENERATE_PER_LOOP)
        const sqls = generateSQL(table, data)
        writeFileBufferSync(`\n${sqls.join('\n')}`)
        console.log(`Make ${table.name}! raws: ${data.length}`)
        total += data.length
        count -= GENERATE_PER_LOOP
    }
    count += GENERATE_PER_LOOP
    if (count > 0) {
        const data = generateData(table, count)
        const sqls = generateSQL(table, data)
        writeFileBufferSync(`\n${sqls.join('\n')}`)
        console.log(`Make ${table.name}! raws: ${data.length}`)
        total += data.length
    }
    console.log(`Finisehd ${table.name}! total: ${total}`)
}