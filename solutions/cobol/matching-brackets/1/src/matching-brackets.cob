       IDENTIFICATION DIVISION.
       PROGRAM-ID. MATCHING-BRACKETS.
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 WS-INPUT                 PIC X(100).
       01 WS-RESULT                PIC 9.
       01 stack.
          03 s-element occurs 100  pic x(1).
          03 s-size                pic 9(3).
       01 i                        pic 9(3).
       01 elem                     pic x(1).
       
       PROCEDURE DIVISION.

       ISPAIRED.
           move 1 to ws-result
           initialize stack
           perform varying i from 1 by 1 
                             until i > function length(
                                         function trim(ws-input))
                             or ws-result = zero
               move ws-input(i:1) to elem
               evaluate elem
                   when '[' 
                   when '{' 
                   when '(' 
                       perform push
                   when ']'
                   when '}'
                   when ')'
                       perform pop
               end-evaluate        
           end-perform
           if s-size > zero
               move zero to ws-result
           end-if
           .
      
       push.
           add 1 to s-size
           move elem to s-element(s-size)
           .

       pop.
           if s-size = zero
               move zero to ws-result
           else
               if s-element(s-size) = '[' and elem = ']'
               or s-element(s-size) = '{' and elem = '}'
               or s-element(s-size) = '(' and elem = ')'
                   subtract 1 from s-size
               else
                   move 0 to ws-result
               end-if
           end-if
           .