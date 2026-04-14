       IDENTIFICATION DIVISION.
       PROGRAM-ID. LEAP.
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 WS-YEAR          PIC 9(04).
       01 WS-RESULT        PIC 9(01) VALUE 0.
       01 WS-DIV           PIC 9(04).
       01 WS-REMAINDER-400 PIC 9(04).
       01 WS-REMAINDER-100 PIC 9(04).
       01 WS-REMAINDER-4   PIC 9(04).
       PROCEDURE DIVISION.
       LEAP.
         DIVIDE WS-YEAR BY 400 
                GIVING WS-DIV 
                REMAINDER WS-REMAINDER-400.
         DIVIDE WS-YEAR BY 100 
                GIVING WS-DIV 
                REMAINDER WS-REMAINDER-100.
         DIVIDE WS-YEAR BY 4   
                GIVING WS-DIV 
                REMAINDER WS-REMAINDER-4.
      
         IF WS-REMAINDER-4 = 0
            AND (WS-REMAINDER-100 NOT = 0 
                 OR WS-REMAINDER-400 = 0) 
         THEN
            MOVE 1 TO WS-RESULT
         ELSE
            MOVE 0 TO WS-RESULT
         END-IF.
       LEAP-EXIT.
         EXIT.