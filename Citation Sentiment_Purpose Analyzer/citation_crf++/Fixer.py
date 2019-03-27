import re

f=open("PosClue.txt")
f2=open("NegClue.txt")
savefile=open("PosCluefix.txt",'w')
savefile2=open("NegCluefix.txt",'w')
line=f.readlines()
for n, str1 in enumerate(line):
    m=re.sub('\t','\n',str1)
    savefile.write(m)
line2=f2.readlines()
for n2, str2 in enumerate(line2):
    m2=re.sub('\t','\n',str2)
    savefile2.write(m2)
    
f.close()
f2.close()
savefile.close()
savefile2.close()
