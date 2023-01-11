const express = require('express');
const app = express();
const bodyParser = require('body-parser');

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
    extended: true
}));

var Changelly = require('./lib.js');

var changelly = new Changelly(
  '', //Input your changelly API key and secret
  '' //Input your changelly secret
);

app.post('/v1/minimum', function (req, res) {

    var coin1 = req.body.coin1;
    var coin2 = req.body.coin2;

    changelly.getMinAmount(coin1, coin2, function(err, data) {
     if (err){
       console.log('Error getMinAmount', err);
     } else {
       console.log('getMinAmount', data);
       return res.send({ error: false, data, message: 'success' });
     }
     });
});

app.post('/v1/getXamount', function (req, res) {

    var coin1 = req.body.coin1;
    var coin2 = req.body.coin2;
    var amount = req.body.amount;

    changelly.getExchangeAmount(coin1, coin2, amount, function(err, data) {
    if (err){
     console.log('Error!', err);
    } else {
     console.log('getExchangeAmount', data);
     return res.send({ error: false, data, message: 'success' });
    }
  });
});

app.post('/v1/create', function (req, res) {

    var coin1 = req.body.coin1;
    var coin2 = req.body.coin2;
    var amount = req.body.amount;
    var address = req.body.address;
    var refund = req.body.refund;

    changelly.createTransaction(coin1, coin2, address, amount, undefined, function(err, data) {
    if (err){
       console.log('Error!', err);
    } else {
       console.log('createTransaction', data);
       return res.send({ error: false, data, message: 'success' });
    }
 });
});

app.post('/v1/getTransaction', function (req, res) {

    var currency = req.body.currency;
    var address = req.body.address;
    var limit = '10';
    var offset= '10';

    changelly.getTransactions(limit, offset, currency, address, 'null', function(err, data) {
    if (err){
       console.log('Error!', err);
    } else {
       console.log('getTransactions', data);
       return res.send({ error: false, data, message: 'success' });
    }
 });
});


app.get('/', function (req, res) {
    return res.send({ error: true, message: 'Server Online' })
});

app.all("*", function (req, res, next) {
    return res.send('page not found');
    next();
});

app.listen(8083, function () {
    console.log('Node app is running on port 8083');
});

module.exports = app;


