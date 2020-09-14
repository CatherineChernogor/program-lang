import random

float_lst, lst = [], []


def swap(fst, ind1, ind2):
    fst[ind1], fst[ind2] = fst[ind2], fst[ind1]


for i in range(10):
    lst.append(random.uniform(-100, 100))
    float_lst.append(lst[i] - int(lst[i]))

for i in range(1, 10):
    for j in range(9):
        if float_lst[i] < float_lst[j]:
            swap(float_lst, i, j)
            swap(lst, i, j)

for num in lst:
    print(num)
