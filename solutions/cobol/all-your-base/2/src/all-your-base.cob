       IDENTIFICATION DIVISION.
       PROGRAM-ID. ALL-YOUR-BASE.
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 WS-INPUTBASE             PIC S999.
       01 WS-OUTPUTBASE            PIC S999.
       01 WS-DIGITS                PIC X(60).
       01 WS-RESULT                PIC X(60).
       01 WS-ERROR                 PIC X(60).
       01 ws-decimal               pic 9(10).
       01 i                        pic 99.
       01 i-first-non-zero         pic 99.
       01 ws-count                 pic 99.
       01 ws-str-num               pic xx.
       01 ws-nr-of-digits          pic 99.
       01 ws-input-digits.
          03 ws-input-digit        pic S99 occurs 20.        
       01 ws-output-digits.
          03 ws-output-digit       pic 99 occurs 20.        

       PROCEDURE DIVISION.
       REBASE.
           move space to ws-result
           move space to ws-error
           move zero to ws-input-digits
           move zero to ws-output-digits
           perform check-base
           if ws-error = space
               perform splits-invoer
               perform check-digits
               if ws-error = space
                   perform conv-to-decimal
                   perform splits-decimaal
                   perform maak-uitvoer
               end-if
           end-if
           .

       maak-uitvoer.
           move space to ws-result
           perform varying i from ws-count by -1 until i = 0
               if ws-output-digit(i)(1:1) = '0'
                  move ws-output-digit(i)(2:1) to ws-str-num
               else
                  move ws-output-digit(i) to ws-str-num
               end-if
               string ws-result  delimited by space
                      ','        delimited by size
                      ws-str-num delimited by space
                 into ws-result     
           end-perform
           move ws-result(2 : function length(ws-result) - 1)
             to ws-result
           if ws-result = space
              move '0' to ws-result
           end-if
           .

       check-base.
           if ws-inputbase < 2
               move 'input base must be >= 2' to ws-error
           else
               if ws-outputbase < 2
                   move 'output base must be >= 2' to ws-error
               end-if
           end-if
           .
      
       splits-invoer.
           move 1 to ws-count
           move 1 to ws-nr-of-digits
           perform until ws-count > 20 or ws-digits = space
               unstring ws-digits
                   delimited by ','
                   into ws-input-digit(ws-nr-of-digits)
                   with pointer ws-count
               end-unstring
               add 1 to ws-nr-of-digits
           end-perform
           subtract 1 from ws-nr-of-digits
           display 'ws-nr-of-digits : ' ws-nr-of-digits
           .      

       check-digits.
           perform varying i from 1 by 1 until i > ws-nr-of-digits
               if ws-input-digit(i) < 0 or >= ws-inputbase
                   move 'all digits must satisfy 0 <= d < input base' 
                     to ws-error
               end-if
           end-perform
           .
      
       conv-to-decimal.
           move zero to ws-decimal
           perform varying i from ws-nr-of-digits by -1 until i = 0
               compute ws-decimal =
                       ws-decimal +
                       ws-input-digit(i) * 
                          (ws-inputbase ** (ws-nr-of-digits - i))
           end-perform
           display 'ws-decimal: ' ws-decimal
           .

       splits-decimaal.
           move zero to ws-count
           move zero to ws-output-digits
           perform until ws-decimal = zero or ws-count > 20
               add 1 to ws-count
               divide ws-decimal by ws-outputbase
                                 giving ws-decimal
                                 remainder ws-output-digit(ws-count)
           end-perform
           display ws-output-digits  
           .