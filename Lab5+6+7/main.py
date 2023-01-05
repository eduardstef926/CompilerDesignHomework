from Grammar import Grammar
from ParserOutput import ParserOutput

if __name__ == '__main__':
    grammar1 = Grammar("resources/g1.in")
    parserOutput1 = ParserOutput(grammar1, "resources/output1.txt")
    parserOutput1.runParsing()
    grammar2 = Grammar("resources/g2.in")
    parserOutput2 = ParserOutput(grammar2, "resources/output2.txt")
    parserOutput2.runParsing()