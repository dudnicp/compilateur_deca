#! /bin/bash

cd "$(dirname "$0")"/../../.. || exit 1
PATH=./src/test/script/launchers:"$PATH"

f=$1
catPgm=$2
catEoutp=$3


echo -en "$f"
if [[ $catPgm == "true" ]];
then
    echo
    cat $f
fi
eoutput=$(test_lex $f 2>&1 > /dev/null | head -n 1)
if [[ $catEoutp == "true" ]];
then
    echo $eoutput
fi
if [ -n $eoutput ];
then
    echo -e " \e[92mpassed"
else
    echo -e " \e[91mfailed"
    echo $eoutput
fi
echo -e "\e[39m<<<================================>>>"
