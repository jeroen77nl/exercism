       IDENTIFICATION DIVISION.
       PROGRAM-ID. PASCALS-TRIANGLE.
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 WS-COUNT               PIC 99.
       
       01 WS-RESULT. 
         02 WS-ROW               OCCURS 1 TO 99 DEPENDING ON WS-COUNT.
            05 ROWELEM           PIC X(60).

       01 hulpvelden.
          03 l                   pic 9(02).
          03 i                   pic 9(02).
          03 c                   pic 9(05).
          03 c-lzs               pic zzzz9. 
          03 tekst               pic x(60).
       
       PROCEDURE DIVISION.
       
       ROWS.
           initialize ws-result
           initialize hulpvelden
      
           perform varying l from 1 by 1 
                   until   l > ws-count
               perform verwerk-regel
               move tekst to rowelem(l)
           end-perform
           .      
       verwerk-regel.
           move space to tekst
           move 1 to c
           perform varying i from 1 by 1 until i > l
               move c to c-lzs
               if tekst = space
                   move function trim (c-lzs)
                     to tekst
               else
                   string  tekst                 delimited by space
                           ','                   delimited by space
                           function trim (c-lzs) delimited by size
                   into    tekst
               end-if
               compute c = c * (l - i) / i
           end-perform
           .
