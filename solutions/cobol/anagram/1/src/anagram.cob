       IDENTIFICATION DIVISION.
       PROGRAM-ID. ANAGRAM.

       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 WS-SUBJECT PIC X(20).
       01 WS-CANDIDATES-COUNT PIC 9.
       01 WS-CANDIDATES-TABLE.
           02 WS-CANDIDATES OCCURS 1 TO 20
                            DEPENDING ON WS-CANDIDATES-COUNT.
              05 WS-CANDIDATE PIC X(20).
       01 WS-RESULT-LIST PIC X(48).

       01 ws-fields.
          03 ws-woord               pic x(20).
          03 ws-tekst-21            pic x(21).
          03 ws-subj-i              pic 9(2).
          03 ws-cand-i              pic 9(2).
          03 ws-cands-i             pic 9(2).
          03 ws-letter-i            pic 9(2).
          03 ws-subject-tab.
             05 ws-subject-count    pic 9(1) occurs 26.
          03 ws-candidate-count-tab.
             05 ws-candidate-count  pic 9(1) occurs 26.

       01 ws-constants.
          03 ws-ord-a               pic 9(3).
      
       PROCEDURE DIVISION.

       FIND-ANAGRAMS.
           perform init.
           perform fill-subject-tab.
           perform process-candidate-word
                           varying ws-cands-i from 1 by 1
                           until ws-cands-i > ws-candidates-count.
       init.
           initialize ws-fields.
           initialize ws-result-list.
           compute ws-ord-a = function ord ('a').
      
       fill-subject-tab.
           move function lower-case(ws-subject) to ws-subject.
           perform tel-letters-subject varying ws-subj-i from 1 by 1
                                       until ws-subj-i > 20.

       tel-letters-subject.
           if ws-subject(ws-subj-i:1) >= 'a' and <= 'z'
              compute ws-letter-i = 
                      function ord (ws-subject(ws-subj-i:1)) 
                      - ws-ord-a 
                      + 1
              add 1 to ws-subject-count(ws-letter-i)
           end-if.

       process-candidate-word.
           move function lower-case( ws-candidate(ws-cands-i) ) 
             to ws-woord.
           if ws-woord <> ws-subject
               perform process-candidate-letter-count
           end-if.

       process-candidate-letter-count.
           initialize ws-candidate-count-tab
           perform tel-letters-candidate 
                   varying ws-cand-i from 1 by 1
                   until ws-cand-i > 20
           if ws-candidate-count-tab = ws-subject-tab
              if ws-result-list = spaces
                  move ws-candidate(ws-cands-i)
                    to ws-result-list
              else
                  string ws-result-list           delimited by space
                         ','                     
                         ws-candidate(ws-cands-i) delimited by space
                  into   ws-result-list
              end-if
          end-if.

       tel-letters-candidate.
           if ws-woord(ws-cand-i:1) >= 'a' and <= 'z'
              compute ws-letter-i = 
                      function ord (ws-woord(ws-cand-i:1)) 
                      - ws-ord-a 
                      + 1
              add 1 to ws-candidate-count(ws-letter-i)
           end-if.
