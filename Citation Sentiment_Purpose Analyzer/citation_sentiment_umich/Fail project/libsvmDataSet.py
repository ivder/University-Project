import re

savefile=open('CitationExtraction.data','w')

f=open("citation_sentiment_release/annotated_sentences.txt")
line=f.readlines()
for n, data in enumerate(line):
    text=data.rstrip()
    m=re.match(r'.+\t.+\t\d+\t.+\t(\d+)\t.+\t(\d+)\t.+\t(\d+)\t.+\t(\d+)\t(\d+)\t(\d+)',text).groups()
    savefile.write(str(m[5])+' 1:'+str(m[0]+' 2:'+str(m[1])+' 3:'+str(m[2])+' 4:'+str(m[3])+' 5:'+str(m[4])+'\n'))
f.close()
