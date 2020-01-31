#!/bin/bash

for FILE in "$@"
do
    echo "$FILE"
    decac "$FILE"
done
