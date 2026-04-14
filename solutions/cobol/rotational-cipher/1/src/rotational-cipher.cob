       IDENTIFICATION DIVISION.
       PROGRAM-ID. rotational-cipher.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 WS-KEY            PIC 9(2).
       01 WS-TEXT           PIC X(128).
       01 WS-CIPHER         PIC X(128).
       01 i                 pic 9(03).
       01 new-letter        pic x(01).
       01 help-code          pic 9(03).     
       01 new-code          pic 9(03).     
       01 a-code            pic 9(03).

       PROCEDURE DIVISION.
       ROTATIONAL-CIPHER.
           move space to ws-cipher
           move function ord('A') to a-code
           move function upper-case(ws-text) to ws-text
           perform varying i 
                           from 1 by 1 
                           until i > 128
               if ws-text(i:1) >= 'A' and <= 'Z'
                  compute help-code = function ord(ws-text(i:1))
                                      + ws-key - a-code
                  compute new-code = function mod(help-code, 26) 
                                     + a-code
                  move function char(new-code)
                    to ws-cipher(i:1)
               else
                  move ws-text(i:1)
                    to ws-cipher(i:1) 
               end-if
           end-perform
           .