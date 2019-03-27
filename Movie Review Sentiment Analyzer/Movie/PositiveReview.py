import re
import glob
import os

positive=0
negative=0
firstpos=0
firstneg=0
finalpos=0
finalneg=0
word=[]
result="pos"

all_files=os.listdir("review_polarity/txt_sentoken/pos/")
for x in all_files:
    f=open("review_polarity/txt_sentoken/pos/"+x)
    line=f.readlines()
    str=''.join(line)
    word=str.split(" ")
    f.seek(0) #reset file pointer
    firstline=f.readline()
    firststr=''.join(firstline)
    firstword=firststr.split(" ")
    finalline=line[len(line)-1] #final line
    finalstr=''.join(finalline)
    finalword=finalstr.split(" ")

    f=open("subjectivity_clues_hltemnlp05/subjectivity_clues_hltemnlp05/subjclueslen1-HLTEMNLP05.txt")
    line=f.readlines()
    for n, data in enumerate(line):
        text=data.rstrip()
        m=re.search(r'word1=+(.+?)\s.+priorpolarity=(.+)',text).groups()
        if m[0] in word:
            if m[1]=="positive":
                positive=positive+1
            elif m[1]=="negative":
                negative=negative+1
        if m[0] in firstword:
            if m[1]=="positive":
                firstpos=firstpos+1
            elif m[1]=="negative":
                firstneg=firstneg+1
        if m[0] in finalword:
            if m[1]=="positive":
                finalpos=finalpos+1
            elif m[1]=="negative":
                finalneg=finalneg+1

    print positive,",",negative,",",firstpos,",",firstneg,",",finalpos,",",finalneg,",",result
    positive=negative=firstpos=firstneg=finalpos=finalneg=0

f.close()
    
    



