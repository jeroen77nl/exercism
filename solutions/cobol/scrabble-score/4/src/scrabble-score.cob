       IDENTIFICATION DIVISION.
       PROGRAM-ID. SCRABBLE-SCORE.
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 WS-WORD   PIC X(60).
       01 WS-RESULT PIC 99.
       01 j         pic 99.
       01 lookup-tab.
          03 lookup-letter occurs 26 ascending key letter indexed by i.
            05 letter   pic x.
            05 val      pic 99.
            05 filler   pic x.
       procedure division.
       scrabble-score.
           move zero to ws-result
           string 'A01 B03 C03 D02 E01 F04 G02 H04 I01 J08 K05 L01 M03 '
                  'N01 O01 P03 Q10 R01 S01 T01 U01 V04 W04 X08 Y04 Z10 '
           into   lookup-tab
           move function upper-case(ws-word) to ws-word
           perform varying j from 1 by 1 
                             until j = function length(ws-word)
               search all lookup-letter
                   when letter(i) = ws-word(j:1)
                       add val(i) to ws-result
           end-perform.