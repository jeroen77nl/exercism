       IDENTIFICATION DIVISION.
       PROGRAM-ID. LEAP.
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 WS-YEAR          PIC 9(04).
       01 WS-RESULT        PIC 9(01) VALUE 0.
       01 WS-DIV           PIC 9(04).
       PROCEDURE DIVISION.
       LEAP.
           IF FUNCTION MOD (WS-YEAR, 4) = 0
              AND (FUNCTION MOD (WS-YEAR, 100) NOT = 0 
                   OR FUNCTION MOD (WS-YEAR, 400) = 0) 
           THEN
              MOVE 1 TO WS-RESULT
           ELSE
              MOVE 0 TO WS-RESULT
           END-IF.
       LEAP-EXIT.
           EXIT.