       IDENTIFICATION DIVISION.
       PROGRAM-ID. difference-of-squares.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 WS-DIFFERENCE-OF-SQUARES PIC 9(8).
       01 WS-SUM-OF-SQUARES PIC 9(8).
       01 WS-SQUARE-OF-SUM PIC 9(8).
       01 WS-NUMBER PIC 9(8).
       01 I                pic 9(08).
       01 ws-sum           pic 9(08).

       PROCEDURE DIVISION.       
       SQUARE-OF-SUM.
           move zero to ws-sum
           perform varying i from 1 by 1 until i > ws-number
               add i to ws-sum
           end-perform
           compute WS-SQUARE-OF-SUM = ws-sum ** 2
           .       
       SUM-OF-SQUARES.
           move zero to ws-sum-of-squares
           perform varying i from 1 by 1 until i > ws-number
               compute ws-sum-of-squares = 
                       ws-sum-of-squares + i ** 2
           end-perform
           .
       
       DIFFERENCE-OF-SQUARES.
           perform square-of-sum
           perform sum-of-squares
           compute ws-difference-of-squares =
                   ws-square-of-sum - ws-sum-of-squares
           .

