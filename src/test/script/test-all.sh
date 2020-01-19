#! /bin/bash

cd "$(dirname "$0")"/../../.. || exit 1
PATH=./src/test/script/launchers:"$PATH"

# LEX
# fait appel à test_lex sur tous les fichiers src/test/deca/syntax/*/lex/*.deca
# et compare avec le résultat attendu indiqué en ligne de commentaire
# pas de commentaire signifie pas d'erreurs attendue
catPgm="false"
catEoutp="true"
catAss=""

if [ $1 = "lex" ];
then
	# valid
	echo -e "VALID"
	for f in ./src/test/deca/syntax/valid/lex/*.deca
		do
			if [[ $catPgm == "true" ]];
			then
			    echo
			    cat $f
			fi
			./src/test/script/testlexvalid.sh $f $catEoutp
		done

	# invalid
	echo -e "INVALID"
	for f in ./src/test/deca/syntax/invalid/lex/*.deca
		do
			if [[ $catPgm == "true" ]];
			then
			    echo
			    cat $f
			fi
			./src/test/script/testlexinvalid.sh $f $catEoutp
		done


# SYNT
# fait appel à test_synt sur tous les fichiers src/test/deca/syntax/*/synt/*.deca
elif [ $1 = "synt" ];
then
	# valid
	echo -e "VALID"
	for f in ./src/test/deca/syntax/valid/synt/*.deca
		do
			if [[ $catPgm == "true" ]];
			then
			    echo
			    cat $f
			fi
			./src/test/script/testsyntvalid.sh $f $catEoutp
		done

	# invalid
	echo -e "INVALID"
	for f in ./src/test/deca/syntax/invalid/synt/*.deca
		do
			if [[ $catPgm == "true" ]];
			then
			    echo
			    cat $f
			fi
			./src/test/script/testsyntinvalid.sh $f $catEoutp
		done

# CONT
# fait appel à test_codegen sur tous les fichiers src/test/deca/context/*/*.deca
elif [ $1 = "context" ];
then
	# valid
	echo -e "VALID"
	for f in ./src/test/deca/context/valid/*.deca
		do
			if [[ $catPgm == "true" ]];
			then
			    echo
			    cat $f
			fi
			./src/test/script/testcontextvalid.sh $f $catEoutp
		done

	# invalid
	echo -e "INVALID"
	for f in ./src/test/deca/context/invalid/*.deca
		do
			if [[ $catPgm == "true" ]];
			then
			    echo
			    cat $f
			fi
			./src/test/script/testcontextinvalid.sh $f $catEoutp
		done

# CODEGEN
# fait appel à decac sur tous les fichiers src/test/deca/codegen/*/*.deca
elif [ $1 = "codegen" ];
then
	# valid
	echo -e "VALID"
	for f in ./src/test/deca/codegen/valid/*.deca
	do
		if [[ $catPgm == "true" ]];
		then
		    echo
		    cat $f
		fi
		./src/test/script/testcodegenvalid.sh $f $catAss
	done
else

	echo "Please, use one argument among lex, synt, context and codegen"

fi

echo -e "\e[39m"
