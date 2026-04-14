       IDENTIFICATION DIVISION.
       PROGRAM-ID. raindrops.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 WS-NUMBER PIC 9(4).
       01 WS-RESULT PIC X(20).
       01 WS-WORD   PIC X(05).

       PROCEDURE DIVISION.
       RAINDROPS.
           IF FUNCTION MOD (WS-NUMBER 3) = 0
               MOVE 'Pling' TO WS-WORD
               PERFORM STRING-IT
           END-IF.
           IF FUNCTION MOD (WS-NUMBER 5) = 0
               MOVE 'Plang' TO WS-WORD
               PERFORM STRING-IT
           END-IF.
           IF FUNCTION MOD (WS-NUMBER 7) = 0
               MOVE 'Plong' TO WS-WORD
               PERFORM STRING-IT
           END-IF.
           IF WS-RESULT = SPACES
               MOVE WS-NUMBER TO WS-RESULT
           END-IF.

       STRING-IT.
           STRING WS-RESULT WS-WORD 
                  DELIMITED BY SPACE
                  INTO WS-RESULT.
      