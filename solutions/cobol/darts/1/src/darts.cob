       IDENTIFICATION DIVISION.
       PROGRAM-ID. DARTS.
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 WS-X PIC 99V9.
       01 WS-Y PIC 99V9.
       01 WS-RESULT PIC 99.

       01 hulpvelden.
          03 outer-radius       pic 9(02) value 10.
          03 middle-radius      pic 9(02) value 5.
          03 inner-radius       pic 9(02) value 1.
          03 shot-radius        pic 9(02)v9(02).
       PROCEDURE DIVISION.
       DARTS.
           compute shot-radius = 
               function sqrt(ws-x ** 2 + ws-y ** 2)
           evaluate shot-radius
           when > outer-radius 
               move zero to ws-result
           when > middle-radius 
               move 1 to ws-result
           when > inner-radius
               move 5 to ws-result
           when other
               move 10 to ws-result
           end-evaluate
           .