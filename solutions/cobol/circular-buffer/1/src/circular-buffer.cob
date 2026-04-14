       IDENTIFICATION DIVISION.
       PROGRAM-ID. CIRCULAR-BUFFER.
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 WS-CAPACITY              PIC 999.
       01 WS-ITEM                  PIC 9.
       01 WS-RESULT                PIC 9.
       01 WS-SUCCESS               PIC 9.
       01 ring.
          03 ring-element  occurs 5       pic 9.
       01 buffer-count             pic 9.
       01 i-first                  pic 9.
       01 i-last                   pic 9.

       PROCEDURE DIVISION. 
       CREATE-BUFFER.
           move zero to buffer-count
           move zero to ring
           move 1 to i-first
           move zero to i-last
           .

       READ-BUFFER.
           if buffer-count = 0
               move zero to ws-success
               move zero to ws-result
           else
               move ring-element(i-first) to ws-result
               subtract 1 from buffer-count
               add 1 to i-first
               if i-first > ws-capacity
                   move 1 to i-first
               end-if
               move 1 to ws-success
           end-if
           .
       WRITE-BUFFER.
           move zero to ws-result
           if buffer-count = ws-capacity
               move zero to ws-success
           else
               add 1 to buffer-count
               add 1 to i-last
               if i-last > ws-capacity
                  move 1 to i-last
               end-if
               move ws-item to ring-element(i-last)
               move 1 to ws-success
           end-if
           .

       OVERWRITE-BUFFER.
           move 1 to ws-success
           if buffer-count = ws-capacity
               move ws-item to ring-element(i-first)
               add 1 to i-first
               if i-first > ws-capacity
                   move 1 to i-first
               end-if
           else
               perform write-buffer             
           end-if
           .

       CLEAR-BUFFER.
           if buffer-count = zero
               move 1 to ws-success
           else
               move zero to ring-element(i-first)
               add 1 to i-first
               if i-first > ws-capacity
                   move 1 to i-first
               end-if
               subtract 1 from buffer-count
               move 1 to ws-success
           end-if
           .