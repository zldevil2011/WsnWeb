const path = require('path');

module.exports = {
    entry: './src/index.js',
    output:  {
        path: path.resolve(__dirname, 'public'),
        filename: 'bundle.js'
    },
    module: {
        rules: [
            {
                test: /\.js$/,
                exclude: /(node_modules|bower_components)/,
                use: {
                  loader: 'babel-loader',
                  options: {
                    presets: ['es2015', 'react']
                  }
                }
            },
            {
                test: /\.css$/,
                use: [ 'style-loader', 'css-loader' ]
            },
            {
                test: /\.less$/,
                use: [{
                     loader: "style-loader" // creates style nodes from JS strings
                 }, {
                     loader: "css-loader" // translates CSS into CommonJS
                 }, {
                     loader: "less-loader" // compiles Less to CSS
                 }]
            },
            {
                test: /\.svg$/,
                loader: 'svg-sprite-loader'
              }
        ]
    },
    resolve: {		
        mainFiles: ["index.web","index"],// 这里哦
        modules: [path.resolve(__dirname, "src"), "node_modules"],
        extensions: ['.web.tsx', '.web.ts', '.web.jsx', '.web.js', '.ts', '.tsx', '.js', '.jsx', '.json'],
    }
}