const fs = require('fs')
const { generateRaws } = require('./schema.js')
const config = require('./config.js')

const generateSQL = (table, data) => {
    if (data.length === 0) {
        return ""
    }
    const sql = []

    for (const row of data) {
        const values = Object.values(row).map(v => `"${v}"`)
        const sqlRow = `(${values.join(', ')})`
        sql.push(sqlRow)
    }

    const keys = Object.keys(data[0])
    let result = `INSERT INTO ${table.name} (${keys.join(', ')}) VALUES `
    result += sql.join(', ')
    result += ';'
    return result
}
const writeSqlFile = (table, data) => {
    const sqlString = `\n${generateSQL(table, data)}`
    fs.appendFileSync(config.SQL_FILE_NAME, sqlString);
}


module.exports = (table) => {
    fs.appendFileSync(config.SQL_FILE_NAME, `\n\n-- ${table.name}`)

    console.log(`Generating ${table.name}...`)

    let total = 0

    while (total < config.GENERATE_COUNT) {
        const count = Math.min(config.GENERATE_PER_LOOP, config.GENERATE_COUNT - total)
        const raws = generateRaws(table, count, total)
        writeSqlFile(table, raws)
        total += raws.length
    }
    
    console.log(`Finish Generating ${table.name}: ${total}`)
}