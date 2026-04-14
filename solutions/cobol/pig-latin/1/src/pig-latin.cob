       IDENTIFICATION DIVISION.
       PROGRAM-ID. PIG-LATIN.
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 WS-INPUT                 PIC X(60).
       01 WS-RESULT                PIC X(60).
       01 hulpvelden.
          03 i                     pic 99.
          03 i-left                pic 99.
          03 i-right               pic 99.
          03 i-woord               pic 99.
          03 tekst                 pic x(100).
          03 ay                    pic xx value 'ay'.
          03 letter                pic x.
          03 stoppen               pic 9.
          03 consonant             pic 9.
          03 nr-of-right-shifts    pic 9.
          03 woord-tab.
             05 woord occurs 20    pic x(10).
          03 nr-of-woorden         pic 99.
          03 ws-pointer            pic 99.

       PROCEDURE DIVISION.
           
       TRANSLATE.
           move space to ws-result
           perform vul-woord-tab      
           perform varying i-woord from 1 by 1 
                                   until i-woord > nr-of-woorden
               perform init
               evaluate true
                   when tekst(i-left:1) = 'a'
                   when tekst(i-left:1) = 'e'
                   when tekst(i-left:1) = 'i'
                   when tekst(i-left:1) = 'o'
                   when tekst(i-left:1) = 'u'
                   when tekst(i-left:2) = 'xr'
                   when tekst(i-left:2) = 'yt'
                       perform add-right-ay
                   when other
                       perform handle-consonants
               end-evaluate
               perform finish
           end-perform
       
           move woord(1) to ws-result
           perform varying i-woord from 2 by 1 
                                   until i-woord > nr-of-woorden
               string function trim(ws-result)
                      ' '    
                      function trim(woord(i-woord))
                      delimited by size
                      into ws-result 
           end-perform
           .

       init.
           move woord(i-woord) to tekst      
           move 1 to i-left
           perform varying i-right from 1 by 1 
                                   until tekst(i-right:1) = space
               continue
           end-perform
           subtract 1 from i-right
           .

       handle-consonants.
           move zero to stoppen
           move zero to nr-of-right-shifts
           perform until stoppen = 1
               perform first-is-consonant
               evaluate true
                   when tekst(i-left : 2) = 'qu'
                       perform shift-head-to-tail
                       perform shift-head-to-tail
                       add 2 to nr-of-right-shifts
                       move 1 to stoppen
                   when tekst(i-left : 1) = 'y' 
                       if nr-of-right-shifts = zero
                           perform shift-head-to-tail
                           add 1 to nr-of-right-shifts
                       else
                           move 1 to stoppen
                   when consonant = 1
                       add 1 to nr-of-right-shifts
                       perform shift-head-to-tail
                   when consonant = 0
                       move 1 to stoppen
               end-evaluate
           end-perform
           if nr-of-right-shifts > zero
               perform add-right-ay
           end-if
           .      

       add-right.
           add 1 to i-right
           move letter to tekst(i-right:1)
           .

       add-right-ay.
           move 'a' to letter
           perform add-right
           move 'y' to letter
           perform add-right
           .

       shift-head-to-tail.
           move tekst(i-left : 1) to letter
           move space to tekst(i-left : 1)
           add 1 to i-left
           perform add-right
           .

       finish.
           move function trim(tekst) to woord(i-woord)
           .

       first-is-consonant.
           move zero to consonant
           evaluate true
               when tekst(i-left:1) = 'a'
               when tekst(i-left:1) = 'e'
               when tekst(i-left:1) = 'i'
               when tekst(i-left:1) = 'o'
               when tekst(i-left:1) = 'u'
                   continue
               when other
                   move 1 to consonant
           end-evaluate
           .

       vul-woord-tab.
           move 1 to i
           move 1 to nr-of-woorden
           perform until i > 20
               unstring ws-input
                   delimited by ' '
                   into woord(nr-of-woorden)
                   with pointer i
               end-unstring
               add 1 to nr-of-woorden
           end-perform
           perform varying i from 1 by 1 until i > 20 
                                         or woord(i) = space
               continue
           end-perform
           compute nr-of-woorden = i - 1
           .