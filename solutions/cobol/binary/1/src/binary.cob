       IDENTIFICATION DIVISION.
       PROGRAM-ID. BINARY.
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 WS-BINARY                PIC X(60).
       01 WS-RESULT                PIC 9999.
       01 WS-ERROR                 PIC X(60). 
       01 i                        pic 99.
       01 bin                      pic x(60).
       01 dec                      pic 9(10).
       01 rest                     pic 9.
       01 msg-invalid-digit        pic x(60) value
       'error: a number containing non-binary digits is invalid'.
       01 msg-invalid-char         pic x(60) value
       'error: a number containing non-binary characters is invalid'.
       
       PROCEDURE DIVISION.
       
       DECIMAL.
           move zero to ws-result
           move space to ws-error
           move zero to dec
   
           perform varying i from 1 by 1 
                   until i > function length(function trim(ws-binary))
               evaluate ws-binary(i:1)
               when '0'
               when '1'
                   continue
               when >= '2' and <= '9'
                   move msg-invalid-digit to ws-error
               when other
                   move msg-invalid-char to ws-error
               end-evaluate
           end-perform

           if ws-error = space
               move function reverse(function trim(ws-binary))
                 to bin
               perform varying i from 1 by 1
                       until i > function length(function trim (bin))
                    compute rest = function mod(bin(i:1) 2)
                    if rest = 1
                        compute dec = dec + (2 ** (i - 1))
                    end-if
               end-perform
           end-if
           move dec to ws-result
           .
           