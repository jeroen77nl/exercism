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


       PROCEDURE DIVISION.

       C-REAL.
           move z1-re to result-re
           move zero  to result-im
           .
 
       C-IMAGINARY.
           move zero  to result-re
           move z1-im to result-im
           .

       C-ADD.
           compute result-re = z1-re + z2-re
           compute result-im = z1-im + z2-im
           .

       C-SUB.
           compute result-re = z1-re - z2-re
           compute result-im = z1-im - z2-im
           .

       C-MUL.
           compute result-re = z1-re * z2-re - z1-im * z2-im
           compute result-im = z1-im * z2-re + z1-re * z2-im
           .

       C-DIV.
           compute result-re = (z1-re * z2-re + z1-im * z2-im)
                             / (z2-im ** 2 + z2-re ** 2)
           compute result-im = (z1-im * z2-re - z1-re * z2-im)
                             / (z2-re ** 2 + z2-im ** 2)
           .

       C-ABS.
           compute result-re = function sqrt(z1-re ** 2 + z1-im ** 2)
           move zero to result-im
           .
       C-CONJUGATE.
           move z1-re to result-re
           compute result-im = - z1-im
           .