import os
import sys

lines = open('src/lorem.txt').readlines()
dct = dict()

for line in lines:
    print(line)
    for word in line.split():
        if word not in dct.keys():
            dct[word] = 1
        else:
            dct[word] += 1


print(dct)
