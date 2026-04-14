       IDENTIFICATION DIVISION.
       PROGRAM-ID. MEETUP.
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 ws-yyyymmdd-x.
          03 WS-YEAR               PIC 9999.
          03 WS-MONTH              PIC 99.
          03 ws-day                pic 99.
       01 ws-yyyymmdd redefines ws-yyyymmdd-x
                                   pic 9(8).
       01 WS-WEEK                  PIC X(10).
       01 WS-DAYOFWEEK             PIC X(10).
       01 WS-RESULT                PIC X(40).
       01 is-leap                  pic 9.
       01 y                        pic 9(4).
       01 y4                       pic 9(4).
       01 y100                     pic 9(4).
       01 y400                     pic 9(4).
       01 d-min                    pic 99.
       01 d-max                    pic 99.

       01 WS-WEEKDAY-NAME  PIC X(10) VALUE SPACES.
       01 ws-weekday-num   pic 9 value zero.
       01 ws-m-corr        pic 9(1).
       01 ws-temp          pic 9(10).

       01 datum.
          03 jaar          pic 9(4).
          03 filler        pic x value '-'.
          03 maand         pic 9(2).
          03 filler        pic x value '-'.
          03 dag           pic 9(2).
         
       01 WEEKDAY-NAMES.
          05 WS-SUNDAY      PIC X(10) VALUE 'SUNDAY   '.
          05 WS-MONDAY      PIC X(10) VALUE 'MONDAY   '.
          05 WS-TUESDAY     PIC X(10) VALUE 'TUESDAY  '.
          05 WS-WEDNESDAY   PIC X(10) VALUE 'WEDNESDAY'.
          05 WS-THURSDAY    PIC X(10) VALUE 'THURSDAY '.
          05 WS-FRIDAY      PIC X(10) VALUE 'FRIDAY   '.
          05 WS-SATURDAY    PIC X(10) VALUE 'SATURDAY '.
       PROCEDURE DIVISION. 

       MEETUP.
         evaluate ws-week
         when 'teenth'           
           perform varying ws-day from 13 by 1 until ws-day > 19
               PERFORM DETERMINE-WEEKDAY2
               if function upper-case(ws-dayofweek) = ws-weekday-name
                   move ws-day to dag
                   move ws-month to maand
                   move ws-year to jaar
                   move datum to ws-result
               end-if
           end-perform
           display ws-result
         when 'first'
           perform varying ws-day from 1 by 1 until ws-day > 7
               PERFORM DETERMINE-WEEKDAY2
               if function upper-case(ws-dayofweek) = ws-weekday-name
                   move ws-day to dag
                   move ws-month to maand
                   move ws-year to jaar
                   move datum to ws-result
               end-if
           end-perform
           display ws-result
         when 'second'
           perform varying ws-day from 8 by 1 until ws-day > 14
               PERFORM DETERMINE-WEEKDAY2
               if function upper-case(ws-dayofweek) = ws-weekday-name
                   move ws-day to dag
                   move ws-month to maand
                   move ws-year to jaar
                   move datum to ws-result
               end-if
           end-perform
           display ws-result
         when 'third'
           perform varying ws-day from 15 by 1 until ws-day > 21
               PERFORM DETERMINE-WEEKDAY2
               if function upper-case(ws-dayofweek) = ws-weekday-name
                   move ws-day to dag
                   move ws-month to maand
                   move ws-year to jaar
                   move datum to ws-result
               end-if
           end-perform
           display ws-result
         when 'fourth'
           perform varying ws-day from 22 by 1 until ws-day > 28
               PERFORM DETERMINE-WEEKDAY2
               if function upper-case(ws-dayofweek) = ws-weekday-name
                   move ws-day to dag
                   move ws-month to maand
                   move ws-year to jaar
                   move datum to ws-result
               end-if
           end-perform
           display ws-result
         when 'last'
           perform bepaal-laatste-week
           perform varying ws-day from d-min by 1 until ws-day > d-max
               PERFORM DETERMINE-WEEKDAY2
               if function upper-case(ws-dayofweek) = ws-weekday-name
                   move ws-day to dag
                   move ws-month to maand
                   move ws-year to jaar
                   move datum to ws-result
               end-if
           end-perform
           display ws-result
         end-evaluate
         .

       determine-weekday2.
              evaluate ws-month
                  when  1 move 0 to ws-m-corr
                  when  2 move 3 to ws-m-corr
                  when  3 move 2 to ws-m-corr
                  when  4 move 5 to ws-m-corr
                  when  5 move 0 to ws-m-corr
                  when  6 move 3 to ws-m-corr
                  when  7 move 5 to ws-m-corr
                  when  8 move 1 to ws-m-corr
                  when  9 move 4 to ws-m-corr
                  when 10 move 6 to ws-m-corr
                  when 11 move 2 to ws-m-corr
                  when 12 move 4 to ws-m-corr
              end-evaluate
              if ws-month < 3
                  compute y = ws-year - 1
              else
                  compute y = ws-year
              end-if
              compute y4 = y / 4
              compute y100 = y / 100
              compute y400 = y / 400
              compute ws-temp = y + y4 - y100 + y400 + 
                  ws-m-corr + ws-day
              compute ws-weekday-num = function mod(ws-temp, 7)
           EVALUATE ws-weekday-num
               WHEN 0 MOVE WS-SUNDAY TO WS-WEEKDAY-NAME
               WHEN 1 MOVE WS-MONDAY TO WS-WEEKDAY-NAME
               WHEN 2 MOVE WS-TUESDAY TO WS-WEEKDAY-NAME
               WHEN 3 MOVE WS-WEDNESDAY TO WS-WEEKDAY-NAME
               WHEN 4 MOVE WS-THURSDAY TO WS-WEEKDAY-NAME
               WHEN 5 MOVE WS-FRIDAY TO WS-WEEKDAY-NAME
               WHEN 6 MOVE WS-SATURDAY TO WS-WEEKDAY-NAME
               WHEN OTHER DISPLAY "ERROR: Invalid weekday number."
           END-EVALUATE
           .
       leap.
           move 0 to is-leap
           if function mod(ws-year, 4) = 0
              and (function mod(ws-year, 100) not = 0
                   or
                   function mod(ws-year, 400) = 0)
               move 1 to is-leap
           end-if
           .
       bepaal-laatste-week.
           perform leap
           evaluate ws-month
           when 2
               if is-leap = 1
                   move 29 to d-max
               else
                   move 28 to d-max
               end-if
           when 1
           when 3
           when 5
           when 7
           when 8
           when 10
           when 12
               move 31 to d-max
           when other
               move 30 to d-max
           end-evaluate

           compute d-min = d-max - 6
           .