       IDENTIFICATION DIVISION.
       PROGRAM-ID. SIEVE.
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 WS-LIMIT PIC 9999.
       01 hulpvelden.
          03 ws-result-tab.
             05 WS-RESULT PIC 999 OCCURS 1000 TIMES. 
          03 WS-COUNT PIC 9999.
          03 work-tab.
             05 work-entry occurs 1000.
                07 mark     pic 9(1).
          03 i              pic 9(4).
          03 j              pic 9(6).
          03 priem          pic 9(4).
          03 k              pic 9(4).
          03 disp           pic x(40).
      
       PROCEDURE DIVISION.
       SIEVE.
           initialize hulpvelden
      * for convenience: first entry is for nr 1, mark it as well
           move 1 to mark(1)
           perform varying i from 2 by 1 until i > ws-limit
               perform walkthrough
           end-perform
 
           move zero to j
           perform varying i from 1 by 1 until i > ws-limit
               if mark(i) = zero
                   add 1 to j
                   move i to ws-result(j)
               end-if
           end-perform
           .

       walkthrough.
           perform varying j from i by 1 until j > ws-limit
                                               or mark(j) = zero
           end-perform
           move j to priem
           if mark(j) = zero
               perform until j > ws-limit
                   compute j = j + priem 
                   if j <= ws-limit
                       move 1 to mark(j)
                   end-if
               end-perform
           end-if
           .