const express = require('express');
const app = express();
const bodyParser = require('body-parser');
const bitcoin = require('bitcoinjs-lib');
const axios = require('axios');

const bcypher = require('blockcypher');

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
    extended: true
}));


app.get('/', function (req, res) {
    return res.send({ error: true, message: 'Server Online' })
});

app.post('/v1/transaction/btc', function (req, res) {

    var Toaddress = req.body.toaddress;
    var myaddr = req.body.address;
    var pk = req.body.algo;
    var amountToSend = req.body.amount;
    var fees = req.body.fees;
    var balance = req.body.balance;

    var sb = require('satoshi-bitcoin');//Convertion to satoshi and bitcoin

    var mainnet = bitcoin.networks.bitcoin;
    var amt = sb.toSatoshi(amountToSend);
    var bals = sb.toSatoshi(balance);

    const Bitcoin3 = require("bitcoin3");
    const btc3     = new Bitcoin3("mainnet");
    btc3.setProvider("blockcypher");

    btc3.getUtxo(myaddr)//Start to get uxtos
    .then( utxos => {

    var txb =  new bitcoin.TransactionBuilder(mainnet);

    var count = Object.keys(utxos).length;

    //input
    for ( var i = 0; i < utxos.length; i++) {
        txb.addInput(utxos[i].txid, utxos[i].vout);
     }
    //output
    var leftover = bals - amt;
    var bal =  leftover - fees;
console.log(bal);
    //if left over is 0 do here
    if (bal == 0){
      txb.addOutput(Toaddress,amt);//toaddress
    }else{

      txb.addOutput(Toaddress,amt);//toaddress
      txb.addOutput(myaddr,bal);//left over
    }

    var WIF = pk;
    var keypairSpend = bitcoin.ECPair.fromWIF(WIF, mainnet);

    for (var i = 0; i < count; i += 1) {
        txb.sign(i, keypairSpend);
        console.log(i + " "+ keypairSpend);
     }

    var tx = txb.build();
    var texhex = tx.toHex();
      //return res.send({ error: false, data: texhex, message: 'success' });
    console.log(texhex);
      //Do broadcast the transaction
    var bcapi = new bcypher('btc','main','f52a530143a746ae91222dca00ef04ff');
    function printResponse(err, data) {
       if (err !== null) {
          console.log(err);
         return res.send({ error: true, err, message: 'error' });
       } else {
         console.log(data);
          return res.send({ error: false, data, message: 'success' });
      }
     }

     bcapi.pushTX(texhex,printResponse);
    })
    .catch(error => {
      return res.send({ error: true, data: error, message: 'error' });
      console.log(error);
    });

});


app.post('/v1/btc', function (req, res) {

    var bip39 = require('bip39') // npm i -S bip39
    var crypto = require('crypto')
    var  randomBytes = crypto.randomBytes(16) // 128 bits is enough
    var mnemonic = bip39.entropyToMnemonic(randomBytes.toString('hex'))

    var main = bitcoin.networks.bitcoin;
    var keypair = bitcoin.ECPair.makeRandom({network:main});
    var addr = keypair.getAddress();
    var pk = keypair.toWIF();
    return res.send({ error: false, address: addr, keys: pk, seed: mnemonic, message: 'success' });
  });


app.get('/v1/fees', function (req, res) {
   axios.get('https://api.blockcypher.com/v1/btc/main') .then(function(response){
    console.log(response.data.low_fee_per_kb);
    return res.send({data:response.data.low_fee_per_kb});
  });
  });

app.get('/v1/otp', function (req, res) {
    const uniqueRandom = require('unique-random');
    const random = uniqueRandom(100000, 999999);
    console.log(random());
    return res.send({data:random()});
  });


app.all("*", function (req, res, next) {
 return res.send('page not found');
    next();
});

app.listen(8081, function () {
    console.log('Node app is running on port 8081');
});

module.exports = app;

