       IDENTIFICATION DIVISION.
       PROGRAM-ID. YACHT.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 WS-CATEGORY       PIC X(15).
       01 WS-DICE           PIC 9(05).
       01 HULPVELDEN.
          03 WS-RESULT      PIC 9(02).
          03 I              PIC 9(01).
          03 H-DIGIT        PIC 9(01).
          03 H-HAS-THREE    PIC 9(01).
          03 H-HAS-TWO      PIC 9(01).
          03 H-TABEL.
             05 H-TAB-ENTRY OCCURS 6 
                            PIC 9(01).
       
       PROCEDURE DIVISION.
       YACHT.
           INITIALIZE HULPVELDEN.

           MOVE 1 TO I.
           PERFORM VUL-TAB-ENTRY UNTIL I > 6.

           EVALUATE ws-category
               WHEN 'ones'   PERFORM SINGLE-NUMBER
               WHEN 'twos'   PERFORM SINGLE-NUMBER
               WHEN 'threes' PERFORM SINGLE-NUMBER
               WHEN 'fours'  PERFORM SINGLE-NUMBER 
               WHEN 'fives'  PERFORM SINGLE-NUMBER
               WHEN 'sixes'  PERFORM SINGLE-NUMBER
               WHEN 'yacht'  PERFORM YACHT-PAR
               WHEN 'four of a kind'  
                             PERFORM FOUR-KIND
               WHEN 'full house'  
                             PERFORM FULL-HOUSE
               WHEN 'big straight' 
                             PERFORM BIG-STRAIGHT
               WHEN 'little straight' 
                             PERFORM LITTLE-STRAIGHT
               WHEN 'choice' PERFORM SOM-VAN-5
           END-EVALUATE.

       VUL-TAB-ENTRY.
           MOVE WS-DICE(I:1) TO H-DIGIT.
           ADD 1 TO H-TAB-ENTRY(H-DIGIT).
           ADD 1 TO I.

       SINGLE-NUMBER.
           EVALUATE ws-category
     	         WHEN 'ones' MOVE 1 TO I
     	         WHEN 'twos' MOVE 2 TO I
     	         WHEN 'threes' MOVE 3 TO I
     	         WHEN 'fours' MOVE 4 TO I
     	         WHEN 'fives' MOVE 5 TO I
     	         WHEN 'sixes' MOVE 6 TO I
           END-EVALUATE.              
           COMPUTE WS-RESULT = H-TAB-ENTRY(I) * I.

       YACHT-PAR.
            MOVE 1 TO I.
            PERFORM YACHT-PAR-ITER UNTIL I > 6.
       YACHT-PAR-ITER.
            IF H-TAB-ENTRY(I) = 5 THEN
               MOVE 50 TO WS-RESULT
            END-IF.
            ADD 1 TO I.
      
       FOUR-KIND.
            MOVE 1 TO I.
            PERFORM FOUR-KIND-ITER UNTIL I > 6.
       FOUR-KIND-ITER.
            IF H-TAB-ENTRY(I) = 4 OR 5 THEN
               COMPUTE WS-RESULT = I * 4
            END-IF.
            ADD 1 TO I.

       FULL-HOUSE.
            MOVE ZERO TO H-HAS-THREE.
            MOVE ZERO TO H-HAS-TWO.
            MOVE 1 TO I.
            PERFORM FULL-HOUSE-ITER UNTIL I > 6.
            IF H-HAS-THREE = 1 AND H-HAS-TWO = 1 THEN
               PERFORM SOM-VAN-5
            END-IF.
       FULL-HOUSE-ITER.
            IF H-TAB-ENTRY(I) = 3 THEN
               MOVE 1 TO H-HAS-THREE
            ELSE
               IF H-TAB-ENTRY(I) = 2 THEN
                  MOVE 1 TO H-HAS-TWO
               END-IF
            END-IF.
            ADD 1 TO I.

       SOM-VAN-5.
            MOVE ZERO TO WS-RESULT.
            MOVE 1 TO I.
            PERFORM SOM-VAN-5-ITER UNTIL I > 6.
       SOM-VAN-5-ITER.
            COMPUTE WS-RESULT = WS-RESULT + H-TAB-ENTRY(I) * I.
            ADD 1 TO I.

       BIG-STRAIGHT.
            IF H-TAB-ENTRY(2) = 1
               AND H-TAB-ENTRY(3) = 1
               AND H-TAB-ENTRY(4) = 1
               AND H-TAB-ENTRY(5) = 1
               AND H-TAB-ENTRY(6) = 1 THEN
               MOVE 30 TO WS-RESULT
            END-IF.

       LITTLE-STRAIGHT.
            IF H-TAB-ENTRY(1) = 1
               AND H-TAB-ENTRY(2) = 1
               AND H-TAB-ENTRY(3) = 1
               AND H-TAB-ENTRY(4) = 1
               AND H-TAB-ENTRY(5) = 1 THEN
               MOVE 30 TO WS-RESULT
            END-IF.
      