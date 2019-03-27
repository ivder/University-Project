import re
import sys
import nltk
from nltk import PorterStemmer
from nltk.corpus import stopwords
from collections import Counter

contrast=['despite','whereas','although','even though','nevertheless','aside from','beside','nonetheless']
contrast+=['but','however','on the other hand','on the contrary','in contrast']
negation=['no','not','never']
comparison=['similarly','likewise','also','like','just as','just like','similar to','same as','compare','compared','not only','comparison']
power=['very','super','extremely','really','importantly','quite','exceedingly','excessively','highly','hugely','immensely','intensely','overly','remarkably','severly','terribly','totally','utterly']
firstp=['i','we','me','us','mine','ours','my','our']
thirdp=['he','she','it','they','him','her','hit','it','them','his','hers','its','theirs','his','her','its','their']

word=[]
refcount=trefcount=totrefcount=0
concount=negcount=comp=0
trefpos=0
pos=neg=refpos=refneg=0
fp=tp=0
section=0

savefile=open('featurepolarity.txt','w')
#stopwordslist=stopwords.words('english')
#stemmer=PorterStemmer()

f=open("dataset.txt") #open file
line=f.readlines() #read all lines from text file
for n, str1 in enumerate(line):
    str1=str1.lower()
    refcount=str1.count("<ref>")
    trefcount=str1.count("<tref>")
    totrefcount=refcount+trefcount

    for y in contrast:
        if y in str1:
            concount=concount+1
    for x in negation:
        if x in str1:
            negcount=negcount+1
    for z in comparison:
        if z in str1:
            comp=comp+1
    for a in firstp:
        if a in str1:
            fp=fp+1
    for b in thirdp:
        if b in str1:
            tp=tp+1
            
    word=nltk.word_tokenize(str1)
    result=nltk.pos_tag(word)
    dic=open("subjectivity_clues_hltemnlp05/subjectivity_clues_hltemnlp05/subjclueslen1-HLTEMNLP05.txt")
    linedic=dic.readlines()
    m=re.match(r'(.+?)~~~(.+?)~~~(.+?)~~~(.+)',str1).groups()
    for i in result:
        if i[1]=='JJ':
            for n, data in enumerate(linedic):
                text=data.rstrip()
                mdic=re.search(r'word1=+(.+?)\s.+priorpolarity=(.+)',text).groups()
                if mdic[0] == i[0]:
                    if mdic[1]=="positive":
                        pos=pos+1
                    elif mdic[1]=="negative":
                        neg=neg+1
    if "tref" in m[0]:
        trefpos=m[0].index("<tref>")
        wordm=nltk.word_tokenize(m[0])
        resultm=nltk.pos_tag(wordm)
        for i in resultm:
            if i[1]=='JJ':
                for n, data in enumerate(linedic):
                    text=data.rstrip()
                    mdic=re.search(r'word1=+(.+?)\s.+priorpolarity=(.+)',text).groups()
                    if mdic[0] in m[0]:
                        if mdic[1]=="positive":
                            refpos=refpos+1
                        elif mdic[1]=="negative":
                            refneg=refneg+1
    elif "tref" in m[1]:
        trefpos=m[1].index("<tref>")
        wordm=nltk.word_tokenize(m[1])
        resultm=nltk.pos_tag(wordm)
        for i in resultm:
            if i[1]=='JJ':
                for n, data in enumerate(linedic):
                    text=data.rstrip()
                    mdic=re.search(r'word1=+(.+?)\s.+priorpolarity=(.+)',text).groups()
                    if mdic[0]==i[0]:
                        if mdic[1]=="positive":
                            refpos=refpos+1
                        elif mdic[1]=="negative":
                            refneg=refneg+1
    elif "tref" in m[2]:
        trefpos=m[2].index("<tref>")
        wordm=nltk.word_tokenize(m[2])
        resultm=nltk.pos_tag(wordm)
        for i in resultm:
            if i[1]=='JJ':
                for n, data in enumerate(linedic):
                    text=data.rstrip()
                    mdic=re.search(r'word1=+(.+?)\s.+priorpolarity=(.+)',text).groups()
                    if mdic[0]==i[0]:
                        if mdic[1]=="positive":
                            refpos=refpos+1
                        elif mdic[1]=="negative":
                            refneg=refneg+1
    elif "tref" in m[3]:
        trefpos=m[3].index("<tref>")
        wordm=nltk.word_tokenize(m[0])
        resultm=nltk.pos_tag(wordm)
        for i in resultm:
            if i[1]=='JJ':
                for n, data in enumerate(linedic):
                    text=data.rstrip()
                    mdic=re.search(r'word1=+(.+?)\s.+priorpolarity=(.+)',text).groups()
                    if mdic[0]==i[0]:
                        if mdic[1]=="positive":
                            refpos=refpos+1
                        elif mdic[1]=="negative":
                            refneg=refneg+1
    
    savefile.write(' 1:'+str(totrefcount)+' 2:'+str(concount)+' 3:'+str(negcount)+' 4:'+str(comp)+' 5:'+str(fp)+' 6:'+str(tp)+' 7:'+str(trefpos)+' 8:'+str(refpos)+' 9:'+str(refneg)+' 10:'+str(pos)+' 11:'+str(neg)+'\n')
    
    concount=negcount=comp=pos=neg=refpos=refneg=fp=tp=0         

sys.stdout.flush()
savefile.close()
f.close()
dic.close()
