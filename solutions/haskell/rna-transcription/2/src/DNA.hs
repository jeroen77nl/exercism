module DNA (toRNA) where

toRNA :: String -> Either Char String
toRNA dna = toRNAiter dna []

toRNAiter :: String -> String -> Either Char String
toRNAiter dna rna = case (dna, rna) of
    ([], _) -> Right $ reverse rna
    ('G':dna_rest, _)  -> toRNAiter dna_rest ('C':rna)
    ('C':dna_rest, _)  -> toRNAiter dna_rest ('G':rna)
    ('T':dna_rest, _)  -> toRNAiter dna_rest ('A':rna)
    ('A':dna_rest, _)  -> toRNAiter dna_rest ('U':rna)
    (c:_, _) -> Left c
