#! /bin/bash

cd "$(dirname "$0")"/../../.. || exit 1
PATH=./src/test/script/launchers:"$PATH"

f=$1
catEoutp=$2

echo -en "$f"
eoutput=$(test_synt $f 2>&1 > /dev/null | head -n 1)
if [[ $catEoutp == "true" ]];
then
    echo $eoutput
fi
xtree=$(grep @expected_tree $f | cut -c19-)
tree=$(test_synt $f)
# SI ERREUR = VIDE && TREE EST BON ALORS VALIDE
if [[ -z $eoutput ]];
then
    if [[ -n $xtree ]];
    then
        if [[ "$tree" == "$xtree" ]];
        then
            echo -e " \e[92mpassed"
        else
            echo -e " \e[91mfailed"
            echo -e "expected"
            echo "$xtree"
            echo -e "obtained"
            echo "$tree"
        fi
    else
        echo -e " \e[93mno @expected_tree not found in the file!"
        echo -e " $tree"
    fi
else
    echo -e " \e[91mfailed"
fi
echo -e "\e[39m<<<================================>>>"
