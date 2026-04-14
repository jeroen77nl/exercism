       IDENTIFICATION DIVISION.
       PROGRAM-ID. reverse-string.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 WS-STRING          PIC X(64).
       01 WS-LEN             PIC 9(02).
       01 WS-MID             PIC 9(02).
       01 WS-I               PIC 9(02).
       01 WS-J               PIC 9(02).
       01 WS-TEMP            PIC X(01).

       PROCEDURE DIVISION.
       REVERSE-STRING.
          
          PERFORM VARYING WS-LEN FROM 64 BY -1
                  UNTIL WS-LEN = 0 
                     OR WS-STRING(WS-LEN:1) NOT = SPACE
          END-PERFORM.

          DIVIDE WS-LEN BY 2 GIVING WS-MID.

          PERFORM SWITCH-THEM
                  VARYING WS-I FROM 1 BY 1 
                  UNTIL   WS-I > WS-MID.
      
       SWITCH-THEM.
           COMPUTE WS-J = WS-LEN - WS-I + 1.
           MOVE WS-STRING(WS-I:1) TO WS-TEMP.
           MOVE WS-STRING(WS-J:1) TO WS-STRING(WS-I:1).
           MOVE WS-TEMP TO WS-STRING(WS-J:1).
           
           