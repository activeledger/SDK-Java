<img src="https://www.activeledger.io/wp-content/uploads/2018/09/Asset-23.png" alt="Activeledger" width="500"/>


# Activeledger Java SDK

Welcome to Java SDK for Activeledger. This SDK facilitates the user in sending the transactions to the ledger against a smart contract.

## Prerequisites

- JDK 1.8 or above
- Apache Maven 3.7.0

## Installing

- mvn install

  This command will generate a jar file which can be imported into Java application

- mvn install -DskipTests=true

  In case if you want to skip tests

## Usage

The SDK currently supports the following functionality

- Connection handling
- Key generation
- Key onboarding
- Transaction building
 ### Initialization and Connection Handlilng 
 ```
 ctx = ActiveledgerJavaSdkApplication.getContext();
		ActiveledgerJavaSdkApplication.setConnection("http", "testnet-uk.activeledger.io", "5260");
```

### Generating KeyPair
```
KeyGen keyGen = (KeyGen) ctx.getBean("KeyGen");
KeyPair keyPair = keyGen.generateKeyPair(Encryption.RSA);
```

### Key onboarding
```
OnboardIdentity onboardIdentity = (OnboardIdentity) ctx.getBean("OnboardIdentity");
TxResponse resp = onboardIdentity.onboard(KeyPair, Encryption.RSA/Encryption.EC, "local");// "local" is a keyName. Can be changed to anything. TxResponse will either give you an erorr or streamID

```
    
### Transaction Building    
 Transaction class has 2 functions which you can use for building the Transaction.
 Build the TxObject and send it to either
 1) createTransaction(TxReqOptions) - createsthe Transaction Object
 	- You can update the sigs object if needed in the returned Transaction Object.
	- To use the territoriality, pass the nodeID. Otherwise null.
	- If transaction is self signed , send true otherwise false
	- Use sendTransaction(Transaction) to send the transaction to activeledger
 2) createAndSendTransation(TxReqOptions) - creates and sends the transaction to Activeledger. 
 	- You will get a TxResponse object in return which will give you either the streamID or error.
 
```
Transaction transaction=new Transaction();
transaction.setTxObject={}//create TXObject using model classes

TxReqOptions opts=new TxReqOptions();
opts.setStream(<streamId>);//optianed from onbaording
opts.setKeyPair(<keyPair>);//generated keyPair
opts.setKeyName(<keyName>);//keyName used while onboarding
opts.setType(<type>);//keyType
opts.setTerritoriality(<node id>);
opts.isSelfSign(<selfSign>);

transaction.createTransaction(opts);
transaction.sendTransaction();
or
transaction.createAndSendTransaction(opts);

```

