       IDENTIFICATION DIVISION.
       PROGRAM-ID. collatz-conjecture.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 WS-NUMBER        PIC S9(8).
       01 WS-STEPS         PIC 9(4).
       01 WS-ERROR         PIC X(35).
      
       PROCEDURE DIVISION.
       COLLATZ-CONJECTURE.
           IF WS-NUMBER <= 0
               MOVE "Only positive integers are allowed" TO WS-ERROR
           ELSE
               PERFORM COMPUTE-IT UNTIL WS-NUMBER = 1
           END-IF.
      
       COMPUTE-IT.
           IF FUNCTION MOD(WS-NUMBER 2) = ZERO
               COMPUTE WS-NUMBER = WS-NUMBER / 2
           ELSE
               COMPUTE WS-NUMBER = WS-NUMBER * 3 + 1
           END-IF.
           ADD 1 TO WS-STEPS.
