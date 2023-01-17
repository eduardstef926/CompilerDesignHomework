from Grammar import Grammar
from HashTable import HashTable
from ParserOutput import ParserOutput
from Scanner import Scanner

if __name__ == '__main__':
    st = HashTable()
    ct = HashTable()
    scanner = Scanner("resources/p1.in", st, ct)
    grammar1 = Grammar("resources/g1.in")
    parserOutput1 = ParserOutput(grammar1, "resources/outputTable.txt", scanner)
    parserOutput1.runParsing()
    grammar2 = Grammar("resources/g2.in")
    parserOutput2 = ParserOutput(grammar2, "resources/outputTable.txt", scanner)
    parserOutput2.runParsing()
