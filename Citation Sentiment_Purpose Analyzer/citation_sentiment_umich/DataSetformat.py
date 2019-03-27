import re

savefile=open('dataset.txt','w')
savefile2=open('purpose-class.txt','w')
savefile3=open('purpose.txt','w')
savefile4=open('class.txt','w')

f=open("citation_sentiment_release/annotated_sentences.txt")
line=f.readlines()
for n, data in enumerate(line):
    text=data.rstrip()
    m=re.match(r'.+\t.+\t\d+\t(.+?)\t\d+\t(.+?)\t\d+\t(.+?)\t\d+\t(.+?)\t\d+\t(\d+)\t(\d+)',text).groups()
    if str(m[5])!='0':
        savefile.write(str(m[0])+'~~~'+str(m[1])+'~~~'+str(m[2])+'~~~'+str(m[3])+'.\n')
        savefile2.write(str(m[5])+' 1:'+str(m[4])+'\n')
        savefile3.write(str(m[4])+'\n')
        savefile4.write(str(m[5])+'\n')
f.close()
