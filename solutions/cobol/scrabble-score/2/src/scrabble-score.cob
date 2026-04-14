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
      
       procedure division.
       scrabble-score.
           move zero to ws-result
           string 'A01B03C03D02E01F04G02H04I01J08K05L01M03'
                  'N01O01P03Q10R01S01T01U01V04W04X08Y04Z10'
           into   lookup-tab
           move function upper-case(ws-word) to ws-word
           perform varying j from 1 by 1 
                             until j = function length(ws-word)
               search all lookup-letter
                   when letter(i) = ws-word(j:1)
                       add val(i) to ws-result
           end-perform.