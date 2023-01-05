

class ParserOutput:

    def __init__(self, grammar, fileName):
        self.grammar = grammar
        self.fileName = fileName

    def runParsing(self):
        self.printMenu()
        self.createTable()

    def printMenu(self):
        print("\n------APPLICATION-----MENU--------")
        print("Non terminals: ", self.grammar.getNonTerminals())
        print("Terminals: ", self.grammar.getTerminals())
        print("Starting symbol: ", self.grammar.getStartingSymbol())
        print("Set of Values: ", self.grammar.getProductionSet())
        print("Is CFG or not: ", self.grammar.getProductionSet().getIsCFG())

    def printFileMenu(self):
        print("\n1. Print Table on command Line: ")
        print("2. Print Table on file: ")

    def createTable(self):
        tableOfValues = []
        tableOfValues.append(["Index", "Info", "Parent", "RightSibling"])
        visited = set()
        index = 1
        tableOfValues.append([index, self.grammar.getStartingSymbol(), 0, 0])
        index += 1
        self.parse(index, self.grammar.getStartingSymbol(), visited, tableOfValues)
        self.runPrintMenu(tableOfValues)

    def parse(self, index, currentSymbol, visited, tableOfValues):
        visited.add(currentSymbol)
        if currentSymbol in self.grammar.getProductionSetKeys():
            valueList = self.grammar.getProductionSetValue(currentSymbol)
            for symbol in range(0, len(valueList)):
                if symbol - 1 >= 0 and valueList[symbol] not in self.grammar.getTerminals():
                    tableOfValues.append([index, valueList[symbol], currentSymbol, valueList[symbol - 1]])
                else:
                    tableOfValues.append([index, valueList[symbol], currentSymbol, 0])
                index += 1
                if valueList[symbol] not in visited:
                    self.parse(index, valueList[symbol], visited, tableOfValues)

    def runPrintMenu(self, tableOfValues):
        self.printFileMenu()
        command = int(input("command:"))
        if command == 1:
            self.printTableToScreen(tableOfValues)
        else:
            self.printTableToFile(tableOfValues)

    def printTableToScreen(self, tableValues):
        print('    '.join(tableValues[0]))
        for i in range(1, len(tableValues)):
            for j in tableValues[i]:
                print(j, end='    |    ')
            print("\n")

    def printTableToFile(self, tableValues):
        file = open(self.fileName, "w")
        file.write("----------------Table of Values---------------\n")
        for i in range(1, len(tableValues)):
            for j in tableValues[i]:
                file.write(str(j))
                file.write('    |    ')
            file.write("\n")