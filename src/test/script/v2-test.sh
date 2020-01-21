#! /bin/bash

cd "$(dirname "$0")"/../../.. || exit 1
PATH=./src/test/script/launchers:"$PATH"

# COUNTERS
passedLex=0
totalLex=0

passedSynt=0
totalSynt=0

passedContext=0
totalContext=0

passedCodegen=0
totalCodegen=0

# PARAMS

# display the .deca before each file
catPgm=false

# display the error output even if the test is a success
catEOutput=false

# write all the file that didn't pass in a toFix.log
writeIssues=false

# stop the tests if one fails
exitOne=false

# Step A
# display the lexing output (i.e. tokens and their location)
lexOutput=false

# display the syntax output (i.e. a tree with no decorations)
syntOutput=false

# Step B
# display the context output (i.e. a tree with decorations)
contextOutput=false

# Step C
# display the result
codegenOutput=false

# display the assembly
catAss=false

# tests to do
lex=false
synt=false
context=false
codegen=false

# file (in case there is one)
file=""

# in case the file is given, must precise if it's valid or invalid
fileCategory="none"

# PARSING Options

for arg in $@
do
    if [[ $arg == "lex" ]];
    then
        lex=true
        echo "parsed lex"
    elif [[ $arg == "synt" ]];
    then
        synt=true
        echo "parsed synt"
    elif [[ $arg == "context" ]];
    then
        context=true
        echo "parsed context"
    elif [[ $arg == "codegen" ]];
    then
        codegen=true
        echo "parsed codegen"
    elif [[ $arg == "pgm" ]];
    then
        catPgm=true
        echo "parsed pgm"
    elif [[ $arg == "eout" ]];
    then
        catEOutput=true
        echo "parsed eoutp"
    elif [[ $arg == "log" ]];
    then
        writeIssues=true
        echo "parsed log"
    elif [[ $arg == "exit1" ]];
    then
        exitOne=true
        echo "parsed exit1"
    elif [[ $arg == "lexO" ]];
    then
        lexOutput=true
        echo "parsed lexO"
    elif [[ $arg == "syntO" ]];
    then
        syntOutput=true
        echo "parsed syntO"
    elif [[ $arg == "contextO" ]];
    then
        contextOutput=true
        echo "parsed contextO"
    elif [[ $arg == "codegenO" ]];
    then
        codegenOutput=true
        echo "parsed codegenO"
    elif [[ $arg == "assembly" ]];
    then
        catAss=true
        echo "parsed ass"
    elif [[ $arg == "valid" ]];
    then
        fileCategory=$arg
        echo "parsed filecat $fileCategory"
    elif [[ $arg == "invalid" ]];
    then
        fileCategory=$arg
        echo "parsed filecat $fileCategory"
    elif [[ ${arg: -5} == ".deca" ]];
    then
        if [[ -z $file ]];
        then
            file=$arg
            echo "parsed file $file"
        else
            echo "2 or more .deca files, exiting..."
            exit 1
        fi
    else
        echo "invalid command arguments, please use one among"
        echo "lex"
        echo "synt"
        echo "context"
        echo "codegen"
        echo "pgm"
        echo "eout"
        echo "log"
        echo "exit1"
        echo "lexO"
        echo "syntO"
        echo "contextO"
        echo "codegenO"
        echo "assembly"
        echo ".deca file"
        exit 1
    fi
done

if [[ "$fileCategory" == "none" && -n $file ]];
then
    echo "file given but no category, please precise valid or invalid"
    echo "exiting..."
    exit 1
fi

if [[ $lex = false && $synt = false && $context = false && $codegen = false ]];
then
    echo "one among lex, synt, context, codegen should be used, exiting..."
    exit 1
fi

# COMMON FUNCTIONS

displayProg () { # $1 = file
    if [[ "$catPgm" == true ]];
    then
        echo "Program :"
        cat $1
    fi
}

displayEOutput () { # $1 = eoutput
    if [[ "$catEOutput" == true ]];
    then
        echo "Eoutput:"
        echo $1
    fi
}

