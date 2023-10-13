const fs = require('fs')
const generate = require('./generate.js')
const config = require('./config.js')
const { tables } = require('./schema.js')

generate.csv(tables)