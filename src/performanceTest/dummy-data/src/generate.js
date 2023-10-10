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

const sql = (tables) => {
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

    fs.appendFileSync(config.SQL_FILE_NAME, `
    \n
    SET unique_checks=1;
    SET foreign_key_checks=1;
    `)
}

const csv = (tables) => {
    for (const table of tables) {
        const FILE_NAME = config.FORLDER_NAME + `/${table.name}.csv`
        try {
            fs.unlinkSync(FILE_NAME)
        } catch (e) {
    
        }
        fs.appendFileSync(FILE_NAME, `${table.fields.map(e => e.name).join(',')}\n`);
        
        console.log(`Generating ${table.name}...`)
    
        let total = 0
    
        while (total < config.GENERATE_COUNT) {
            const count = Math.min(config.GENERATE_PER_LOOP, config.GENERATE_COUNT - total)
            const raws = generateRaws(table, count, total)
            fs.appendFileSync(FILE_NAME, raws.map(r => Object.values(r).join(',')).join('\n'))
            fs.appendFileSync(FILE_NAME, '\n')
            total += raws.length
        }
        
        console.log(`Finish Generating ${table.name}: ${total}`)
    }
}
module.exports = {
    sql, 
    csv,
}