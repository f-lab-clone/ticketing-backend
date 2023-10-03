const { faker } = require('@faker-js/faker')

const defaultValue = (value) => () => value
class Field {
    constructor(name, generate) {
        this.name = name;
        this.generate = generate;
    }
}

class Table {
    constructor(name, fields) {
        this.name = name;
        this.fields = fields;
    }
}

module.exports = [
    new Table('event', [
        new Field('name', faker.music.songName),
        new Field('start_time', faker.date.recent),
        new Field('end_time', faker.date.future),
        new Field('reservation_start_time', faker.date.recent),
        new Field('reservatino_end_time', faker.date.future),
        new Field('max_reservation_count', () => faker.number.int(1000)),
        new Field('current_reservation_count', defaultValue(0)),
    ])
]
