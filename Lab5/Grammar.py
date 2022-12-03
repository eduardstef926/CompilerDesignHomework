from ProductionSet import ProductionSet


class Grammar:

    def __init__(self, fileName):
        self.nonTerminals = []
        self.terminals = []
        self.startingSymbol = ""
        self.productionSet = ProductionSet()
        self.readFile(fileName)

    def readFile(self, fileName):
        file = open(fileName, "r")
        data = file.readlines()
        self.nonTerminals = data[0].strip()
        self.terminals = data[1].strip()
        self.startingSymbol = data[2].strip()
        for line in range(3, len(data)):
            currentLine = data[line].strip()
            currentLine = currentLine.split('->')
            key = currentLine[0]
            values = currentLine[1].split(' ')
            self.productionSet.addProduction(key, values)

    def printValues(self):
        print("\n")
        print("Non terminals: ", self.nonTerminals)
        print("Terminals: ", self.terminals)
        print("Starting symbol: ", self.startingSymbol)
        print("Set of Values: ", self.productionSet.getProductionSet())
        print("Is CFG or not: ", self.productionSet.getIsCFG())
        print("\n")

    def printTable(self, tableValues):
        print('    '.join(tableValues[0]))
        for i in range(1, len(tableValues)):
            for j in tableValues[i]:
                print(j, end = '    |    ')
            print("\n")

    def createTable(self):
        tableOfValues = []
        tableOfValues.append(["Index", "Info", "Parent", "RightSibling"])
        visited = set()
        index = 1
        tableOfValues.append([index, self.startingSymbol, 0, 0])
        index += 1
        self.parse(index, self.startingSymbol, visited, tableOfValues)
        self.printTable(tableOfValues)

    def parse(self, index, currentSymbol, visited, tableOfValues):
        visited.add(currentSymbol)
        if currentSymbol  in self.productionSet.getProductionSet().keys():
            valueList = self.productionSet.getProductionSet().get(currentSymbol)
            for symbol in range(0, len(valueList)):
                if symbol - 1 >= 0 and valueList[symbol] not in self.terminals:
                    tableOfValues.append([index, valueList[symbol], currentSymbol, valueList[symbol - 1]])
                else:
                    tableOfValues.append([index, valueList[symbol], currentSymbol, 0])
                index += 1
                if valueList[symbol] not in visited:
                    self.parse(index, valueList[symbol], visited, tableOfValues)







