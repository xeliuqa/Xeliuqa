const express = require('express');
const app = express();
const bodyParser = require('body-parser');
Web3 = require('web3');
const mysql = require('mysql');

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
    extended: false
}));

const mc = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: 'Iagdtbr991700',
    database: 'wallet',
    port: 3306,
});

mc.connect();


app.get('/', function (req, res) {
    return res.send({ error: true, message: 'Server Online' })
});

        app.post('/v1/api', function (req, res) {

         if (typeof web3 !== 'undefined') {
            web3 = new Web3(web3.currentProvider);
        } else {
            web3 = new Web3(new Web3.providers.HttpProvider("https://mainnet.infura.io/v3/0826dfa67d21460cbfaef62a6c3c6220"));
        }
        
            var accounts= web3.eth.accounts.create();
            var address = accounts.address;
            var pkey = accounts.privateKey;

            if (error) throw error;
            return res.send({ error: false, data: address, key: pkey, message: 'success' });
         });

 app.post('/v1/send/eth', function (req, res) {

            let contractAddress = req.body.contract;
            let myAddress = req.body.address;
            let toAddress = req.body.toaddress;
            let amount = req.body.amount;
            let mykey = req.body.algo;
            let fees = req.body.fees;
            let coin_name = req.body.coin;

            var Tx = require('ethereumjs-tx');
            var Web3 = require('web3')
            var web3 = new Web3(new Web3.providers.HttpProvider('https://mainnet.infura.io/v3/0826dfa67d21460cbfaef62a6c3c6220'))

            let amt =  web3.utils.toWei(amount, 'ether') ;
            
            web3.eth.getTransactionCount(myAddress).then(function(v){
            console.log(coin_name);
            count = v

            
            var privateKey = new Buffer.from(mykey, 'hex');
            var abiArray;
            if (coin_name=="EtLyteT"){
               abiArray =  [{"constant":true,"inputs":[],"name":"name","outputs":[{"name":"","type":"string"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[{"name":"spender","type":"address"},{"name":"tokens","type":"uint256"}],"name":"approve","outputs":[{"name":"success","type":"bool"}],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[],"name":"totalSupply","outputs":[{"name":"","type":"uint256"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[{"name":"from","type":"address"},{"name":"to","type":"address"},{"name":"tokens","type":"uint256"}],"name":"transferFrom","outputs":[{"name":"success","type":"bool"}],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[],"name":"decimals","outputs":[{"name":"","type":"uint8"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":true,"inputs":[],"name":"_totalSupply","outputs":[{"name":"","type":"uint256"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":true,"inputs":[{"name":"tokenOwner","type":"address"}],"name":"balanceOf","outputs":[{"name":"balance","type":"uint256"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[],"name":"acceptOwnership","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[],"name":"owner","outputs":[{"name":"","type":"address"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":true,"inputs":[],"name":"symbol","outputs":[{"name":"","type":"string"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":true,"inputs":[{"name":"a","type":"uint256"},{"name":"b","type":"uint256"}],"name":"safeSub","outputs":[{"name":"c","type":"uint256"}],"payable":false,"stateMutability":"pure","type":"function"},{"constant":false,"inputs":[{"name":"to","type":"address"},{"name":"tokens","type":"uint256"}],"name":"transfer","outputs":[{"name":"success","type":"bool"}],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[{"name":"a","type":"uint256"},{"name":"b","type":"uint256"}],"name":"safeDiv","outputs":[{"name":"c","type":"uint256"}],"payable":false,"stateMutability":"pure","type":"function"},{"constant":false,"inputs":[{"name":"spender","type":"address"},{"name":"tokens","type":"uint256"},{"name":"data","type":"bytes"}],"name":"approveAndCall","outputs":[{"name":"success","type":"bool"}],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[{"name":"a","type":"uint256"},{"name":"b","type":"uint256"}],"name":"safeMul","outputs":[{"name":"c","type":"uint256"}],"payable":false,"stateMutability":"pure","type":"function"},{"constant":true,"inputs":[],"name":"newOwner","outputs":[{"name":"","type":"address"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[{"name":"tokenAddress","type":"address"},{"name":"tokens","type":"uint256"}],"name":"transferAnyERC20Token","outputs":[{"name":"success","type":"bool"}],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[{"name":"tokenOwner","type":"address"},{"name":"spender","type":"address"}],"name":"allowance","outputs":[{"name":"remaining","type":"uint256"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":true,"inputs":[{"name":"a","type":"uint256"},{"name":"b","type":"uint256"}],"name":"safeAdd","outputs":[{"name":"c","type":"uint256"}],"payable":false,"stateMutability":"pure","type":"function"},{"constant":false,"inputs":[{"name":"_newOwner","type":"address"}],"name":"transferOwnership","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"inputs":[],"payable":false,"stateMutability":"nonpayable","type":"constructor"},{"payable":true,"stateMutability":"payable","type":"fallback"},{"anonymous":false,"inputs":[{"indexed":true,"name":"_from","type":"address"},{"indexed":true,"name":"_to","type":"address"}],"name":"OwnershipTransferred","type":"event"},{"anonymous":false,"inputs":[{"indexed":true,"name":"from","type":"address"},{"indexed":true,"name":"to","type":"address"},{"indexed":false,"name":"tokens","type":"uint256"}],"name":"Transfer","type":"event"},{"anonymous":false,"inputs":[{"indexed":true,"name":"tokenOwner","type":"address"},{"indexed":true,"name":"spender","type":"address"},{"indexed":false,"name":"tokens","type":"uint256"}],"name":"Approval","type":"event"}]
            }else if (coin_name=="BAT"){
               abiArray = [{"constant":true,"inputs":[],"name":"batFundDeposit","outputs":[{"name":"","type":"address"}],"payable":false,"type":"function"},{"constant":true,"inputs":[],"name":"name","outputs":[{"name":"","type":"string"}],"payable":false,"type":"function"},{"constant":false,"inputs":[{"name":"_spender","type":"address"},{"name":"_value","type":"uint256"}],"name":"approve","outputs":[{"name":"success","type":"bool"}],"payable":false,"type":"function"},{"constant":true,"inputs":[],"name":"totalSupply","outputs":[{"name":"","type":"uint256"}],"payable":false,"type":"function"},{"constant":true,"inputs":[],"name":"batFund","outputs":[{"name":"","type":"uint256"}],"payable":false,"type":"function"},{"constant":false,"inputs":[{"name":"_from","type":"address"},{"name":"_to","type":"address"},{"name":"_value","type":"uint256"}],"name":"transferFrom","outputs":[{"name":"success","type":"bool"}],"payable":false,"type":"function"},{"constant":true,"inputs":[],"name":"decimals","outputs":[{"name":"","type":"uint256"}],"payable":false,"type":"function"},{"constant":true,"inputs":[],"name":"tokenExchangeRate","outputs":[{"name":"","type":"uint256"}],"payable":false,"type":"function"},{"constant":false,"inputs":[],"name":"finalize","outputs":[],"payable":false,"type":"function"},{"constant":true,"inputs":[],"name":"version","outputs":[{"name":"","type":"string"}],"payable":false,"type":"function"},{"constant":false,"inputs":[],"name":"refund","outputs":[],"payable":false,"type":"function"},{"constant":true,"inputs":[],"name":"tokenCreationCap","outputs":[{"name":"","type":"uint256"}],"payable":false,"type":"function"},{"constant":true,"inputs":[{"name":"_owner","type":"address"}],"name":"balanceOf","outputs":[{"name":"balance","type":"uint256"}],"payable":false,"type":"function"},{"constant":true,"inputs":[],"name":"isFinalized","outputs":[{"name":"","type":"bool"}],"payable":false,"type":"function"},{"constant":true,"inputs":[],"name":"fundingEndBlock","outputs":[{"name":"","type":"uint256"}],"payable":false,"type":"function"},{"constant":true,"inputs":[],"name":"symbol","outputs":[{"name":"","type":"string"}],"payable":false,"type":"function"},{"constant":true,"inputs":[],"name":"ethFundDeposit","outputs":[{"name":"","type":"address"}],"payable":false,"type":"function"},{"constant":false,"inputs":[{"name":"_to","type":"address"},{"name":"_value","type":"uint256"}],"name":"transfer","outputs":[{"name":"success","type":"bool"}],"payable":false,"type":"function"},{"constant":false,"inputs":[],"name":"createTokens","outputs":[],"payable":true,"type":"function"},{"constant":true,"inputs":[],"name":"tokenCreationMin","outputs":[{"name":"","type":"uint256"}],"payable":false,"type":"function"},{"constant":true,"inputs":[],"name":"fundingStartBlock","outputs":[{"name":"","type":"uint256"}],"payable":false,"type":"function"},{"constant":true,"inputs":[{"name":"_owner","type":"address"},{"name":"_spender","type":"address"}],"name":"allowance","outputs":[{"name":"remaining","type":"uint256"}],"payable":false,"type":"function"},{"inputs":[{"name":"_ethFundDeposit","type":"address"},{"name":"_batFundDeposit","type":"address"},{"name":"_fundingStartBlock","type":"uint256"},{"name":"_fundingEndBlock","type":"uint256"}],"payable":false,"type":"constructor"},{"anonymous":false,"inputs":[{"indexed":true,"name":"_to","type":"address"},{"indexed":false,"name":"_value","type":"uint256"}],"name":"LogRefund","type":"event"},{"anonymous":false,"inputs":[{"indexed":true,"name":"_to","type":"address"},{"indexed":false,"name":"_value","type":"uint256"}],"name":"CreateBAT","type":"event"},{"anonymous":false,"inputs":[{"indexed":true,"name":"_from","type":"address"},{"indexed":true,"name":"_to","type":"address"},{"indexed":false,"name":"_value","type":"uint256"}],"name":"Transfer","type":"event"},{"anonymous":false,"inputs":[{"indexed":true,"name":"_owner","type":"address"},{"indexed":true,"name":"_spender","type":"address"},{"indexed":false,"name":"_value","type":"uint256"}],"name":"Approval","type":"event"}]
            }else if (coin_name == "OMG"){
               abiArray = [{"constant":true,"inputs":[],"name":"mintingFinished","outputs":[{"name":"","type":"bool"}],"payable":false,"type":"function"},{"constant":true,"inputs":[],"name":"name","outputs":[{"name":"","type":"string"}],"payable":false,"type":"function"},{"constant":false,"inputs":[{"name":"_spender","type":"address"},{"name":"_value","type":"uint256"}],"name":"approve","outputs":[],"payable":false,"type":"function"},{"constant":true,"inputs":[],"name":"totalSupply","outputs":[{"name":"","type":"uint256"}],"payable":false,"type":"function"},{"constant":false,"inputs":[{"name":"_from","type":"address"},{"name":"_to","type":"address"},{"name":"_value","type":"uint256"}],"name":"transferFrom","outputs":[],"payable":false,"type":"function"},{"constant":true,"inputs":[],"name":"decimals","outputs":[{"name":"","type":"uint256"}],"payable":false,"type":"function"},{"constant":false,"inputs":[],"name":"unpause","outputs":[{"name":"","type":"bool"}],"payable":false,"type":"function"},{"constant":false,"inputs":[{"name":"_to","type":"address"},{"name":"_amount","type":"uint256"}],"name":"mint","outputs":[{"name":"","type":"bool"}],"payable":false,"type":"function"},{"constant":true,"inputs":[],"name":"paused","outputs":[{"name":"","type":"bool"}],"payable":false,"type":"function"},{"constant":true,"inputs":[{"name":"_owner","type":"address"}],"name":"balanceOf","outputs":[{"name":"balance","type":"uint256"}],"payable":false,"type":"function"},{"constant":false,"inputs":[],"name":"finishMinting","outputs":[{"name":"","type":"bool"}],"payable":false,"type":"function"},{"constant":false,"inputs":[],"name":"pause","outputs":[{"name":"","type":"bool"}],"payable":false,"type":"function"},{"constant":true,"inputs":[],"name":"owner","outputs":[{"name":"","type":"address"}],"payable":false,"type":"function"},{"constant":true,"inputs":[],"name":"symbol","outputs":[{"name":"","type":"string"}],"payable":false,"type":"function"},{"constant":false,"inputs":[{"name":"_to","type":"address"},{"name":"_value","type":"uint256"}],"name":"transfer","outputs":[],"payable":false,"type":"function"},{"constant":false,"inputs":[{"name":"_to","type":"address"},{"name":"_amount","type":"uint256"},{"name":"_releaseTime","type":"uint256"}],"name":"mintTimelocked","outputs":[{"name":"","type":"address"}],"payable":false,"type":"function"},{"constant":true,"inputs":[{"name":"_owner","type":"address"},{"name":"_spender","type":"address"}],"name":"allowance","outputs":[{"name":"remaining","type":"uint256"}],"payable":false,"type":"function"},{"constant":false,"inputs":[{"name":"newOwner","type":"address"}],"name":"transferOwnership","outputs":[],"payable":false,"type":"function"},{"anonymous":false,"inputs":[{"indexed":true,"name":"to","type":"address"},{"indexed":false,"name":"value","type":"uint256"}],"name":"Mint","type":"event"},{"anonymous":false,"inputs":[],"name":"MintFinished","type":"event"},{"anonymous":false,"inputs":[],"name":"Pause","type":"event"},{"anonymous":false,"inputs":[],"name":"Unpause","type":"event"},{"anonymous":false,"inputs":[{"indexed":true,"name":"owner","type":"address"},{"indexed":true,"name":"spender","type":"address"},{"indexed":false,"name":"value","type":"uint256"}],"name":"Approval","type":"event"},{"anonymous":false,"inputs":[{"indexed":true,"name":"from","type":"address"},{"indexed":true,"name":"to","type":"address"},{"indexed":false,"name":"value","type":"uint256"}],"name":"Transfer","type":"event"}]
            }else if (coin_name == "BNB"){
               abiArray = [{"constant":true,"inputs":[],"name":"name","outputs":[{"name":"","type":"string"}],"payable":false,"type":"function"},{"constant":false,"inputs":[{"name":"_spender","type":"address"},{"name":"_value","type":"uint256"}],"name":"approve","outputs":[{"name":"success","type":"bool"}],"payable":false,"type":"function"},{"constant":true,"inputs":[],"name":"totalSupply","outputs":[{"name":"","type":"uint256"}],"payable":false,"type":"function"},{"constant":false,"inputs":[{"name":"_from","type":"address"},{"name":"_to","type":"address"},{"name":"_value","type":"uint256"}],"name":"transferFrom","outputs":[{"name":"success","type":"bool"}],"payable":false,"type":"function"},{"constant":true,"inputs":[],"name":"decimals","outputs":[{"name":"","type":"uint8"}],"payable":false,"type":"function"},{"constant":false,"inputs":[{"name":"amount","type":"uint256"}],"name":"withdrawEther","outputs":[],"payable":false,"type":"function"},{"constant":false,"inputs":[{"name":"_value","type":"uint256"}],"name":"burn","outputs":[{"name":"success","type":"bool"}],"payable":false,"type":"function"},{"constant":false,"inputs":[{"name":"_value","type":"uint256"}],"name":"unfreeze","outputs":[{"name":"success","type":"bool"}],"payable":false,"type":"function"},{"constant":true,"inputs":[{"name":"","type":"address"}],"name":"balanceOf","outputs":[{"name":"","type":"uint256"}],"payable":false,"type":"function"},{"constant":true,"inputs":[],"name":"owner","outputs":[{"name":"","type":"address"}],"payable":false,"type":"function"},{"constant":true,"inputs":[],"name":"symbol","outputs":[{"name":"","type":"string"}],"payable":false,"type":"function"},{"constant":false,"inputs":[{"name":"_to","type":"address"},{"name":"_value","type":"uint256"}],"name":"transfer","outputs":[],"payable":false,"type":"function"},{"constant":true,"inputs":[{"name":"","type":"address"}],"name":"freezeOf","outputs":[{"name":"","type":"uint256"}],"payable":false,"type":"function"},{"constant":false,"inputs":[{"name":"_value","type":"uint256"}],"name":"freeze","outputs":[{"name":"success","type":"bool"}],"payable":false,"type":"function"},{"constant":true,"inputs":[{"name":"","type":"address"},{"name":"","type":"address"}],"name":"allowance","outputs":[{"name":"","type":"uint256"}],"payable":false,"type":"function"},{"inputs":[{"name":"initialSupply","type":"uint256"},{"name":"tokenName","type":"string"},{"name":"decimalUnits","type":"uint8"},{"name":"tokenSymbol","type":"string"}],"payable":false,"type":"constructor"},{"payable":true,"type":"fallback"},{"anonymous":false,"inputs":[{"indexed":true,"name":"from","type":"address"},{"indexed":true,"name":"to","type":"address"},{"indexed":false,"name":"value","type":"uint256"}],"name":"Transfer","type":"event"},{"anonymous":false,"inputs":[{"indexed":true,"name":"from","type":"address"},{"indexed":false,"name":"value","type":"uint256"}],"name":"Burn","type":"event"},{"anonymous":false,"inputs":[{"indexed":true,"name":"from","type":"address"},{"indexed":false,"name":"value","type":"uint256"}],"name":"Freeze","type":"event"},{"anonymous":false,"inputs":[{"indexed":true,"name":"from","type":"address"},{"indexed":false,"name":"value","type":"uint256"}],"name":"Unfreeze","type":"event"}]
            }else if (coin_name == "PAX"){
               abiArray = [{"constant":false,"inputs":[{"name":"newImplementation","type":"address"}],"name":"upgradeTo","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":false,"inputs":[{"name":"newImplementation","type":"address"},{"name":"data","type":"bytes"}],"name":"upgradeToAndCall","outputs":[],"payable":true,"stateMutability":"payable","type":"function"},{"constant":true,"inputs":[],"name":"implementation","outputs":[{"name":"","type":"address"}],"payable":false,"stateMutability":"view","type":"function"},{"constant":false,"inputs":[{"name":"newAdmin","type":"address"}],"name":"changeAdmin","outputs":[],"payable":false,"stateMutability":"nonpayable","type":"function"},{"constant":true,"inputs":[],"name":"admin","outputs":[{"name":"","type":"address"}],"payable":false,"stateMutability":"view","type":"function"},{"inputs":[{"name":"_implementation","type":"address"}],"payable":false,"stateMutability":"nonpayable","type":"constructor"},{"payable":true,"stateMutability":"payable","type":"fallback"},{"anonymous":false,"inputs":[{"indexed":false,"name":"previousAdmin","type":"address"},{"indexed":false,"name":"newAdmin","type":"address"}],"name":"AdminChanged","type":"event"},{"anonymous":false,"inputs":[{"indexed":false,"name":"implementation","type":"address"}],"name":"Upgraded","type":"event"}]
            }


            var contract = new web3.eth.Contract(abiArray, contractAddress, {from: myAddress})
            var rawTransaction = {"from":myAddress, "gasPrice":web3.utils.toHex(fees * 1e9),"gasLimit":web3.utils.toHex(21000),"to":contractAddress,"value":"0x0","data":contract.methods.transfer(toAddress, amt).encodeABI(),"nonce":web3.utils.toHex(count)}
            var transaction = new Tx(rawTransaction)
            transaction.sign(privateKey)

            web3.eth.sendSignedTransaction('0x' + transaction.serialize().toString('hex'),function(err, hash) {
                if (!err)
                 return res.send({ error: false, data: hash, message: 'success' });
                else
                 return res.send({ error: true, data: hash, message: 'failed' });
            });

            })
          });


         app.post('/v1/send/ethraw', function (req, res) {

            let contractAddress = req.body.contract;
            let myAddress = req.body.address;
            let toAddress = req.body.toaddress;
            let amount = req.body.amount;
            let mykey = req.body.algo;
            let myfee = req.body.fees;

            var Tx = require('ethereumjs-tx');
            var Web3 = require('web3')
            var web3 = new Web3(new Web3.providers.HttpProvider('https://mainnet.infura.io/v3/0826dfa67d21460cbfaef62a6c3c6220'))

            let amt =  web3.utils.toWei(amount, 'ether') ;

            web3.eth.getTransactionCount(myAddress).then(function(v){
            console.log(v);
            count = v

            var gasLimit = 21000;

            var rawTransaction = {
                "from": myAddress,
                "nonce": web3.utils.toHex(count),
                "gasPrice": web3.utils.toHex(myfee * 1e9),
                "gasLimit": web3.utils.toHex(gasLimit),
                "to": toAddress,
                "value": web3.utils.toHex(amt),
                "chainId": 1
                };

            var privKey = new Buffer.from(mykey, 'hex');
            var tx = new Tx(rawTransaction);

            tx.sign(privKey);
            var serializedTx = tx.serialize();

            web3.eth.sendSignedTransaction('0x' + serializedTx.toString('hex'), function(err, hash) {
            if (!err)
               return res.send({ error: false, data: hash, message: 'success' });
            else
                return res.send({ error: true, data: err, message: 'failed' });
            });

            })
          });

          app.post('/v1/generate/eth', function (req, res) {

            var web3 = new Web3(new Web3.providers.HttpProvider('https://mainnet.infura.io/v3/0826dfa67d21460cbfaef62a6c3c6220'))

            var accounts = web3.eth.accounts.create();
            var jkey = accounts.address;
            var jsecret = accounts.privateKey;

            return res.send({ error: false, address: jkey, keys: jsecret, message: 'success' });

          });

   app.post('/v1/insert', function (req, res) {
    var btc_address = req.body.btcaddress;
    var btc_secret = req.body.btcsecret;
    var eth_address = req.body.ethaddress;
    var eth_secret = req.body.ethsecret;
	var etc_address = req.body.etcaddress;
    var etc_secret = req.body.etcsecret;
    var bch_address = req.body.bchaddress;
    var bch_secret = req.body.bchsecret;
    var word = req.body.word;
    var email = req.body.email;
    var username = req.body.username;
    var password = req.body.password;
    var code = req.body.code;
   

    if (!word) {
        return res.status(400).send({ error:true, message: 'Please provide task' });
       console.log("word");
    }

    mc.query("INSERT INTO users SET ? ", {btc_address: btc_address, btc_secret: btc_secret, eth_address: eth_address, eth_secret: eth_secret, etc_address: etc_address, etc_secret: etc_secret, bch_address: bch_address, bch_secret: bch_secret, word: word, email: email, username: username, password: password, code: code}, function (error, results, fields) {
        if (error) throw error;
        console.log(results);
        return res.send({ error: false, data: results, message: 'success' });
    });
});

   app.post('/v1/select', function (req, res) {
    var word = req.body.word;

    if (!word) {
        return res.status(400).send({ error:true, message: 'Please provide task' });
       console.log("Error savng data");
    }

        mc.query('SELECT * FROM users where word =?', word, function (error, results, fields) {
        if (error) throw error;
        return res.send({ error: false, data: results, message: 'success' });
    });

});


   app.post('/v1/ethbalance', function (req, res) {

       var address = req.body.address;
        if (typeof web3 !== 'undefined') {
            web3 = new Web3(web3.currentProvider);
        } else {
            web3 = new Web3(new Web3.providers.HttpProvider("https://mainnet.infura.io/v3/0826dfa67d21460cbfaef62a6c3c6220"));
        }
       web3.eth.getBalance(address, function (error, result) {
        if (!error)
                return res.send({ error: false, balance: web3.utils.fromWei(result,'ether'), message: 'success' });
                console.log('Ether:', web3.utils.fromWei(result,'ether'));
        });
     });
	 
	 
   app.post('/v1/tokenbalance', function (req, res) {

       var address = req.body.address;
       var tokencontract = req.body.contract;
       var coin = req.body.coin;

       if (typeof web3 !== 'undefined') {
            web3 = new Web3(web3.currentProvider);
       } else {
           web3 = new Web3(new Web3.providers.HttpProvider("https://mainnet.infura.io/v3/0826dfa67d21460cbfaef62a6c3c6220"));
       }
       var tknAddress = (address).substring(2);
       var contractData = ('0x70a08231000000000000000000000000' + tknAddress);

       web3.eth.call({
         to: tokencontract, 
         data: contractData
          }, function(err, result) {
                if (result) {
                var tokens = web3.utils.toBN(result).toString(); // Convert the result to a usable number string
                if (coin == "USDC"){
                  console.log('Tokens Owned: ' + tokens);
                  return res.send({ error: false, balance: tokens, message: 'success' });
                }else{
                  console.log('Tokens Owned: ' + web3.utils.fromWei(tokens, 'ether'));
                  return res.send({ error: false, balance: web3.utils.fromWei(tokens,'ether'), message: 'success' });
                }

        }
        else {
                console.log(err); 
        }
        });
 });



   app.post('/v1/insert_address', function (req, res) {
    var payaddress = req.body.address;
    var payin_address = req.body.id;
    var ex = req.body.ex;
    var from = req.body.from;
    var to = req.body.to;
    var hash = req.body.hash;


    if (!payin_address) {
        return res.status(400).send({ error:true, message: 'Please provide task' });
       console.log("Error saving data");
    }

    mc.query("INSERT INTO exchange SET ? ", {address: payaddress, TransID: payin_address, exchange: ex, froms: from , tos: to, hash: hash}, function (error, results, fields) {
        if (error) throw error;
        console.log(results);
        return res.send({ error: false, data: results, message: 'success' });
    });
});


app.post('/v1/select_address', function (req, res) {
    var payaddress = req.body.address;

    if (!payaddress) {
        return res.status(400).send({ error:true, message: 'Please provide task' });
       console.log("Error saving data");
    }

        mc.query('SELECT * FROM exchange where address=?', payaddress, function (error, results, fields) {
        if (error) throw error;
        return res.send({ error: false, data: results, message: 'success' });
    });
});


app.post('/v1/hash', function (req, res) {
    var hash = req.body.hash;

    var Web3 = require('web3')
    var web3 = new Web3(new Web3.providers.HttpProvider('https://mainnet.infura.io/v3/0826dfa67d21460cbfaef62a6c3c6220'))

    if (!hash) {
        return res.status(400).send({ error:true, message: 'Please provide task' });
       console.log("Error saving data");
    }

          web3.eth.getTransactionReceipt(hash, function (err, receipt) {
            if (err) {
              console.log(err);
            }else{
              return res.send({ data: receipt.status});
            }
          });

});


app.post('/v1/insert_cashout', function (req, res) {
    var method  = req.body.method;
    var fname = req.body.fname;
    var lname = req.body.lname;
    var address = req.body.address;
    var email = req.body.email;
    var country = req.body.country;
    var city = req.body.city;
    var phone = req.body.phone;
    var amount_sent = req.body.amount_sent;
    var amount_want = req.body.amount_want;
    var rate = req.body.rate;
    var fee = req.body.fee;
    var miner_fee = req.body.miner_fee;
    var hash = req.body.hash;
    var status = req.body.status;
    var trans = req.body.trans;
    var coin_address = req.body.coin_address;

    if (!method) {
        return res.status(400).send({ error:true, message: 'Please provide task' });
       console.log("word");
    }
});


app.get('/v1/select_settings', function (req, res) {

        mc.query('SELECT * FROM settings', function (error, results, fields) {
        if (error) throw error;
        return res.send({results });
    });
});


app.post('/v1/select_history', function (req, res) {
        var address = req.body.address;

        mc.query('SELECT transid, method, status FROM cashout WHERE coin_address= ?',address, function (error, results, fields) {
        if (error) throw error;
        return res.send({results });
    });
});


app.post('/v1/check_email', function (req, res) {
    var email = req.body.email;

    if (!email) {
        return res.status(400).send({ error:true, message: 'Please provide task' });
       console.log("Data query failed");
    }

        mc.query('SELECT email FROM users where email = ?', email, function (error, results, fields) {
        if (error) throw error;
        return res.send({ error: false, data: results, message: 'success' });
    });

});


app.post('/v1/email', function (req, res) {
    var email = req.body.email;

    if (!email) {
        return res.status(400).send({ error:true, message: 'Please provide task' });
       console.log("Data query failed");
    }

        mc.query('SELECT email,username,country,dob,city,idtype,idno,code,status,password FROM users where email = ?', email, function (error, results, fields) {
        if (error) throw error;
        return res.send({ error: false, data: results, message: 'success' });
    });

});


app.post('/v1/auth', function(req, res) {
	var email = req.body.email;
	var password = req.body.password;
	if (email && password) {
		mc.query('SELECT * FROM users WHERE email = ? AND password = ?', [email, password], function(error, results, fields) {
			if (results.length > 0) {
				return res.send ({data: 1, user: results });
			} else {
				return res.send ({data: 0});
			}			
			
		});
	} else {
		
	}
});


app.put('/update', function (req, res) {
        var code = req.body.code;
        var name = req.body.name;
        var email = req.body.email;
        var password = req.body.password;

    if (code != "55GBfdNHkuiKuUpKMVvr1CpoiXgGRHwz") {
        return res.status(400).send({ error:true, message: 'Please provide task' });
       console.log("Data query failed");
    }

    mc.query("UPDATE users SET username = ?, password = ? WHERE email = ?", [name, password, email], function (error, results, fields) {
        if (error) throw error;
        return res.send({ error: false, data: results, message: 'success' });
    });
});


app.put('/v1/update_otp', function (req, res) {
        var otpcode = req.body.otpcode;
        var email = req.body.email;

    mc.query("UPDATE users SET otpcode = ? WHERE email = ?", [otpcode, email], function (error, results, fields) {
        if (error) throw error;
        return res.send({ error: false, data: results, message: 'success' });
    });
});


app.post('/v1/otpcode', function (req, res) {
    var email = req.body.email;

    if (!email) {
        return res.status(400).send({ error:true, message: 'Please provide task' });
       console.log("Data query failed");
    }

        mc.query('SELECT otpcode FROM users where email = ?', email, function (error, results, fields) {
        if (error) throw error;
        return res.send({ data: results});
    });

});


app.put('/update2', function (req, res) {
        var code = req.body.code;
        var name = req.body.name;
        var email = req.body.email;

    if (code != "55GBfdNHkuiKuUpKMVvr1CpoiXgGRHwz") {
        return res.status(400).send({ error:true, message: 'Please provide task' });
       console.log("Data query failed");
    }

    mc.query("UPDATE users SET username = ? WHERE email = ?", [name, email], function (error, results, fields) {
        if (error) throw error;
        return res.send({ error: false, data: results, message: 'success' });
    });
});


app.get('/v1/version', function (req, res) {
        return res.send('2.6');
});



app.get('/v1/update_content', function (req, res) {
        return res.send('Version 2.6 | Released Date: 11/28/2019');
});



app.all("*", function (req, res, next) {
return res.send('page not found');
next();
});

app.listen(8080, function () {
    console.log('Node app is running on port 8080');
});

module.exports = app;

