#! /bin/bash

if [[ $1 == "sin" ]]; then
    ./parseAndPrint.py curves/sinDeca.txt curves/sinJava.txt
fi

if [[ $1 == "atan" ]]; then
    ./parseAndPrint.py curves/atanDeca.txt curves/atanJava.txt
fi

if [[ $1 == "asin" ]]; then
    ./parseAndPrint.py curves/asinDeca.txt curves/asinJava.txt
fi

if [[ $1 == "ulp" ]]; then
    ./parseAndPrint.py curves/ulpDeca.txt curves/ulpBasic.txt
fi
