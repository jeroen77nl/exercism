       IDENTIFICATION DIVISION.
       PROGRAM-ID. YACHT.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 WS-RESULT     PIC 9(02) VALUE 0.
       01 WS-CATEGORY   PIC X(15).
       01 WS-DICE       PIC 9(05).
       01 H-CIJFER      PIC 9(01).
       01 I             PIC 9(01).
       01 H-TABEL VALUE ZEROES.
          03 H-TAB-ENTRY OCCURS 6 PIC 9.
       01 h-has-three   pic 9(01).
       01 h-has-two     pic 9(01).
       
       PROCEDURE DIVISION.
       YACHT.
            MOVE zeroes to h-tabel.
            MOVE zero to ws-result.
            MOVE 1 TO I.
            PERFORM VUL-TAB-entry UNTIL I > 6.

            evaluate ws-category
               when 'ones'   perform single-number
               when 'twos'   perform single-number
               when 'threes' perform single-number
               when 'fours'  perform single-number 
               when 'fives'  perform single-number
               when 'sixes'  perform single-number
               when 'yacht'  perform yacht-par
               when 'four of a kind'  
                             perform four-kind
               when 'full house'  
                             perform full-house
               when 'big straight' 
                             perform big-straight
               when 'little straight' 
                             perform little-straight
               when 'choice' perform som-van-5
            END-evaluate.

       VUL-TAB-ENTRY.
            MOVE WS-DICE(I:1) TO H-CIJFER.
            ADD 1 TO H-TAB-ENTRY(H-CIJFER).
            ADD 1 TO I.

       single-number.
            EVALUATE ws-category
     	       WHEN 'ones' MOVE 1 to i
     	       WHEN 'twos' MOVE 2 to i
     	       WHEN 'threes' MOVE 3 to i
     	       WHEN 'fours' MOVE 4 to i
     	       WHEN 'fives' MOVE 5 to i
     	       WHEN 'sixes' MOVE 6 to i
            END-EVALUATE.              
            COMPUTE ws-result = H-TAB-ENTRY(i) * i.

       yacht-par.
            MOVE 1 TO I.
            PERFORM yacht-par-iter UNTIL I > 6.
       yacht-par-iter.
            if H-TAB-ENTRY(i) = 5 THEN
               MOVE 50 to ws-result
            end-if.
            add 1 to i.
      
       four-kind.
            MOVE 1 TO I.
            PERFORM four-kind-iter UNTIL I > 6.
       four-kind-iter.
            if H-TAB-ENTRY(i) = 4 or 5 THEN
               COMPUTE ws-result = i * 4
            end-if.
            add 1 to i.

       full-house.
            MOVE zero to h-has-three.
            MOVE zero to h-has-two.
            MOVE 1 TO I.
            PERFORM full-house-iter UNTIL I > 6.
            if h-has-three = 1 AND h-has-two = 1 THEN
               perform som-van-5
            end-if.
       full-house-iter.
            if H-TAB-ENTRY(i) = 3 THEN
               MOVE 1 to h-has-three
            else
               if H-TAB-ENTRY(i) = 2 THEN
                  MOVE 1 to h-has-two
               end-if
            end-if.
            add 1 to i.

       som-van-5.
            MOVE zero to ws-result.
            MOVE 1 TO I.
            PERFORM som-van-5-iter UNTIL I > 6.
       som-van-5-iter.
            compute ws-result = ws-result + H-TAB-ENTRY(i) * i.
            add 1 to i.

       big-straight.
            if H-TAB-ENTRY(2) = 1
               AND H-TAB-ENTRY(3) = 1
               AND H-TAB-ENTRY(4) = 1
               AND H-TAB-ENTRY(5) = 1
               AND H-TAB-ENTRY(6) = 1 THEN
               MOVE 30 to ws-result
            end-if.

       little-straight.
            if H-TAB-ENTRY(1) = 1
               AND H-TAB-ENTRY(2) = 1
               AND H-TAB-ENTRY(3) = 1
               AND H-TAB-ENTRY(4) = 1
               AND H-TAB-ENTRY(5) = 1 THEN
               MOVE 30 to ws-result
            end-if.
      