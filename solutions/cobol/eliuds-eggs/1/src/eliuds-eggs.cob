       IDENTIFICATION DIVISION.
       PROGRAM-ID. ELIUDS-EGGS.
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 WS-INPUTVARS.
           05 WS-INPUT             PIC 9(10).
       01 WS-OUTPUTVARS.
           05 WS-RESULT            PIC 9999.
       
       PROCEDURE DIVISION.

       EGG-COUNT.
           move zero to ws-result
           perform until ws-input = 0
              add function mod(ws-input, 2) to ws-result
              divide ws-input by 2 giving ws-input
           end-perform
           .