import random

source=raw_input('Enter data file: ')
train=raw_input('Enter train file: ')
test=raw_input('Enter test file: ')

with open(source,'r') as source:
    data=[(random.random(), line) for line in source]
data.sort()
result=[]
for n, line in data:
    lines=line.rstrip()
    result.append(lines+'\n')

file1=open(train,'w')
file2=open(test,'w')

first=result[0:1500]
first=''.join(first)
file1.write(first)

second=result[1500:2000]
second=''.join(second)
file2.write(second)

file1.close()
file2.close()

    
