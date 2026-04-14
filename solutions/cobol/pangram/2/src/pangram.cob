        IDENTIFICATION DIVISION.
        PROGRAM-ID. PANGRAM.

        ENVIRONMENT DIVISION.
      
        DATA DIVISION.
        WORKING-STORAGE SECTION.
        01 WS-SENTENCE     PIC X(60).
        01 WS-RESULT       PIC 9(01).
        01 WS-I            PIC 9(03).
        01 WS-LETTER       PIC X(01).
        01 WS-LETTER-VNR   PIC 9(02).
        01 WS-TEL-TAB.
           03 WS-TEL-ENTRY PIC 9(02) OCCURS 26.
        01 WS-ALLES-GEVULD PIC X(01).
        01 WS-TEL-GEVULD   PIC 9(02).
      
        PROCEDURE DIVISION.
        PANGRAM.
            PERFORM INIT.
            MOVE FUNCTION LOWER-CASE(WS-SENTENCE) TO WS-SENTENCE.
            PERFORM SENTENCE-TO-TAB.
            PERFORM IS-ALLES-GEVULD.

            MOVE ZERO TO WS-RESULT.
            IF WS-TEL-GEVULD = 26 THEN
               MOVE 1 TO WS-RESULT
            END-IF.
      
        INIT.
            MOVE ZEROES TO WS-TEL-TAB.
        
        SENTENCE-TO-TAB.
            PERFORM SENTENCE-TO-TAB-ITER 
                    VARYING WS-I FROM 1 BY 1
                    UNTIL WS-I > 60.
        SENTENCE-TO-TAB-ITER.
            MOVE WS-SENTENCE(WS-I:1) TO WS-LETTER.
            IF WS-SENTENCE(WS-I:1) >= 'a' and <= 'z' THEN
               COMPUTE WS-LETTER-VNR = 
                       FUNCTION ORD(WS-LETTER) - FUNCTION ORD('a') + 1
               ADD 1 TO WS-TEL-ENTRY(WS-LETTER-VNR)
            END-IF.

        IS-ALLES-GEVULD.
            MOVE ZERO TO WS-TEL-GEVULD.
            PERFORM IS-ALLES-GEVULD-ITER 
                    VARYING WS-I FROM 1 BY 1
                    UNTIL   WS-I > 26.
      
        IS-ALLES-GEVULD-ITER.
            IF WS-TEL-ENTRY(WS-I) > ZERO THEN
               ADD 1 TO WS-TEL-GEVULD
            END-IF.
            