import re
import os
import bsddb

word=[]
identifier=10
all_files=os.listdir("review/") #get all text file from directory
DB=bsddb.hashopen('WordDic.db','c')

for n, x in enumerate(all_files): #iterate through all files
    f=open("review/"+x) #open file
    line=f.readlines() #read all lines from text file
    str=''.join(line) #to string
    word=re.compile(r'\W+', re.UNICODE).split(str) #choose alphanumeric only
    for y, data in enumerate(word):
        if data not in DB:
            DB['%s'%word[y]]='%d'%identifier
            identifier=identifier+1

DB.sync()
DB.close()
