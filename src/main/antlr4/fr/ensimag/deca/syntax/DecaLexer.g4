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
OBRACE: '{';
CBRACE: '}';
PRINTLN: 'println';
OPARENT: '(';
CPARENT: ')';
SEMI: ';';
fragment STRING_CAR: ~('"' | '\\');
STRING: '"' (STRING_CAR | '\\' | '\\\\')* '"';

COMMENT:
    '//' ~('\n'|'\r')* { skip(); }
	| '/*' .*? '*/' { skip();};

WS  :   ( ' '
        | '\t'
        | '\r'
        | '\n'
        ) {skip();}
    ;
	  
DUMMY_TOKEN: .  { 
	System.out.println("Unrecognised character" + getText()); 
	skip(); 
				};
