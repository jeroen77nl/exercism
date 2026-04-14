000001 IDENTIFICATION DIVISION.
       PROGRAM-ID. ROMAN-NUMERALS.
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 WS-NUMBER PIC 9999.
       01 WS-RESULT PIC X(20).
       01 i         pic 99.
       01 compound  pic 999.
       01 single    pic 9999.

       PROCEDURE DIVISION.
       ROMAN-NUMERALS.
           move space to ws-result
           perform until ws-number < 1000
             subtract 1000 from ws-number
             string ws-result 'M' delimited by space into ws-result
           end-perform
           if ws-number >= 900
             subtract 900 from ws-number
             string ws-result 'CM' delimited by space into ws-result
           end-if
           if ws-number >= 500
               subtract 500 from ws-number
               string ws-result 'D' delimited by space into ws-result
           end-if
           if ws-number >= 400
              subtract 400 from ws-number
              string ws-result 'CD' delimited by space into ws-result
           end-if
           perform until ws-number < 100
             subtract 100 from ws-number
             string ws-result 'C' delimited by space into ws-result
           end-perform
           if ws-number >= 90
              subtract 90 from ws-number
              string ws-result 'XC' delimited by space into ws-result
           end-if
           if ws-number >= 50
             subtract 50 from ws-number
             string ws-result 'L' delimited by space into ws-result
           end-if
           if ws-number >= 40
             subtract 40 from ws-number
             string ws-result 'XL' delimited by space into ws-result
           end-if
           perform until ws-number < 10
             subtract 10 from ws-number
             string ws-result 'X' delimited by space into ws-result
           end-perform
           if ws-number >= 9
              subtract 9 from ws-number
              string ws-result 'IX' delimited by space into ws-result
           end-if
           if ws-number >= 5
              subtract 5 from ws-number
              string ws-result 'V' delimited by space into ws-result
           end-if
           if ws-number >= 4
              subtract 4 from ws-number
              string ws-result 'IV' delimited by space into ws-result
           end-if
           perform until ws-number = 0
             subtract 1 from ws-number
             string ws-result 'I' delimited by space into ws-result
           end-perform
           .
