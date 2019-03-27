import re
import bsddb

word=[]
identifier=12
DB=bsddb.hashopen('WordDic2.db','c')

f=open("dataset.txt") #open file
line=f.readlines() #read all lines from text file
str=''.join(line) #to string
str=str.lower()
word=re.compile(r'\W+', re.UNICODE).split(str) #choose alphanumeric only

for y, data in enumerate(word):
    if data not in DB:
        DB['%s'%word[y]]='%d'%identifier
        identifier=identifier+1

DB.sync()
DB.close()
