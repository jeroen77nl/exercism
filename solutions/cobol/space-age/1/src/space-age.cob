       IDENTIFICATION DIVISION.
       PROGRAM-ID. SPACE-AGE.
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 WS-INPUT                 PIC 9(30).
       01 WS-WHICH-PLANET          PIC X(30).
       01 WS-RESULT                PIC 9(4)V99.
       01 WS-ERROR                 PIC X(30).
       01 hulpvelden.
          03 orbital-period        pic 9(3)v9(10).
          03 earth-years           pic 9(4)v9(4).
          03 planet-years          pic 9(4)v9(2).
       
       PROCEDURE DIVISION.
       
       ROUNDS-TO.
           evaluate ws-which-planet
               when 'Mercury' move 0.2408467  to orbital-period
               when 'Venus'   move 0.61519726 to orbital-period
               when 'Earth'   move 1.0        to orbital-period
               when 'Mars'    move 1.8808158  to orbital-period
               when 'Jupiter' move 11.862615  to orbital-period
               when 'Saturn'  move 29.447498  to orbital-period
               when 'Uranus'  move 84.016846  to orbital-period
               when 'Neptune' move 164.79132  to orbital-period
               when other     move 'not a planet' to ws-error
           end-evaluate

           if ws-error = space
               compute earth-years = ws-input / 31557600
               display 'earth-years: ' earth-years
               compute planet-years rounded = 
                       earth-years / orbital-period
               move planet-years to ws-result
           end-if
      
           .