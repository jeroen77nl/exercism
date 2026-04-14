       IDENTIFICATION DIVISION.
       PROGRAM-ID. acronym.
       ENVIRONMENT DIVISION.
       DATA DIVISION.

       WORKING-STORAGE SECTION.
       01 WS-ACRONYM               pic x(80).
       01 ws-acronym-tmp           pic x(80).
       01 WS-RESULT                PIC X(20).
       01 i                        pic 99.
       01 j                        pic 99.

       PROCEDURE DIVISION.
       ABBREVIATE.
           move space to ws-result
           move space to ws-acronym-tmp
           move function upper-case(ws-acronym) to ws-acronym

           perform varying i from 1 by 1 until i > 80
               evaluate ws-acronym(i:1)
               when >= 'A' and <= 'Z'
               when space
                   continue
               when '-'
                   move space to ws-acronym(i:1)
               when other 
                   move low-value to ws-acronym(i:1)
               end-evaluate
           end-perform

           move zero to j
           perform varying i from 1 by 1 until i > 80
               if ws-acronym(i:1) not = low-value
                   add 1 to j
                   move ws-acronym(i:1) to ws-acronym-tmp(j:1)
               end-if
           end-perform

           perform varying i from 1 by 1 until i > 80
               if ws-acronym-tmp(i:1) not = space
                   if i = 1 or ws-acronym-tmp(i - 1:1) = space
                       string ws-result      
                              ws-acronym-tmp(i:1) delimited by space
                       into   ws-result
                   end-if
               end-if
           end-perform
           .