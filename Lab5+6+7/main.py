from Grammar import Grammar
from ParserOutput import ParserOutput

if __name__ == '__main__':
    grammar = Grammar("resources/g2.in")
    parserOutput = ParserOutput(grammar)
    parserOutput.runParsing()