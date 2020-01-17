#! /bin/bash

cd "$(dirname "$0")"/../../.. || exit 1
PATH=./src/test/script/launchers:"$PATH"

# LEX
# fait appel à test_lex sur tous les fichiers src/test/deca/syntax/*/lex/*.deca
# et compare avec le résultat attendu indiqué en ligne de commentaire
# pas de commentaire signifie pas d'erreurs attendue
catPgm="true"
catEoutp="true"

if [ $1 = "lex" ];
then
	# valid
	echo -e "VALID"
	for f in ./src/test/deca/syntax/valid/lex/*.deca
		do
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
		done
		
	# invalid
	echo -e "INVALID"
	for f in ./src/test/deca/syntax/invalid/lex/*.deca
		do
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
		y=$(grep @expected_output $f)
		y=${y:20}
		if [[ $eoutput == *"$y"* ]];
		then
		
		if [[ -z $y ]];
		then
			echo -e " \e[93mno @expected_output not found in the file! $eoutput"
		else
			echo -e " \e[92mpassed"
		fi
		else
			echo -e " \e[91mfailed"
			echo $y
			echo $eoutput
		fi
		echo -e "\e[39m<<<================================>>>"
		done
	

# SYNT
# fait appel à test_synt sur tous les fichiers src/test/deca/syntax/*/synt/*.deca
elif [ $1 = "synt" ];
then
	# valid
	echo -e "VALID"
	for f in ./src/test/deca/syntax/valid/synt/*.deca
		do
		echo -en "$f"
		if [[ $catPgm == "true" ]];
		then
			echo
			cat $f
		fi
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
				fi
			else
				echo -e " \e[93mno @expected_tree not found in the file!"
				echo -e " $tree"
			fi
		else
			echo -e " \e[91mfailed"		
		fi
		echo -e "\e[39m<<<================================>>>"
		done
	
	# invalid
	echo -e "INVALID"
	for f in ./src/test/deca/syntax/invalid/synt/*.deca
		do
		echo -en "$f"
		if [[ $catPgm == "true" ]];
		then
			echo
			cat $f
		fi
		eoutput=$(test_synt $f 2>&1 > /dev/null | head -n 1)
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
			echo -e " \e[93mno @expected_output not found in the file! $eoutput"
		else
			echo -e " \e[92mpassed"
		fi
		else
			echo -e "\e[91mfailed"
		fi
		echo -e "\e[39m<<<================================>>>"
		done

# CONT
# fait appel à test_codegen sur tous les fichiers src/test/deca/context/*/*.deca
elif [ $1 = "context" ];
then
	# valid
	echo -e "VALID"
	for f in ./src/test/deca/context/valid/*.deca
		do
		echo -en "$f"
		if [[ $catPgm == "true" ]];
		then
			echo
			cat $f
		fi
		eoutput=$(test_context $f 2>&1 > /dev/null | head -n 1)
		if [[ $catEoutp == "true" ]];
		then
			echo $eoutput
		fi
		xtree=$(grep @expected_tree $f | cut -c19-)
		tree=$(test_context $f)
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
				fi
			else
				echo -e " \e[93mno @expected_tree not found in the file!"
				echo -e " $tree"
			fi
		else
			echo -e " \e[91mfailed"		
		fi
		echo -e "\e[39m<<<================================>>>"
		done
	
	# invalid
	echo -e "INVALID"
	for f in ./src/test/deca/context/invalid/*.deca
		do
		echo -en "$f"
		if [[ $catPgm == "true" ]];
		then
			echo
			cat $f
		fi
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
		done

# CODEGEN
# fait appel à decac sur tous les fichiers src/test/deca/codegen/*/*.deca
elif [ $1 = "codegen" ];
then
	echo
else
 
	echo "Please, use one argument among lex, synt, context and codegen"

fi

echo -e "\e[39m"
