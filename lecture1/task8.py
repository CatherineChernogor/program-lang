class Byte:

    def __init__(self, num):
        self.value = []
        i = 7
        while (num >= 1):
            self.value.append(num % 2)
            num = num//2
            i-=1

        if len(self.value) < 8:
            ind = 8 - len(self.value)
            for i in range(ind):
                self.value.append(0)

        self.value = self.value[::-1]

    def print_b(self):
        print(self.value)

    def read():
        pass

    def __setitem__(self, index, number):
        self.value[index] = number

    def __getitem__(self, index):
        return self.value[index]


    def __or__(self, other):
        new = Byte(0)
        k = 7
        for i, j in zip(self.value, other.value):
            print(i, j)
            new[k] = (i or j)
            k-=1
        return new
            


bts = Byte(26)
#bts.print_b()
bts2 = Byte(5)
#bts2.print_b()

#bts2[6] = 1
#bts2.print_b()
bts3 = bts or bts2
#bts3.print_b()