       IDENTIFICATION DIVISION.
       PROGRAM-ID. ROBOT-SIMULATOR.
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 WS-X-COORD               PIC S999.
       01 WS-Y-COORD               PIC S999.
       01 WS-DIRECTION             PIC X(20).
       01 WS-INSTRUCTIONS          PIC X(60).

       01 h-direction-tab.
          03 h-direction           pic x(5) occurs 4 
                                            indexed by i-direction.
       01 h-direction-data.
          03 filler                pic x(5) value 'north'.
          03 filler                pic x(5) value 'east'.
          03 filler                pic x(5) value 'south'.
          03 filler                pic x(5) value 'west'.
       01 h-dir                    pic 9.
       01 i                        pic 99.
       
       PROCEDURE DIVISION.
       
       CREATE-ROBOT.
           continue
           .      
      
       MOVE-ROBOT.
           move h-direction-data to h-direction-tab

           set i-direction to 1
           search h-direction 
                  when h-direction(i-direction) = ws-direction
                       move i-direction to h-dir
           end-search

           perform varying i from 1 by 1 
                   until i > function length(
                                 function trim(ws-instructions))
               evaluate ws-instructions(i:1)
                   when 'A' perform advance
                   when 'L' perform rotate-ccw
                   when 'R' perform rotate-cw
               end-evaluate
           end-perform
           move h-direction(h-dir) to ws-direction
           .

       advance.
           display 'x: ' ws-x-coord ' y: ' ws-y-coord ' h-dir: ' h-dir
           evaluate h-direction(h-dir)
               when 'north' add 1 to ws-y-coord
               when 'east'  add 1 to ws-x-coord
               when 'south' subtract 1 from ws-y-coord
               when 'west'  subtract 1 from ws-x-coord
           end-evaluate
           display 'x: ' ws-x-coord ' y: ' ws-y-coord ' h-dir: ' h-dir
           .

       rotate-ccw.
           subtract 1 from h-dir
           if h-dir < 1
               move 4 to h-dir
           end-if
           .

       rotate-cw.
           add 1 to h-dir
           if h-dir > 4
               move 1 to h-dir
           end-if
           .