writeIssues () { # $1 = the issue
    if [[ "$writeIssues" == true ]];
    then
        $1 >> toFix.log
    fi
}

exitUn () {
    if [[ "$exitOne" == true ]];
    then
        echo "exiting..."
        exit 1
    fi
}

ifThenEcho () { # $1 = condition, $2 = printable
    if [[ $1 == true ]];
    then
        echo $2
    fi
}

red () { # $1 = string
    echo -en "\e[91m$1"
    echo -e "\e[39m"
}

green () { # $1 = string
    echo -en "\e[92m$1"
    echo -e "\e[39m"
}

# MAIN
# LEX

lexValid () { # $1 = file
    echo -e "$1"
    displayProg "$1"
    eoutput=$(test_lex $1 2>&1 > /dev/null | head -n 1)
    displayEOutput "$eoutput"
    if [[ "$lexOutput" == true ]];
    then
        out=$(test_lex $1)
        echo "lexOutput:"
        echo "$out"
    fi
    eoutput=$(echo "$eoutput" | grep 'deca:[0-9][0-9]*:')
    if [[ -n "$eoutput" ]];
    then
        echo "failed"
        writeIssues "$eoutput"
        exitUn
    else
        echo "passed"
        passedLex=$passedLex + 1
    fi
    totalLex=$totalLex + 1
}


lexInvalid () {
    echo -e "$1"
    displayProg "$1"
    eoutput=$(test_lex $1 2>&1 > /dev/null | head -n 1)
    displayEOutput "$eoutput"
    eoutput=$(echo "$eoutput" | grep 'deca:[0-9][0-9]*:')
    if [[ -n "$eoutput" ]];
    then
        echo "passed"
        passedLex=$passedLex + 1
    else
        echo "failed"
        writeIssues "$eoutput"
        exitUn
    fi
    totalLex=$totalLex + 1
}


