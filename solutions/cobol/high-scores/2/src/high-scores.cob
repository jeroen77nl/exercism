       IDENTIFICATION DIVISION.
       PROGRAM-ID. high-scores.

       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 WS-PROPERTY       PIC A(20).
       01 WS-SCORES.
           02 SCORES        PIC X(3) OCCURS 20 TIMES
                            INDEXED BY IDX.
       01 WS-SCORES-T.
           02 SCORES-T      PIC X(3) OCCURS 20 TIMES
                            INDEXED BY IDX2.
       01 WS-RESULT-STRING  PIC X(60).
       01 WS-RESULT-VALUE   PIC 999.
       01 i                 pic 9(02).
       01 s                 pic 9(02).
       01 top-1             pic x(03).
       01 top-2             pic x(03).
       01 top-3             pic x(03).

       PROCEDURE DIVISION.
       HIGH-SCORES.
           move space to ws-result-string
           move zero to ws-result-value
           evaluate ws-property
               when 'scores' perform all-scores 
               when 'latest' perform latest
               when 'personalBest' perform best
               when 'personalTopThree' perform top-three
           end-evaluate
           .

       all-scores.
           move ws-scores to ws-result-string
           .
       latest.
           perform varying idx from 1 by 1 until scores(idx) = space
               continue
           end-perform
           move scores(idx - 1) to ws-result-value
           .
       best.
      *    easiest way, not the fastest (n . log n)
           move ws-scores to ws-scores-t
           sort scores-t on descending key scores
           move scores-t(1) to ws-result-value
           .
       top-three.
      *    easiest way, not the fastest (n . log n)
           move ws-scores to ws-scores-t
           sort scores-t on descending key scores
           string scores-t(1) 
                  scores-t(2) 
                  scores-t(3)
             into ws-result-string
           .