       IDENTIFICATION DIVISION.
       PROGRAM-ID. two-fer.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 WS-NAME PIC X(16).
       01 WS-RESULT PIC X(64).
       
       PROCEDURE DIVISION.
       TWO-FER.
           if ws-name = spaces
               move 'One for you, one for me.' to ws-result
           else
               string 'One for '      delimited by size
                      ws-name         delimited by space
                      ', one for me.' delimited by size
               into   ws-result
           end-if
           .