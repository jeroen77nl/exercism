       IDENTIFICATION DIVISION.
       PROGRAM-ID. BOB.
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 WS-HEYBOB                PIC X(60).
       01 WS-RESULT                PIC X(40).
       01 hulpvelden.
          03 h-tekst               pic x(60).
          03 sw-vraag              pic x(01).
          03 sw-all-upper          pic x(01).
          03 sw-teken-whitespace   pic x(01).
          03 sw-all-whitespace     pic x(01).
          03 sw-contains-letter    pic x(01).
          03 h-hex-code            pic x(02).
          03 h-teken               pic x(01).
          03 i                     pic 9(02).

       PROCEDURE DIVISION.
       BOB.
           perform is-vraag
           perform is-all-upper
           perform is-whitespace-tekst
           if sw-vraag = 'J'
               if sw-all-upper = 'J'
                   string 'Calm down, I know what I'
                          function char(40)
                          'm doing!'
                          delimited by size
                   into   ws-result
               else
                   move 'Sure.' to ws-result
               end-if
           else
               if sw-all-upper = 'J'
                   move 'Whoa, chill out!' to ws-result
               else
                   if sw-all-whitespace = 'J'
                       move 'Fine. Be that way!' to ws-result
                   else
                       move 'Whatever.' to ws-result
                   end-if
               end-if
           end-if              
           .

       is-vraag.
           move 'N' to sw-vraag
           move function trim(
                    function reverse(ws-heybob))
             to h-tekst
           if h-tekst(1:1) = '?'
               move 'J' to sw-vraag
           end-if
           .

       is-all-upper.
           move 'J' to sw-all-upper
           if function upper-case(ws-heybob) not = ws-heybob
               move 'N' to sw-all-upper
           else
               move 'N' to sw-contains-letter
               perform varying i from 1 by 1 until i > 60
                   if ws-heybob(i:1) >= 'A' and <= 'Z'
                       move 'J' to sw-contains-letter
                   end-if
               end-perform
               if sw-contains-letter = 'N'
                    move 'N' to sw-all-upper
               end-if
           end-if
           .

       is-whitespace-tekst.
           move 'J' to sw-all-whitespace
           perform varying i from 1 by 1 until i > 60
                                         or sw-all-whitespace ='N'
               move ws-heybob(i:1) to h-teken
               perform is-whitespace-character
               if sw-teken-whitespace = 'N'
                   move 'N' to sw-all-whitespace
               end-if
           end-perform
           .

       is-whitespace-character.
           move 'N' to sw-teken-whitespace 
           move function hex-of(h-teken) to h-hex-code
           if h-hex-code = '20' or '9' or 'D' or 'A'
               move 'J' to sw-teken-whitespace
           end-if
           .