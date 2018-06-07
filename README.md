[![Build Status](https://travis-ci.org/eliostvs/clean-architecture-example.svg?branch=master)](https://travis-ci.org/eliostvs/clean-architecture-example)

# Clean Architecture Example

## Description

## Running

`./gradlew bootRun`

## Architecture

The project consists of 3 main packages: *core*, *data* and *presenter*.

### *core* package

This module contains the domain entities and use cases. 
There are no dependencies to frameworks and/or libraries and could be extracted to its own module.

This module contains the business rules that are essential for our application. 
In this module, gateways for the repositories are being defined. 

### *data* package

### *presenter* package

## Diagram

![c4 component](./doc./c4-component.png)
