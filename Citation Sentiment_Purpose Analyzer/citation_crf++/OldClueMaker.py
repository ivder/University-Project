import re
import nltk

temp=""
BItext=[]
f1=open("CRFPositiveClueTaggerIOB.txt")
#f2=open("CRFNegativeClueTaggerIOB.txt")
savefile1=open("OldPositiveClue.txt",'w')
#savefile2=open("OldNegativeClue.txt",'w')
line1=f1.readlines()
#line2=f2.readlines()

for n,data in enumerate(line1):
    data=data.rstrip()
    if data.strip():
        m=re.match(r'(.+?) (.+?) (.+)',data).groups()
        if m[2]!="O":
            if m[2]=="B":
                savefile1.write(m[0]+'\n')
                BItext.append(temp)
                temp=""
                temp+=m[0]
            elif m[2]=="I":
                temp+=' '+m[0]
BItext.append(temp)

for i in BItext:
    if len(i.split()) > 1:
        savefile2.write(i+'\n')
    
f1.close()
#f2.close()
savefile1.close()
#savefile2.close()
