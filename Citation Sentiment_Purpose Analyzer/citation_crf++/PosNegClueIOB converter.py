import re

f1=open("CRFPositiveClueTagger.txt")
f2=open("CRFNegativeClueTagger.txt")
savefile1=open("CRFPositiveClueTaggerIOB.txt",'w')
savefile2=open("CRFNegativeClueTaggerIOB.txt",'w')
line1=f1.readlines()
line2=f2.readlines()
dic=open("subjectivity_clues_hltemnlp05/subjectivity_clues_hltemnlp05/subjclueslen1-HLTEMNLP05.txt")
linedic=dic.readlines()
mark=0
clue=""

for n1, data1 in enumerate(line1):
    if not data1.isspace():
        data1=data1.rstrip()
        m1=re.match(r'(.+?) .',data1).groups()
        for n, data in enumerate(linedic):
            text=data.rstrip()
            mdic=re.search(r'word1=+(.+?)\s.+priorpolarity=(.+)',text).groups()
            if mdic[0] == m1[0] and mdic[1] =="positive":
                mark=1
        if mark==1:
            if clue.endswith("B") or clue.endswith("I"):
                clue=data1+' '+'I'
            else:
                clue=data1+' '+'B'
        elif mark==0:
            clue=data1+' '+'O'
        savefile1.write(clue+'\n')
        if clue==". . O": savefile1.write('\n')
    mark=0

for n2, data2 in enumerate(line2):
    if not data2.isspace():
        data2=data2.rstrip()
        m2=re.match(r'(.+?) .',data2).groups()
        for n2, data in enumerate(linedic):
            text=data.rstrip()
            mdic=re.search(r'word1=+(.+?)\s.+priorpolarity=(.+)',text).groups()
            if mdic[0] == m2[0] and mdic[1] =="negative":
                mark=1
        if mark==1:
            if clue.endswith("B") or clue.endswith("I"):
                clue=data2+' '+'I'
            else:
                clue=data2+' '+'B'
        elif mark==0:
            clue=data2+' '+'O'
        savefile2.write(clue+'\n')
        if clue==". . O": savefile2.write('\n')
    mark=0
               

f1.close()
f2.close()
savefile1.close()
savefile2.close()
dic.close()

    
