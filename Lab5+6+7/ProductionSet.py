
class ProductionSet:
    def __init__(self):
        self.hashMap = {}
        self.isCFG = True

    def getSet(self):
        return self.hashMap

    def getKeys(self):
        return self.hashMap.keys()

    def getValue(self, key):
        return self.hashMap.get(key)

    def getIsCFG(self):
        return self.isCFG

    def addProduction(self,key, values):
        if key not in self.hashMap.keys():
            self.hashMap[key] = []
        else:
            self.isCFG = False
        self.hashMap[key].extend(values)

    def toString(self):
        hashMap = ""
        for i in self.hashMap.keys():
            hashMap += "["
            hashMap += i
            hashMap += "->"
            hashMap += str(self.hashMap[i])
            hashMap += "], "
        return hashMap