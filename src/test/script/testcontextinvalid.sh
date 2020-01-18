#! /bin/bash

cd "$(dirname "$0")"/../../.. || exit 1
PATH=./src/test/script/launchers:"$PATH"

f=$1
catEoutp=$2

echo -en "$f"
eoutput=$(test_context $f 2>&1 > /dev/null | head -n 1)
if [[ $catEoutp == "true" ]];
then
    echo $eoutput
fi
y=$(grep @expected_output $f)
y=${y:20}
if [[ $eoutput == *"$y"* ]];
then

if [[ -z $y ]];
then
    echo -e " \e[93mno @expected_output not found in the file $eoutput!"
else
    echo -e " \e[92mpassed"
fi
else
    echo -e " \e[91mfailed"
fi
echo -e "\e[39m<<<================================>>>"
