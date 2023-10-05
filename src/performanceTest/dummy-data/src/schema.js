const { faker } = require('@faker-js/faker')
const userpassword = require('./userpassword.js')
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
    new Field('title', (i) => faker.music.songName()),
    new Field('date', (i) => faker.date.recent()),
    new Field('reservation_start_time', (i) => faker.date.recent()),
    new Field('reservation_end_time', (i) => faker.date.future()),
    new Field('max_attendees', (i) => faker.number.int(1000)),
    new Field('current_reservation_count', (i) => defaultValue(0)),
    new Field('created_at', (i) => faker.date.recent()),
    new Field('updated_at', (i) => faker.date.recent()),
])
const user = new Table('user', [
    new Field('email', (i) => `K6-${i + 1}@email.com`),
    new Field('name', (i) => `${i + 1}`),
    new Field('pw', (i) => userpassword[i % 1000]),
    new Field('created_at', (i) => faker.date.recent()),
    new Field('updated_at', (i) => faker.date.recent()),
])

const generateRaw = (table, index) => {
    const row = {}
    for (const filed of table.fields) {
        let value = filed.generator(index)
        
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
    tables: [
        // event, 
        // user,
    ],
    generateRaw,
    generateRaws,
}