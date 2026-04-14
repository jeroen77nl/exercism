       IDENTIFICATION DIVISION.
       PROGRAM-ID. DIAMOND.
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 WS-INPUTVARS. 
           05 WS-LETTER            PIC X.
           05 WS-ROWS              PIC 99.
       01 WS-OUTPUTTABLE.
           05 WS-TABLEROW    OCCURS 1 TO 51 DEPENDING ON WS-ROWS.
              10 WS-LINE           PIC X(60).

       01  hulpvelden.
           03 letter-value         pic 9(02).
           03 current-letter       pic x(01).
           03 regel.
              05 teken occurs 51   pic x(01).
           03 i                    pic 9(02).
           03 min-i                pic 9(02).
           03 max-i                pic 9(02).
           03 i-letter-fst         pic 9(02).
           03 i-letter-snd         pic 9(02).
           03 r                    pic 9(02).
           03 r-max-minus-1        pic 9(02).
           03 i-result             pic 9(02).

       PROCEDURE DIVISION.
       
       DIAMOND. 
           initialize hulpvelden

           compute letter-value = 
                   function ord(ws-letter) - function ord('A') + 1
      
           perform schrijf-regel varying r from 1 by 1
                                 until r > letter-value

           compute r-max-minus-1 = letter-value - 1
           perform schrijf-regel varying r 
                                 from r-max-minus-1 by -1
                                 until r < 1
           .
       schrijf-regel.
           move space to regel
           compute min-i = 1
           compute max-i = 2 * letter-value - 1
           compute i-letter-fst = letter-value + 1 - r
           compute i-letter-snd = letter-value - 1 + r
      
           perform varying i from min-i by 1 until i > max-i
               if i = i-letter-fst or i-letter-snd
                   move function char(r + function ord('A') - 1) 
                     to teken(i)
               else           
                   move space to teken(i)
               end-if
           end-perform

           add 1 to i-result
           move regel to ws-line(i-result)
           .