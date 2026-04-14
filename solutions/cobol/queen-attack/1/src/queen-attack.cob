       IDENTIFICATION DIVISION.
       PROGRAM-ID. QUEEN-ATTACK.
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
      *Inputs
       01 WS-QUEEN PIC X(9).
       01 WS-WHITE_QUEEN PIC X(9).
       01 WS-BLACK_QUEEN PIC X(9).
       01 WS-PROPERTY PIC X(11).
      *Outputs
       01 WS-RESULT PIC 9.
       
       01 Hulpvelden.
          03 alf-x  pic x(3).
          03 alf-y  pic x(3).
          03 pos-x1 pic s9(2).
          03 pos-y1 pic s9(2).
          03 pos-x2 pic s9(2).
          03 pos-y2 pic s9(2).
          03 dx     pic s9(2).
          03 dy     pic s9(2).
          03 split-in pic x(9).
          03 split-out-num-x pic s9(2).
          03 split-out-num-y pic s9(2).

       PROCEDURE DIVISION.
       queen-attack.
           initialize hulpvelden
           move zero to ws-result
           if ws-property = 'create'
               perform create
           else
               perform can-attack
           end-if
           .

       create.
           move ws-queen to split-in
           perform split-field
           move split-out-num-x to pos-x1
           move split-out-num-y to pos-y1
           if pos-x1 >= 0 and <= 7
              and pos-y1 >= 0 and <= 7
               move 1 to ws-result
           end-if
           .
       can-attack.
           move ws-white_queen to split-in
           perform split-field
           move split-out-num-x to pos-x1
           move split-out-num-y to pos-y1

           move ws-black_queen to split-in
           perform split-field
           move split-out-num-x to pos-x2
           move split-out-num-y to pos-y2

           if pos-x1 = pos-x2
           or pos-y1 = pos-y2
           or function abs(pos-x1 - pos-x2) =
              function abs(pos-y1 - pos-y2)
               move 1 to ws-result
           end-if               
           .
       split-field.
           unstring split-in
               delimited by ','
               into alf-x alf-y
           move function numval(alf-x) to split-out-num-x
           move function numval(alf-y) to split-out-num-y
           .