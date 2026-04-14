       IDENTIFICATION DIVISION.
       PROGRAM-ID. PROTEIN-TRANSLATION.
       ENVIRONMENT DIVISION.
       DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 WS-INPUT                  PIC X(60).
       01 WS-PROTEIN                PIC X(120).
       01 WS-ERROR                  PIC X(60).
       01 i                         pic 9(2).
       01 h-codon                   pic x(3).
       01 h-protein                 pic x(15).
       01 codons.
          03 codon occurs 17 
                   ascending key is name 
                   indexed by c-idx.
             05 name                pic x(3).
             05 filler              pic x(1).
             05 i-protein           pic 9(1).
       01 codon-data.
          05 filler              pic x(5) value 'AUG 1'.             
          05 filler              pic x(5) value 'UUU 2'.             
          05 filler              pic x(5) value 'UUC 2'.             
          05 filler              pic x(5) value 'UUA 3'.             
          05 filler              pic x(5) value 'UUG 3'.             
          05 filler              pic x(5) value 'UCU 4'.             
          05 filler              pic x(5) value 'UCC 4'.             
          05 filler              pic x(5) value 'UCA 4'.             
          05 filler              pic x(5) value 'UCG 4'.             
          05 filler              pic x(5) value 'UAU 5'.             
          05 filler              pic x(5) value 'UAC 5'.             
          05 filler              pic x(5) value 'UGU 6'.             
          05 filler              pic x(5) value 'UGC 6'.             
          05 filler              pic x(5) value 'UGG 7'.             
          05 filler              pic x(5) value 'UAA 8'.             
          05 filler              pic x(5) value 'UAG 8'.             
          05 filler              pic x(5) value 'UGA 8'.             

       01 proteins.
          03 protein    pic x(15)  occurs 8 .
       01 protein-data.
          05 filler     pic x(15) value 'Methionine'.
          05 filler     pic x(15) value 'Phenylalanine'.
          05 filler     pic x(15) value 'Leucine'.
          05 filler     pic x(15) value 'Serine'.
          05 filler     pic x(15) value 'Tyrosine'.
          05 filler     pic x(15) value 'Cysteine'.
          05 filler     pic x(15) value 'Tryptophan'.
          05 filler     pic x(15) value 'STOP'.
      
       PROCEDURE DIVISION.

       TRANSLATE-CODON.
           perform init
           move ws-input(1:3) to h-codon
           perform search-codon
           move h-protein to ws-protein
           if h-protein = 'STOP'
               move space to ws-protein
           end-if
           .

       TRANSLATE-RNA.
           perform init
           perform varying i from 1 by 3 until i > 60 
                                         or ws-input(i:3) = space
               move ws-input(i:3) to h-codon
               if function length(
                     function trim(h-codon)) < 3
                   move 'Invalid codon' to ws-error
                   exit perform
               end-if
      
               perform search-codon
               if ws-error not = space
                  or h-protein = 'STOP'
                   exit perform
               end-if

               if ws-protein = space
                   move h-protein to ws-protein
               else
                   string ws-protein     delimited by space
                          ','            delimited by size
                          h-protein      delimited by space
                   into   ws-protein
               end-if
           end-perform     
           .

       init.
           move space to ws-protein
           move space to ws-error
           move codon-data to codons    
           sort codon on ascending key name
           move protein-data to proteins
           .
       search-codon.
           search all codon
                  at end 
                      move 'Invalid codon' to ws-error
                  when name(c-idx) = h-codon
                      move protein(i-protein(c-idx)) to h-protein
           end-search
           .      