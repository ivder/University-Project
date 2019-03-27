import re

savefile=open('citation_sentiment_release/annotated_sentences_norepeat_no0.txt','w')
#savefile2=open('purpose-class.txt','w')
#savefile3=open('purpose.txt','w')
#savefile4=open('class.txt','w')

f=open("citation_sentiment_release/annotated_sentences_norepeat.txt")
line=f.readlines()
for n, data in enumerate(line):
    text=data.rstrip()
    m=re.match(r'.+\t.+\t\d+\t(.+?)\t\d+\t(.+?)\t\d+\t(.+?)\t\d+\t(.+?)\t\d+\t(\d+)\t(\d+)',text).groups()
    #if str(m[5])!='0' and str(m[5])!='1':
    if str(m[5])!='0':
        savefile.write(text+'\n')
        #savefile.write(str(m[0])+'.\t'+str(m[1])+'.\t'+str(m[2])+'.\t'+str(m[3])+'.\t'+str(m[5])+'\n')
        #savefile2.write(str(m[5])+' 1:'+str(m[4])+'\n')
        #savefile3.write(str(m[4])+'\n')
        #savefile4.write(str(m[5])+'\n')
f.close()
savefile.close()
