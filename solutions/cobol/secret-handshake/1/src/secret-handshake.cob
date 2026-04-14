       IDENTIFICATION DIVISION.
       PROGRAM-ID. SECRET-HANDSHAKE.
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 WS-INPUT                   PIC 999.
       01 WS-RESULT                  PIC X(60).

       01 h-byte.
          03 h-bit    occurs 5       pic 9.
       01 i                          pic 9.
       01 h-action                   pic x(15).

       PROCEDURE DIVISION.

       COMMANDS.
           move space to ws-result
           move zero to h-byte      
           move 1 to i
           perform until ws-input = 0
               compute h-bit(i) = function mod(ws-input, 2)
               compute ws-input = ws-input / 2
               add 1 to i
           end-perform

           if h-bit(5) = zero
               perform varying i from 1 by 1 until i > 4 
                   perform process-action-bit
               end-perform
           else 
               perform varying i from 4 by -1 until i < 1 
                   perform process-action-bit
               end-perform
           end-if
           .

       process-action-bit.
           if h-bit(i) = 1
               perform get-action
               if ws-result = space
                   move h-action to ws-result
               else
                   string function trim(ws-result)
                   ','
                   function trim(h-action) delimited by size
                       into ws-result
               end-if
           end-if
           .
      
       get-action.
           evaluate i
               when 1 move 'wink' to h-action
               when 2 move 'double blink' to h-action
               when 3 move 'close your eyes' to h-action
               when 4 move 'jump' to h-action
           end-evaluate         
           .