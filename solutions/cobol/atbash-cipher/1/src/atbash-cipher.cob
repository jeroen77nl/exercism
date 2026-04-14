       IDENTIFICATION DIVISION.
       PROGRAM-ID. ATBASH-CIPHER.
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 WS-PHRASE                PIC X(60).
       01 WS-RESULT                PIC X(60).
       01 i                        pic 99.
       01 k                        pic 99.
       01 rank                     pic 99.
       01 rank-encr                pic 99.       
       01 ord-a                    pic 99.
       
       PROCEDURE DIVISION.
      
       ENCODE.
           move function ord('a') to ord-a
           move space to ws-result
           move function lower-case(ws-phrase) to ws-phrase
           move zero to k
           perform varying i from 1 by 1 until i > 60 
             evaluate ws-phrase(i:1)
               when >= 'a' and <= 'z'
                 add 1 to k
                 if function mod(k 6) = 0
                   add 1 to k
                 end-if
                 compute rank = 
                         function ord(ws-phrase(i:1)) - ord-a + 1
                 compute rank-encr = 27 - rank
                 move function char(rank-encr + ord-a - 1)
                   to ws-result(k:1)
               when >= '1' and <= '9'
                 add 1 to k
                 if function mod(k 6) = 0
                   add 1 to k
                 end-if
                 move ws-phrase(i:1) to ws-result(k:1)
               when other
                 continue
             end-evaluate
           end-perform
           .
       DECODE.
           move function ord('a') to ord-a
           move space to ws-result
           move function lower-case(ws-phrase) to ws-phrase
           move zero to k
           perform varying i from 1 by 1 until i > 60 
             evaluate ws-phrase(i:1)
               when space
                 continue
               when >= 'a' and <= 'z'
                 add 1 to k
                 compute rank = 
                         function ord(ws-phrase(i:1)) - ord-a + 1
                 compute rank-encr = 27 - rank
                 move function char(rank-encr + ord-a - 1)
                   to ws-result(k:1)
               when other
                 add 1 to k
                 move ws-phrase(i:1) to ws-result(k:1)
             end-evaluate
           end-perform
           .
      