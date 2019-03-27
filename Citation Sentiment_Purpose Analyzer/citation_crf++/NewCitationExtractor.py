import re
import os
import fnmatch

data=""
citingid=[]
savefile=open("NewCitingSentenceCollection.txt",'w')

dataset=open("citation_sentiment_release/annotated_sentences_norepeat.txt")
dataline=dataset.readlines()
for x, data1 in enumerate(dataline):
    text=data1.rstrip()
    m=re.match(r'(.+?)\t.+\t\d+\t.+\t\d+\t.+\t\d+\t.+\t\d+\t.+\t\d+\t\d+\t\d',text).groups()
    citingid.append(m[0]+'.txt')
for dirpath, dirs, files in os.walk('pdfbox-0.72/C'):
    for filename in fnmatch.filter(files, '*.txt'):
        if filename not in citingid:
            with open(os.path.join(dirpath, filename)) as f:
                line=f.readlines()
                for n, str1 in enumerate(line):
                    str1=str1.rstrip()+' '
                    str1=str1.lower()
                    data+=str1
                m=re.findall(r"([^.]*?\([^)]+, [0-9]+\)[^.]*\.)",data)
                for i in m:
                    if i.startswith(' '):
                        savefile.write(i[1:]+'\n')
                    else:
                        savefile.write(i+'\n')
                data=""

f.close()
savefile.close()
dataset.close()
