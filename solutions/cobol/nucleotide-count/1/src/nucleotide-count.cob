       IDENTIFICATION DIVISION.
       PROGRAM-ID. nucleotide-count.
       ENVIRONMENT DIVISION.
       CONFIGURATION SECTION.
       REPOSITORY. FUNCTION ALL INTRINSIC.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 WS-DNA PIC X(128).
       01 WS-A PIC 9(4).
       01 WS-C PIC 9(4).
       01 WS-G PIC 9(4).
       01 WS-T PIC 9(4).
       01 WS-ERROR PIC X(36).
       01 WS-I     PIC 9(03).

       PROCEDURE DIVISION.
       NUCLEOTIDE-COUNT.
            MOVE ZERO TO WS-A.
            MOVE ZERO TO WS-C.
            MOVE ZERO TO WS-G.
            MOVE ZERO TO WS-T.
            MOVE SPACES TO WS-ERROR.

            PERFORM CHECK-ACGT.

            IF WS-ERROR = SPACES THEN
               PERFORM COUNT-ACGT
            END-IF.
      
        CHECK-ACGT.
            PERFORM CHECK-ACGT-ITER 
                    VARYING WS-I FROM 1 BY 1
                    UNTIL   WS-I > 128.
      
        CHECK-ACGT-ITER.
            IF WS-DNA(WS-I:1) = 'A' OR 'C' OR 'G' OR 'T' OR SPACE THEN
               CONTINUE
            ELSE
               MOVE 'ERROR: Invalid nucleotide in strand' 
                 TO WS-ERROR
            END-IF.
      
        COUNT-ACGT.
            PERFORM COUNT-ACGT-ITER 
                    VARYING WS-I FROM 1 BY 1
                    UNTIL WS-I > 128.
        COUNT-ACGT-ITER.
            EVALUATE WS-DNA(WS-I:1)
               WHEN 'A' ADD 1 TO WS-A
               WHEN 'C' ADD 1 TO WS-C
               WHEN 'G' ADD 1 TO WS-G
               WHEN 'T' ADD 1 TO WS-T
            END-EVALUATE.
