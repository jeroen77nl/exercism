       IDENTIFICATION DIVISION.
       PROGRAM-ID. luhn.
       ENVIRONMENT DIVISION.
       CONFIGURATION SECTION.
       REPOSITORY. FUNCTION ALL INTRINSIC.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 WS-CARD-NUMBER PIC X(32).
       01 WS-CARD-DIGITS PIC 9(32).
       01 WS-CHECKSUM PIC 9(2).
       01 WS-VALID PIC X(5).
       01 i        pic 9(02).
       01 j        pic 9(02).
       01 getal    pic 9(02).
       01 cc       pic x(32).
       01 som      pic 9(03).
       
       PROCEDURE DIVISION.
      
       luhn.
           move 'VALID' to ws-valid
           move space to cc

           perform remove-spaces
           perform valideer
           if ws-valid = 'VALID'
               perform double-even-digits
               perform sum-all-digits
               if mod(som 10) <> 0
                   move 'FALSE' to ws-valid
               else
                   if stored-char-length(cc) = 1
                       move 'FALSE' to ws-valid
                   end-if
               end-if
           end-if
           .

       remove-spaces.
           move zero to j
           perform varying i from 1 by 1 until i > 32
               if ws-card-number(i:1) not = space
                  add 1 to j
                  move ws-card-number(i:1) to cc(j:1)
               end-if
           end-perform
           .

       valideer.
           perform varying i from 1 by 1 
                             until i > stored-char-length(cc)
               if cc(i:1) not is numeric
                   move 'FALSE' to ws-valid                  
               end-if
           end-perform
           .      

       double-even-digits.
           move zero to j
           perform varying i from stored-char-length(cc) 
                             by -1 until i < 1
             if cc(i:1) not = space
               add 1 to j
               move cc(i:1) to getal
               if function mod(j 2) = 0
                   compute getal = getal * 2
                   if getal > 9
                       subtract 9 from getal
                   end-if
               end-if
               move getal(2:1) to cc(i:1)
             end-if
           end-perform
           .
      
       sum-all-digits.
           move zero to som
           perform varying i from 1 by 1 
                             until i > stored-char-length(cc)
               add numval(cc(i:1)) to som
           end-perform
           .