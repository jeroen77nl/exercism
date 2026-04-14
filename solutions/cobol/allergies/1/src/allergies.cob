       IDENTIFICATION DIVISION.
       PROGRAM-ID. allergies.

       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 WS-SCORE       PIC 999.
       01 WS-ITEM        PIC X(12).
       01 WS-RESULT      PIC A.
       01 WS-RESULT-LIST PIC X(108).
       01 i-pow          pic 9(2).
       01 power          pic 9(10).
       01 i-tab          pic 9.
       01 i              pic 9.
       01 power-tab.
          03 power-value occurs 8 pic 9(03).
       01 ws-value       pic 9(03).

       01 allergy-tab.
          03 allergy-entry occurs 8 indexed by i-search.
             05 value-entry      pic 9(03).
             05 descr-entry       pic x(15).

       PROCEDURE DIVISION.
       
       ALLERGIC-TO.
           move zero to power-tab
           move zero to i-tab
           move space to ws-result-list
           move 'N' to ws-result
           perform init-allergy-tab
           perform calc-highest-pow-of-2
           perform fill-power-tab
           
           set i-search to 1
           search allergy-entry
               when descr-entry(i-search) = ws-item
                   move value-entry(i-search) to ws-value
           end-search

           perform varying i from 1 by 1 until i > 8
               if ws-value = power-value(i)
                   move 'Y' to ws-result
               end-if
           end-perform
           .

       LIST-ALLERGENS.
      * Code this paragraph
           move zero to power-tab
           move zero to i-tab
           move space to ws-result-list
           move space to ws-result
           perform init-allergy-tab
           perform calc-highest-pow-of-2
           perform fill-power-tab
           perform varying i from i-tab by -1 until i < 1
               set i-search to 1
               search allergy-entry
                   when value-entry(i-search) = power-value(i)
                       if ws-result-list = space
                           move descr-entry(i-search) to ws-result-list
                       else      
                           string ws-result-list 
                                  ',' 
                                  descr-entry(i-search)
                                  delimited by space
                             into ws-result-list
                       end-if
               end-search
           end-perform
           .

       calc-highest-pow-of-2.
           move 1 to power
           move 1 to i-pow           
           perform until power > ws-score
              compute power = power * 2
              compute i-pow = i-pow + 1
           end-perform
           compute power = power / 2
           compute i-pow = i-pow - 1
           .

       init-allergy-tab.
           move 1 to value-entry(1)
           move 'eggs' to descr-entry(1)
           move 2 to value-entry(2)
           move 'peanuts' to descr-entry(2)
           move 4 to value-entry(3)
           move 'shellfish' to descr-entry(3)
           move 8 to value-entry(4)
           move 'strawberries' to descr-entry(4)
           move 16 to value-entry(5)
           move 'tomatoes' to descr-entry(5)
           move 32 to value-entry(6)
           move 'chocolate' to descr-entry(6)
           move 64 to value-entry(7)
           move 'pollen' to descr-entry(7)
           move 128 to value-entry(8)
           move 'cats' to descr-entry(8)
           .

       fill-power-tab.
           perform until power = zero or ws-score = 0
               if power <= ws-score
                   if power <= 128
                       add 1 to i-tab
                       move power to power-value(i-tab)
                   end-if
                   compute ws-score = ws-score - power
               end-if
               compute power = power / 2
           end-perform
           .