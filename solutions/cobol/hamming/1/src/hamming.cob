       IDENTIFICATION DIVISION.
       PROGRAM-ID. hamming.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 WS-DNA-1 PIC X(32).
       01 WS-DNA-2 PIC X(32).
       01 WS-HAMMING PIC 9(2).
       01 WS-ERROR PIC X(31).
       01 i        pic 99.

       PROCEDURE DIVISION.
       HAMMING.
           move space to ws-error
           move zero to ws-hamming
           if function stored-char-length(ws-dna-1) not =
              function stored-char-length(ws-dna-2)
               move 'Strands must be of equal length' to ws-error
           else
               perform varying i from 1 by 1
                               until i > 
                               function stored-char-length(ws-dna-1)
                   if ws-dna-1(i:1) not = ws-dna-2(i:1)
                       add 1 to ws-hamming
                   end-if
               end-perform
           end-if
       .
