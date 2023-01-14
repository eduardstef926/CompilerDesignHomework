import re


class Scanner:
    def __init__(self, file, symbolTable, ctTable):
        self.file = file
        self.symbolTable = symbolTable
        self.constantTable = ctTable
        self.tokens = []
        self.readTokenFile('resources/token.in')
        self.pif = []
        self.exceptionList = []
        self.readCode()
        if len(self.exceptionList) == 0:
            print('Lexically correct')
            self.save(self.pif, 'resources/pif.out')
            self.saveST(self.symbolTable.table, 'resources/st.out')
            self.saveST(self.constantTable.table, 'resources/ct.out')
        else:
            print('Lexical error:')
            print(self.exceptionList)

    def getPif(self):
        return self.pif

    def readTokenFile(self, file='resources/token.in'):
        f = open(file, 'rt')  # read text
        lines = f.readlines()
        f.close()

        for line in lines:
            line = line.split(' ')
            self.tokens.append(line[0].strip('\n'))

    def save(self, list, file):
        f = open(file, 'w')
        for elem in list:
            f.write(str(elem))
            f.write('\n')

    def saveST(self, list, file):
        f = open(file, 'w')
        f.write(str(list))

    def checkIdentifier(self, value):
        for char in value:
            if not char.isalpha() and not char.isdigit():
                return False
        return True

    def readCode(self):
        f = open(self.file, 'rt')  # read text
        lines = f.readlines()
        f.close()
        y = 0
        for line in lines:
            y += 1
            line = line.strip('\n')
            line = line.strip('\t')
            line = line.strip(' ')
            line = re.split('([^a-zA-Z0-9])', line)
            line = [i for i in line if i != '']  # filter all empty strings
            for i in range(len(line)):
                if line[i] != ' ':
                    if line[i] not in self.tokens:
                        poz = self.symbolTable.hashFunction(line[i])
                        if line[i].isdigit():
                            aux = line[i]
                            if i - 1 > 0 and line[i - 1] == "+" or line[i - 1] == "-":  # treat +- constant integers
                                aux = line[i - 1] + line[i]
                            if not self.constantTable.checkIfSymbolInST(aux):
                                self.constantTable.addToST(aux)
                            self.pif.append({"constant": (poz, self.constantTable.getSymbolIndex(aux))})
                        elif i - 1 > 0 and line[i - 1] == '"':
                            if i == len(line) - 1 or line[i + 1] != '"':  # treat "a
                                self.exceptionList.append('Quote not ended on line ' + str(y))
                            else:
                                if not self.checkIdentifier(line[i]):
                                    self.exceptionList.append('Constant format not accepted on line ' + str(y))
                                else:
                                    if not self.constantTable.checkIfSymbolInST(line[i]):
                                        self.constantTable.addToST(line[i])
                                    self.pif.append({"constant": (poz, self.constantTable.getSymbolIndex(line[i]))})
                        elif i + 1 < len(line) and line[i + 1] == '"':  # treat a"
                            if i - 1 > 0 and line[i - 1] != '"':
                                self.exceptionList.append('Quote beginning missing on line ' + str(y))
                        else:  # identifier case
                            if not line[i][0].isalpha() or not self.checkIdentifier(line[i]):
                                self.exceptionList.append('Identifier format not accepted on line ' + str(y))
                            else:
                                if not self.symbolTable.checkIfSymbolInST(line[i]):
                                    self.symbolTable.addToST(line[i])
                                    self.pif.append({"identifier": (poz, self.symbolTable.getSymbolIndex(line[i]))})
                                else:
                                    self.pif.append({"identifier": (poz, self.symbolTable.getSymbolIndex(line[i]))})
                    else:
                        if i - 1 > 0 and line[i - 1] == '"':
                            if i + 1 < len(line) and line[i + 1] == '"':
                                if not self.checkIdentifier(line[i]):
                                    self.exceptionList.append('Constant format not accepted on line ' + str(y))
                                else:
                                    poz = self.symbolTable.hashFunction(line[i])
                                    if not self.constantTable.checkIfSymbolInST(line[i]):
                                        self.constantTable.addToST(line[i])
                                    self.pif.append({"constant": (poz, self.constantTable.getSymbolIndex(line[i]))})
                            else:
                                self.exceptionList.append("Quote end missing on line " + str(y))
                        elif i + 1 < len(line) and line[i + 1] == '"':
                            self.exceptionList.append('Quote beginning missing on line ' + str(y))
                        else:
                            if line[i] == 'int' or line[i] == 'string':
                                if i == len(line) - 1:
                                    self.exceptionList.append('No symbol for data type declared on line ' + str(y))
                                elif line[i + 1] == ']':
                                    self.exceptionList.append(
                                        'No open bracket found for type declaration on line ' + str(y))
                                elif line[i + 1] == '[' and (i + 1 == len(line) - 1 or line[i + 2] != ']'):
                                    self.exceptionList.append(
                                        'No closed bracket found for type declaration on line ' + str(y))
                            if line[i] == '=' and i == len(line) - 1:
                                self.exceptionList.append('No assigned value found on line ' + str(y))
                            self.pif.append({line[i]: -1})