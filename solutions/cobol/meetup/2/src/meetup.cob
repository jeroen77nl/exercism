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
         
       01 weekday-names.
          05 ws-sunday      pic x(10) value 'sunday   '.
          05 ws-monday      pic x(10) value 'monday   '.
          05 ws-tuesday     pic x(10) value 'tuesday  '.
          05 ws-wednesday   pic x(10) value 'wednesday'.
          05 ws-thursday    pic x(10) value 'thursday '.
          05 ws-friday      pic x(10) value 'friday   '.
          05 ws-saturday    pic x(10) value 'saturday '.
               
       PROCEDURE DIVISION. 

       MEETUP.
         evaluate ws-week
         when 'teenth'           
           move 13 to d-min
           move 19 to d-max
           perform zoek-de-weekdag
         when 'first'
           move 1 to d-min
           move 7 to d-max
           perform zoek-de-weekdag
         when 'second'
           move 8 to d-min
           move 14 to d-max
           perform zoek-de-weekdag
         when 'third'
           move 15 to d-min
           move 21 to d-max
           perform zoek-de-weekdag
         when 'fourth'
           move 22 to d-min
           move 28 to d-max
           perform zoek-de-weekdag
         when 'last'
           perform bepaal-laatste-week
           perform zoek-de-weekdag
         end-evaluate
         .

       zoek-de-weekdag.
           perform varying ws-day from d-min by 1 
                                  until ws-day > d-max
               perform determine-weekday
               if function lower-case(ws-dayofweek) = ws-weekday-name
                   move ws-day to dag
                   move ws-month to maand
                   move ws-year to jaar
                   move datum to ws-result
               end-if
           end-perform
           display ws-result
           .      
      
       determine-weekday.
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
           evaluate ws-weekday-num
               when 0 move ws-sunday to ws-weekday-name
               when 1 move ws-monday to ws-weekday-name
               when 2 move ws-tuesday to ws-weekday-name
               when 3 move ws-wednesday to ws-weekday-name
               when 4 move ws-thursday to ws-weekday-name
               when 5 move ws-friday to ws-weekday-name
               when 6 move ws-saturday to ws-weekday-name
               when other display "ERROR: Invalid weekday number."
           end-evaluate
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
           when 1 when 3  when 5 when 7
           when 8 when 10 when 12
               move 31 to d-max
           when other
               move 30 to d-max
           end-evaluate

           compute d-min = d-max - 6
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