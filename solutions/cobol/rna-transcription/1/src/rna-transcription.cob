       IDENTIFICATION DIVISION.
       PROGRAM-ID. rna-transcription.

       DATA DIVISION.
       WORKING-STORAGE SECTION.
      
       01 WS-COMPLEMENT PIC X(64).

       01 hulpvelden.
          03 i                       pic 9(02).
          03 dna-letter-tab.
             05 dna-letter occurs 64 pic x(01).
          03 rna-letter-tab.
             05 rna-letter occurs 64 pic x(01).

       PROCEDURE DIVISION.
       RNA-TRANSCRIPTION.
           initialize hulpvelden
           move ws-complement to dna-letter-tab

           perform varying i from 1 by 1 
                             until i > 64 
                             or    dna-letter(i) = space
               evaluate dna-letter(i)
               when 'G' move 'C' to rna-letter(i)
               when 'C' move 'G' to rna-letter(i)
               when 'T' move 'A' to rna-letter(i)
               when 'A' move 'U' to rna-letter(i)
           end-perform
      
           move rna-letter-tab to ws-complement
           .
