lexer grammar DecaLexer;

options {
   language=Java;
   // Tell ANTLR to make the generated lexer class extend the
   // the named class, which is where any supporting code and
   // variables will be placed.
   superClass = AbstractDecaLexer;
}

@members {
}

// Deca lexer rules.

// Ignore spaces, tabs, newlines and whitespaces
WS  :   ( ' '
        | '\t'
        | '\r'
        | '\n'
        ) {
              skip(); // avoid producing a token
          }
    ;

// A FAIRE : Mots réservés
ASM: ('asm');
CLASS: ('class');
EXTENDS: ('extends');
ELSE: ('else');
FALSE: ('false');
IF: ('if');
INSTANCE_OF: ('instanceof');
NEW: ('new');
NULL: ('null');
READ_INT: ('readInt');
READ_FLOAT: ('readFloat');
PRINT: ('print');
PRINTLN: ('println');
PRINTLNX: ('printlnx');
PROTECTED: ('protected');
RETURN: ('return');
THIS: ('this');
TRUE: ('true');
WHILE: ('while');

// Identifiers
LETTER: 'a' .. 'z' | 'A' .. 'Z';
DIGIT: '0' ..'9';
IDENT: (LETTER | '$' | '_')(LETTER | DIGIT | '$' | '_')*;


// A FAIRE: Symboles spéciaux
ASSIGN : '=' ;
SEMI : (';') ;
OPARENT : ('(') ;
CPARENT : (')') ;
OBRACE : ('{') ;
CBRACE : ('}' EOF)  ;


// Integers
POSITIVE_DIGIT: '1' .. '9';
INT: '0' | POSITIVE_DIGIT DIGIT*;

// A FAIRE : Flottants

// Strings
STRING_CAR: ~('\\' | '\'' | '\n');  
STRING: '"' (STRING_CAR | '\\"' | '\\\\')* '"';
MULTI_LINE_STRING: '"' (STRING_CAR | '\n' | '\\"' | '\\\\')* '"';

// A FAIRE : Commentaires /* */

// A FAIRE : Séparateurs

// A FAIRE : Inclusion de fichiers



