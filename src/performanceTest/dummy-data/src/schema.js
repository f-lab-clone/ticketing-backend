const { faker } = require('@faker-js/faker')

const defaultValue = (value) => () => value

class Field {
    constructor(name, generator) {
        this.name = name;
        this.generator = generator
    }
}

class Table {
    constructor(name, fields) {
        this.name = name;
        this.fields = fields;
    }
}

const event = new Table('event', [
    new Field('title', faker.music.songName),
    new Field('date', faker.date.recent),
    new Field('reservation_start_time', faker.date.recent),
    new Field('reservation_end_time', faker.date.future),
    new Field('max_attendees', () => faker.number.int(1000)),
    new Field('current_reservation_count', defaultValue(0)),
])

const generateRaw = (table, index) => {
    const row = {}
    for (const filed of table.fields) {
        let value = filed.generator()
        
        if (value instanceof Date) {
            value = value.toISOString().replace('T', ' ').replace('Z', '')
        }
        row[filed.name] = value
    }
    return row
}
const generateRaws = (table, count, indexStart) => {
    const data = []
    for (let i = 0; i < count; i++) {
        data.push(generateRaw(table, indexStart + i))
    }
    return data
}

module.exports = {
    tables: [event],
    generateRaw,
    generateRaws,
}