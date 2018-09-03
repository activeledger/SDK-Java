# Activeledger Java SDK

Welcome to Java SDK for Activeledger. This SDK facilitates the user in sending the transactions to the ledger against a smart contract.

## Prerequisites

- Java 1.8

## Installing

- mvn install

  This command will generate a jar file which can be imported into Java application

- mvn install -DskipTests=true

  In case if you want to skip tests

## End to End Scenario

The src/test/java/org/activeledger/java/sdk/activeledgerjavasdk/ActiveledgerJavaSdkApplicationTest.java contains a test Transfer funds transaction. It contains how to initialize the SDK, set connection, generate keys and send transaction. Currently, Transfer funds transaction has been created and sent but any transaction can be created similarly depending on the smart contract.
