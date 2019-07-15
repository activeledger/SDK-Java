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
JSONObject inJson = onboardIdentity.onboard(KeyPair, Encryption.RSA/Encryption.EC, "local");// "local" is a keyName. Can be changed to anything
```
    
### Transaction Building    
 Import the transaction Object and the TxObject from the sdk and build the transaction as per requirements.
 Sign the txObject
```
Sign sign = (Sign) ctx.getBean("Sign");
String signed = sign.signMessage(mapper.writeValueAsBytes(txObject), keyPair, Encryption.RSA);

```
Include the the signature in the transaction and send it
```
GenericTransaction genericTransaction = (GenericTransaction) ctx.getBean("GenericTransaction");
JSONObject genericTransactionOutput = genericTransaction.transaction(transaction);
```