if [[ "$lex" == true ]];
then
    if [[ -n $file && "$fileCategory" == "valid" ]]
    then
        lexValid "$file"

    elif [[ -n $file && "$fileCategory" == "invalid" ]]
    then

        lexInvalid "$file"

    else
        # valid
    	for f in ./src/test/deca/syntax/valid/lex/*.deca
    		do
                lexValid $f
    		done

    	# invalid
    	for f in ./src/test/deca/syntax/invalid/lex/*.deca
    		do
                lexInvalid $f
    		done
    fi
fi

# SYNT

syntValid () { # $1 = file
    echo -e "$1"
    displayProg "$1"
    eoutput=$(test_synt $1 2>&1 > /dev/null | head -n 1)
    displayEOutput "$eoutput"
    if [[ "$syntOutput" == true ]];
    then
        out=$(test_synt $1)
        echo "syntOutput:"
        echo "$out"
    fi
    eoutput=$(echo "$eoutput" | grep 'deca:[0-9][0-9]*:')
    if [[ -n "$eoutput" ]];
    then
        echo "failed"
        writeIssues "$eoutput"
        exitUn
    else
        echo "passed"
        passedSynt=$((passedSynt + 1))
    fi
    totalSynt=$((totalSynt + 1))
}


syntInvalid () {
    echo -e "$1"
    displayProg "$1"
    eoutput=$(test_synt $1 2>&1 > /dev/null | head -n 1)
    displayEOutput "$eoutput"
    eoutput=$(echo "$eoutput" | grep 'deca:[0-9][0-9]*:')
    if [[ -n "$eoutput" ]];
    then
        echo "passed"
        passedSynt=$((passedSynt + 1))
    else
        echo "failed"
        writeIssues "$eoutput"
        exitUn
    fi
    totalSynt=$((totalSynt + 1))
}

if [[ "$synt" == true ]];
then
    if [[ -n $file && "$fileCategory" == "valid" ]]
    then
        syntValid "$file"

    elif [[ -n $file && "$fileCategory" == "invalid" ]]
    then

        syntInvalid "$file"

    else
        # valid
    	for f in ./src/test/deca/syntax/valid/synt/*.deca
    		do
                syntValid $f
    		done

    	# invalid
    	for f in ./src/test/deca/syntax/invalid/synt/*.deca
    		do
                syntInvalid $f
    		done
    fi
fi


# CONTEXT

contextValid () { # $1 = file
    echo -e "$1"
    displayProg "$1"
    eoutput=$(test_context $1 2>&1 > /dev/null | head -n 1)
    displayEOutput "$eoutput"
    if [[ "$contextOutput" == true ]];
    then
        out=$(test_context $1)
        echo "contextOutput:"
        echo "$out"
    fi
    eoutput=$(echo "$eoutput" | grep 'deca:[0-9][0-9]*:')
    if [[ -n "$eoutput" ]];
    then
        echo "failed"
        writeIssues "$eoutput"
        exitUn
    else
        echo "passed"
        passedContext=$((passedContext + 1))
    fi
    totalContext=$((totalContext + 1))
}


contextInvalid () {
    echo -e "$1"
    displayProg "$1"
    eoutput=$(test_context $1 2>&1 > /dev/null | head -n 1)
    displayEOutput "$eoutput"
    eoutput=$(echo "$eoutput" | grep 'deca:[0-9][0-9]*:')
    if [[ -n "$eoutput" ]];
    then
        echo "passed"
        passedContext=$((passedContext + 1))
    else
        echo "failed"
        writeIssues "$eoutput"
        exitUn
    fi
    totalContext=$((totalContext + 1))
}


if [[ "$context" == true ]];
then
    if [[ -n $file && "$fileCategory" == "valid" ]]
    then
        contextValid "$file"

    elif [[ -n $file && "$fileCategory" == "invalid" ]]
    then

        contextInvalid "$file"

    else
        # valid
    	for f in ./src/test/deca/context/valid/*.deca
    		do
                contextValid $f
    		done

    	# invalid
    	for f in ./src/test/deca/context/invalid/*.deca
    		do
                contextInvalid $f
    		done
    fi
fi

# CODEGEN

codegenValid () {
    echo -e "$1"
    displayProg "$1"
    tmp="$1"
    echo $tmp
    a=${tmp:: -5}.ass
    decac $1 > /dev/null

    if [[ $catAss == "true" ]];
    then
        echo
        cat $a
    fi

    if [ ! -f $a ]; then
        echo "Fichier .ass non généré."
        exitUn
    else
        result=$(ima $a)
        echo
        rm $a
        ifThenEcho "$codegenOutput" "$result"
        expresult=$(grep @result $1 | cut -c12-)
        if [[ "$result" == "$expresult" ]];
        then
            echo "passed"
            passedCodegen=$((passedCodegen + 1))
        else
            echo "failed"
            exitUn
        fi
    fi
    totalCodegen=$((totalCodegen + 1))
}

codegenInvalid () {
    codegenValid "$1"
}

if [[ "$codegen" == true ]];
then
    if [[ -n $file && "$fileCategory" == "valid" ]]
    then
        codegenValid "$file"

    elif [[ -n $file && "$fileCategory" == "invalid" ]]
    then

        codegenInvalid "$file"

    else
        # valid
    	for f in ./src/test/deca/codegen/valid/*.deca
    		do
                codegenValid $f
    		done

    	# invalid
    	for f in ./src/test/deca/codegen/invalid/*.deca
    		do
                codegenInvalid $f
    		done
    fi
fi

# SUMMARY

if [[ "$lex" == true ]];
then
    echo "$passedLex passed out of $totalLex"
fi
if [[ "$synt" == true ]];
then
    echo "$passedSynt passed out of $totalSynt"
fi
if [[ "$context" == true ]];
then
    echo "$passedContext passed out of $totalContext"
fi
if [[ "$codegen" == true ]];
then
    echo "$passedCodegen passed out of $totalCodegen"
fi

echo "check toFix.log in your current directory for more details about the failed tests"

exit 0
