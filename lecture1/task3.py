
import sys
import os

lines = open('src/words.txt').readlines()
maxlen = 0

for word in lines:
    if len(word) > maxlen:
        output = word
        maxlen = len(word)

print(output)
