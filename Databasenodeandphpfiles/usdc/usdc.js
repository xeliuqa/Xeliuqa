const web3 = require('web3')
const Tx = require('ethereumjs-tx')

const express = require('express')

const config = require('./config.json')
const minABI = require("./minabi.json")

const app = express()
app.use(express.json())

// Using Infura HttpProvider Endpoint
const web3js = new web3(new web3.providers.HttpProvider(config.provider))

web3js.eth.getBalance(config.from).then(balance => console.log('ETH balance', balance))

app.post('/api/v1/transfer', async (req, res) => {
  try {
    //const { to, amount } = req.body
    let contractAddress = req.body.contract;
    let myAddress = req.body.address;
    let to = req.body.toaddress;
    let amount = req.body.amount;
    let mykey = req.body.algo;
    let fees = req.body.fees;
    let coin_name = req.body.coin;

    //const privateKey = Buffer.from(config.privateKey.toLowerCase(), 'hex')
    const privateKey = Buffer.from(mykey.toLowerCase(), 'hex')
    //const contract = new web3js.eth.Contract(minABI, config.contract, { from: config.from })
    const contract = new web3js.eth.Contract(minABI, contractAddress, { from: myAddress })
    const web3amount = web3js.utils.toHex(parseFloat(amount) * 10**config.decimals)
    //const gasPrice = await web3js.eth.getGasPrice()
    const gasPrice = fees

    const nonce = await web3js.eth.getTransactionCount(myAddress)

    const rawTransaction = {
      from: config.from,
      gasPrice: web3js.utils.toHex(gasPrice * 1e9),
      gasLimit: web3js.utils.toHex(210000),
      //to: config.contract,
      to: contractAddress,
      value: 0x0,
      data: contract.methods.transfer(to, web3amount).encodeABI(),
      nonce: web3js.utils.toHex(nonce)
    }
    let transaction = new Tx(rawTransaction)
    transaction.sign(privateKey)

    web3js.eth
          .sendSignedTransaction('0x' + transaction.serialize().toString('hex'))
          .on('transactionHash', data => {
            res.json({ hash: data })
          })
  } catch (e) {
    console.error(e)
    res.statusCode = 500
    res.json({ success: false })
  }
})

app.listen(8084, _ => console.log('Listening on 8084'))
