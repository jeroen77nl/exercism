       IDENTIFICATION DIVISION.
       PROGRAM-ID. SCRABBLE-SCORE.
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
      *Inputs
       01 WS-WORD   PIC X(60).
      *Outputs
       01 WS-RESULT PIC 99.
       01 i         pic 99.

       procedure division.
       scrabble-score.
           move zero to ws-result
           move function upper-case(ws-word) to ws-word
           perform varying i from 1 by 1 until i > 60 
                                         or ws-word(i:1) = space
               evaluate ws-word(i:1)
               when 'D'
               when 'G'
                    add 2 to ws-result
               when 'B'
               when 'C'
               when 'M'
               when 'P'
                    add 3 to ws-result
               when 'F'
               when 'H'
               when 'V'
               when 'W'
               when 'Y'
                    add 4 to ws-result
               when 'K'
                    add 5 to ws-result
               when 'J'
               when 'X'
                    add 8 to ws-result
               when 'Q'
               when 'Z'
                    add 10 to ws-result
               when other
                    add 1 to ws-result
               end-evaluate
           end-perform
           .