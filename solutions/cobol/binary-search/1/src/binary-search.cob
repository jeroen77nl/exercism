       IDENTIFICATION DIVISION.
       PROGRAM-ID. BINARY-SEARCH.
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 WS-ITEM                  PIC 9999.
       01 WS-RESULT                PIC 99.
       01 WS-ERROR                 PIC X(40).
       01 WS-ARRAY                 pic x(80).
      * position fields for the search algorithm
       01 i                        pic 99.
       01 i-min                    pic 99.
       01 i-max                    pic 99.
      * fields for unstring of 'csv' string ws-array
       01 h-pos                    pic 99.
       01 h-nr-of-elements         pic 99.
       01 h-array.
          03 rowelem               pic 9(4) occurs 20.

       PROCEDURE DIVISION.
       
       BINARY-SEARCH. 
           perform split-ws-array-into-h-array
      
           move 1 to i-min
           move h-nr-of-elements to i-max
           move zero to ws-result
      
           perform until ws-result > 0 or i-min > i-max
               compute i = (i-max + i-min) / 2
               if ws-item = rowelem(i)
      *            found
                   move i to ws-result
               else
                   if ws-item < rowelem(i)
      *                search left part in next cycle
                       compute i-max = i - 1
                   else
      *                search right part in next cycle
                       compute i-min = i + 1
                   end-if
               end-if
           end-perform

           if ws-result = zero
               move 'value not in array' to ws-error
           end-if
           .

       split-ws-array-into-h-array.
           move 1 to h-pos
           move 1 to h-nr-of-elements
           perform until h-pos > 80 or ws-array(h-pos:1) = space
               unstring ws-array
                   delimited by ','
                   into rowelem(h-nr-of-elements)
                   with pointer h-pos
               end-unstring
               add 1 to h-nr-of-elements
           end-perform
           subtract 1 from h-nr-of-elements
           .      