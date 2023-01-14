
class HashTable:
    def __init__(self):
        self.table = {}
        self.hashNr = 7

    def hashFunction(self, symbol):
        s = 0
        for elem in symbol:
            s += ord(elem)
        return s % self.hashNr

    def checkIfSymbolInST(self, symbol):
        poz = self.hashFunction(symbol)
        if poz in self.table.keys():
            for elem in self.table[poz]:
                if elem == symbol:
                    return True
        return False

    def addToST(self, symbol):
        poz = self.hashFunction(symbol)
        if poz not in self.table.keys():
            self.table[poz] = []

        if not self.checkIfSymbolInST(symbol):
            self.table[poz].append(symbol)

    def getSymbols(self, poz):
        if poz in self.table.keys():
            return self.table[poz]

    def getSymbol(self, poz, index):
        return self.table[poz][index]

    def getSymbolIndex(self, symbol):
        poz = self.hashFunction(symbol)
        if poz in self.table.keys():
            return self.table[poz].index(symbol)