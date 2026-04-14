       IDENTIFICATION DIVISION.
       PROGRAM-ID. GRADE-SCHOOL.
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 WS-STUDENTNAME           PIC X(60).
       01 WS-STUDENTGRADE          PIC 9.
       01 WS-DESIREDGRADE          PIC 9.
       01 WS-RESULT                PIC X(5).
       01 ws-student-found         pic x.
       01 i                        pic 9(02).
       01 j                        pic 9(02).
       01 ws-display               pic x(20).
       
       01 STUDENTROSTER.
           02 ROSTER               OCCURS 10 TIMES.
              05 ST-NAME           PIC X(60).
              05 ST-GRADE          PIC 9.   

       01 TEMPROSTER.
           02 TMP-ROSTER            OCCURS 10 TIMES.
              05 TMP-NAME           PIC X(60).
              05 TMP-GRADE          PIC 9.   
       
       PROCEDURE DIVISION.
        
       init-roster.
           initialize temproster
           .
       add-student.
           perform existing-student
           if ws-student-found = 'N'
               move ws-studentname to tmp-name(1)
               move ws-studentgrade to tmp-grade(1)
           end-if
           sort tmp-roster ascending st-grade st-name
           initialize studentroster
           perform aanschuiven
           .

       get-grade.
           initialize studentroster
           move zero to j
           perform varying i from 1 by 1 until i > 10
             if tmp-grade(i) = ws-desiredgrade
               add 1 to j
               move tmp-name(i) to st-name(j)
               move tmp-grade(i) to st-grade(j)
             end-if
           end-perform
           .

       existing-student.
           move 'N' to ws-student-found
           perform varying i from 1 by 1 until i > 10
               if tmp-name(i) = ws-studentname
                   move 'J' to ws-student-found
               end-if
           end-perform
           .

       aanschuiven.
           move zero to j
           perform varying i from 1 by 1 until i > 10
             if tmp-name(i) not = space
               add 1 to j
               move tmp-name(i) to st-name(j)
               move tmp-grade(i) to st-grade(j)
             end-if
           end-perform
           .