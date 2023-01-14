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

    def getNonTerminals(self):
        return self.nonTerminals

    def getTerminals(self):
        return self.terminals

    def getStartingSymbol(self):
        return self.startingSymbol

    def getProductionSet(self):
        return self.productionSet

    def getProductionSetKeys(self):
        return self.productionSet.getKeys()

    def getProductionSetValue(self, key):
        return self.productionSet.getValue(key)

    def getProductionSetToString(self):
        return self.productionSet.toString()







