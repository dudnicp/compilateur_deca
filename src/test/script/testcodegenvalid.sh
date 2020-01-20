#! /bin/bash

cd "$(dirname "$0")"/../../.. || exit 1
PATH=./src/test/script/launchers:"$PATH"

f=$1
catAss=$2

echo -en "$f"
a=${f:: -5}.ass
decac $f > /dev/null
if [[ $catAss == "true" ]];
then
    echo
    cat $a
fi

if [ ! -f $a ]; then
    echo -e "\e[93mFichier .ass non généré."
    echo -en "\e[39m"
    exit 1
else
    result=$(ima $a)
    echo
    rm $a
fi

expresult=$(grep @result $f | cut -c12-)
if [[ $result == $expresult ]];
then
    echo -e " \e[92mpassed"
else
    echo -e " \e[91mfailed"
fi
echo -e "\e[39m<<<================================>>>"
