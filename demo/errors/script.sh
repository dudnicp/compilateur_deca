#!/bin/bash

for FILE in "$@"
do
    cat "$FILE"
    decac "$FILE"
done
