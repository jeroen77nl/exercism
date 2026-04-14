       IDENTIFICATION DIVISION.
       PROGRAM-ID. TRIANGLE.
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
      *Incoming
       01 WS-SIDES PIC X(20).
       01 WS-PROPERTY PIC X(11).
       01 hulpvelden.
          03 a          pic 99v9.
          03 b          pic 99v9.
          03 c          pic 99v9.
      *Outgoing
       01 WS-RESULT PIC 9.
       PROCEDURE DIVISION.
       TRIANGLE.
           move zero to ws-result

           unstring ws-sides
              delimited by ','
              into a, b, c

           perform is-triangle
           if ws-result = zero
               goback
           end-if

           move zero to ws-result
           evaluate ws-property
               when "equilateral" perform equilateral
               when "isosceles"   perform isosceles
               when "scalene"     perform scalene
           end-evaluate
           .

       is-triangle.
           if  a + b >= c
           and b + c >= a
           and a + c >= b
               move 1 to ws-result
           end-if
           .
       equilateral.
           if  a = b 
           and b = c 
           and a = c
               move 1 to ws-result
           end-if
           .

       isosceles.
           if (a = b and a = c)
           or (b = a and b = c)
           or (c = a and c = b)
               move 1 to ws-result
           end-if           
           .
       scalene.
           move zero to ws-result
           if a <> b and a <> c and b <> c
               move 1 to ws-result
           end-if