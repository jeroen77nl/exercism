       IDENTIFICATION DIVISION.
       PROGRAM-ID. armstrong-numbers.
       ENVIRONMENT DIVISION.
       CONFIGURATION SECTION.
       REPOSITORY. FUNCTION ALL INTRINSIC.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 WS-NUMBER               pic x(8).
       01 WS-RESULT               PIC 9.
       01 hulpvelden.
          03 i                    pic 9.
          03 h-sum                pic 9(8).
          03 h-number             pic 9(8).
          03 h-number-x redefines h-number.
             05 h-digit  occurs 8 pic 9(1).
          03 h-len                pic 9(1).

       PROCEDURE DIVISION.
       ARMSTRONG-NUMBERS.
           initialize hulpvelden
           move zero to ws-result
           move function numval(ws-number) to h-number
      
           perform varying i from 1 by 1 until i > 8 
                                    or ws-number(i:1) = space
               add 1 to h-len
           end-perform

           perform varying i from 1 by 1 until i > 8
               compute h-sum = h-sum + h-digit(i) ** h-len
           end-perform

           if h-sum = h-number
               move 1 to ws-result
           end-if
           .
      