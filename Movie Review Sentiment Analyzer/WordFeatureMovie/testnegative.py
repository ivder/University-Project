import re
import os
import sys
import bsddb
from nltk import PorterStemmer
from nltk.corpus import stopwords

word=[]
listkey=[]
identifier=1
wordfreq=[]
result="-1"
all_files=os.listdir("neg/") #get all text file from directory
savefile=open('resultneg.txt','w')
DB=bsddb.hashopen('WordDic.db','c')
stopwordslist=stopwords.words('english')
stemmer=PorterStemmer()
#normalizing textual data
for n, x in enumerate(all_files): #iterate through all files
    f=open("neg/"+x) #open file
    line=f.readlines() #read all lines from text file
    str=''.join(line) #to string
    word=re.compile(r'\W+', re.UNICODE).split(str) #choose alphanumeric only
    for y, data in enumerate(word):
        word[y]=stemmer.stem(word[y])
        if data not in stopwordslist and data in DB:
            wordfreq.append(word.count(data)) #count frequency
            #dictfreq=dict(zip(word,wordfreq)) #use dict to combine the repeated word
            listkey.append(DB[data])
            listkey=map(int,listkey)
            dictresult=dict(zip(listkey,wordfreq))
    listkey=[]
    savefile.write(" ")
    for k in sorted(dictresult.keys()):
        savefile.write("%d:%s "%(k,dictresult[k]))
    savefile.write("\n")
sys.stdout.flush()
DB.sync()
DB.close()
savefile.close()
