import re
import nltk

temp=""
BItext=[]
#f1=open("outputPositive")
f2=open("outputNegative")
#savefile1=open("NewPositiveClue.txt",'w')
savefile2=open("NewNegativeClue.txt",'w')
#line1=f1.readlines()
line2=f2.readlines()

for n,data in enumerate(line2):
    data=data.rstrip()
    if data.strip():
        m=re.match(r'(.+?)\t(.+?)\t(.+)',data).groups()
        if m[2]!="O":
            if m[2]=="B":
                savefile2.write(m[0]+'\n')
                BItext.append(temp)
                temp=""
                temp+=m[0]
            elif m[2]=="I":
                temp+=' '+m[0]
BItext.append(temp)

for i in BItext:
    if len(i.split()) > 1:
        savefile2.write(i+'\n')
    
#f1.close()
f2.close()
#savefile1.close()
savefile2.close()
