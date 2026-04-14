       IDENTIFICATION DIVISION.
       PROGRAM-ID. KNAPSACK.
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 WS-INPUTS.
           05 MAXIMUM-WEIGHT    PIC 999.
           05 NO-OF-ITEMS       PIC 99.
           05 KNAPSACKTABLE.
             07 KNAPSACKTABLE-ENTRY OCCURS 1 TO 20 
                                    DEPENDING ON NO-OF-ITEMS.
              10 WS-WEIGHT      PIC 999.
              10 WS-VALUE       PIC 999.
       01 WS-OUTPUTS.
           05 WS-RESULT         PIC 99999.

       01 hulpvelden.
          03 max                pic 9(03).
          03 n                  pic 9(02).
          03 w                  pic 9(03).
          03 i                  pic 9(03).
          03 j                  pic 9(03).
          03 j-max              pic 9(03).
       01 dp-tab.
          03 dp occurs 1000     pic 9(05).
      
       PROCEDURE DIVISION.
 
       MAXIMUM-VALUE.
           move maximum-weight to w
           move no-of-items to n

           perform knapsack
 
           move dp(w + 1) to ws-result
           .

       knapsack.
           initialize dp-tab
           perform varying i from 2 by 1 until i > n + 1
               compute j-max = w + 1
               perform varying j from j-max by -1 until j = 0
                   if ws-weight(i - 1) <= j
                        compute dp(j) = function max
                          ( dp(j) 
                            dp(j - ws-weight(i - 1)) + ws-value(i - 1) 
                          )
                   end-if
               end-perform
           end-perform
           .
