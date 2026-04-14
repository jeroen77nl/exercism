       IDENTIFICATION DIVISION.
       PROGRAM-ID. COMPLEX-NUMBERS.
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 Z1-RE          PIC S99999.
       01 Z1-IM          PIC S99999.
       01 Z2-RE          PIC S99999.
       01 Z2-IM          PIC S99999. 
       01 RESULT-RE      PIC S99999V9999.
       01 RESULT-IM      PIC S99999V9999.
       01 a              PIC S99999.
       01 b              PIC S99999.
       01 c              PIC S99999.
       01 d              PIC S99999.

       PROCEDURE DIVISION.
       fill-abcd.
           move z1-re to a
           move z1-im to b
           move z2-re to c
           move z2-im to d
           .

       C-REAL.
           perform fill-abcd           
           move a     to result-re
           move zero  to result-im
           .
 
       C-IMAGINARY.
           perform fill-abcd           
           move zero  to result-re
           move b     to result-im
           .

       C-ADD.
           perform fill-abcd           
           compute result-re = a + c
           compute result-im = b + d
           .

       C-SUB.
           perform fill-abcd           
           compute result-re = a - c
           compute result-im = b - d
           .

       C-MUL.
           perform fill-abcd           
           compute result-re = a * c - b * d
           compute result-im = b * c + a * d
           .

       C-DIV.
           perform fill-abcd           
           compute result-re = (a * c + b * d)
                             / (c ** 2 + d ** 2)
           compute result-im = (b * c - a * d)
                             / (c ** 2 + d ** 2)
           .

       C-ABS.
           perform fill-abcd           
           compute result-re = function sqrt(a ** 2 + b ** 2)
           move zero to result-im
           .
       C-CONJUGATE.
           perform fill-abcd           
           move a to result-re
           compute result-im = - b
           .