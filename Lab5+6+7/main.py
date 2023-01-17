from Grammar import Grammar
from HashTable import HashTable
from ParserOutput import ParserOutput
from Scanner import Scanner

if __name__ == '__main__':
    st = HashTable()
    ct = HashTable()
    scanner = Scanner("resources/p1.in", st, ct)
    grammar2 = Grammar("resources/grammar.in")
    parserOutput2 = ParserOutput(grammar2, "resources/output2.txt", scanner)
    parserOutput2.runParsing()
