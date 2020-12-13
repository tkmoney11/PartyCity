const path = require('path');

module.exports ={
    entry: './src/js/componentInjector.js',
    output: {
        path: __dirname+ '/dist'+ '/scripts',
        filename:'bundle.js'
    },
    devServer:{
        contentBase: './src',
        port:5500
    }
}