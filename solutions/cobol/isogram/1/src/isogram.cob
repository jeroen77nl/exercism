        IDENTIFICATION DIVISION.
        PROGRAM-ID. ISOGRAM.
        ENVIRONMENT DIVISION.
        DATA DIVISION.
        WORKING-STORAGE SECTION.
        01 WS-PHRASE       PIC X(60).
        01 WS-RESULT       PIC 99.
        01 WS-I            PIC 9(03).
        01 WS-LETTER       PIC X(01).
        01 WS-LETTER-VNR   PIC 9(02).
        01 WS-TEL-TAB.
           03 WS-TEL-ENTRY PIC 9(02) OCCURS 26.
        PROCEDURE DIVISION.

        ISOGRAM.
            PERFORM INIT.
            MOVE FUNCTION LOWER-CASE(WS-PHRASE) TO WS-PHRASE.
            PERFORM SENTENCE-TO-TAB.

            MOVE 1 TO WS-RESULT.
            PERFORM HOOGSTENS-1.
      
        INIT.
            MOVE ZEROES TO WS-TEL-TAB.
        
        SENTENCE-TO-TAB.
            PERFORM SENTENCE-TO-TAB-ITER 
                    VARYING WS-I FROM 1 BY 1
                    UNTIL WS-I > 60.

        SENTENCE-TO-TAB-ITER.
            MOVE WS-PHRASE(WS-I:1) TO WS-LETTER.
            IF WS-PHRASE(WS-I:1) >= 'a' and <= 'z' THEN
               COMPUTE WS-LETTER-VNR = 
                       FUNCTION ORD(WS-LETTER) - FUNCTION ORD('a') + 1
               ADD 1 TO WS-TEL-ENTRY(WS-LETTER-VNR)
            END-IF.

        HOOGSTENS-1.
            PERFORM HOOGSTENS-1-ITER 
                    VARYING WS-I FROM 1 BY 1
                    UNTIL   WS-I > 26.
      
        HOOGSTENS-1-ITER.
            IF WS-TEL-ENTRY(WS-I) > 1 THEN
               MOVE ZERO TO WS-RESULT
            END-IF.
