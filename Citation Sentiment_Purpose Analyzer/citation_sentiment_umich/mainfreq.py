import re
import sys
import bsddb
from nltk import PorterStemmer
from nltk.corpus import stopwords

word=[]
listkey=[]
identifier=1
wordfreq=[]

savefile=open('libsvmformat.txt','w')
DB=bsddb.hashopen('WordDic2.db','c')
stopwordslist=stopwords.words('english')
stemmer=PorterStemmer()
#normalizing textual data

f=open("dataset.txt") #open file
line=f.readlines() #read all lines from text file
for n, str in enumerate(line):
    str=str.lower()
    word=re.compile(r'\W+', re.UNICODE).split(str) #choose alphanumeric only
    for y, data in enumerate(word):
        #word[y]=stemmer.stem(word[y])
        if data not in stopwordslist and data in DB:
            wordfreq.append(word.count(data)) #count frequency
            #dictfreq=dict(zip(word,wordfreq)) #use dict to combine the repeated word
            listkey.append(DB[data])
            listkey=map(int,listkey)
            dictresult=dict(zip(listkey,wordfreq))
    savefile.write(" ")
    for k in sorted(dictresult.keys()):
        savefile.write("%d:%s "%(k,dictresult[k]))
    savefile.write("\n")
    listkey=[]

sys.stdout.flush()
DB.sync()
DB.close()
savefile.close()
