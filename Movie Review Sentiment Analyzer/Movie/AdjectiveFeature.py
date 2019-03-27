import re
import glob
import os
import nltk

contrast=['despite','whereas','although','even though','nevertheless','aside from','beside','nonetheless']
contrast2=['but','however','on the other hand','on the contrary','in contrast']
negation=['no','not','never']
power=['very','super','extremely','really','importantly','quite','exceedingly','excessively','highly','hugely','immensely','intensely','overly','remarkably','severly','terribly','totally','utterly']
positive=0
negative=0
concount=0
word=[]
value1="-1"
value2="+1"

savefile=open('movieadj.data','w')

all_files=os.listdir("review_polarity/txt_sentoken/neg/") #get all text file from directory
for x in all_files: #iterate through all files
    fneg=open("review_polarity/txt_sentoken/neg/"+x) #open file
    line=fneg.readlines() #read all lines from text file
    for n, sentence in enumerate(line):
        for y in contrast:
            if y in sentence:
                concount=concount+1
                if sentence.startswith(y) :
                    z=re.search(r'[,]?(.+)',sentence).groups()
                else:
                    z=re.search(r'(.+?)'+y+'.',sentence).groups()
                sentence = z[0]
        for y2 in contrast2:
            if y2 in sentence:
                concount=concount+1
                if sentence.startswith(y2):
                    z2=re.search(r''+y2+'(.+?)',sentence).groups()
                else:
                    z2=re.search(r'.'+y2+'(.+?)',sentence).groups()
                sentence=z2[0]
                
        sentence=nltk.word_tokenize(sentence)
        result=nltk.pos_tag(sentence)
        for i in result:
            if i[1]=='JJ':
                iprev=result[result.index(i)-1]
                if iprev[1] == 'JJ':
                    iprev=iprev
                elif iprev[1] is not 'JJ':
                    iprev=result[result.index(i)-2]
                if iprev[0] not in negation:
                    fneg2=open("subjectivity_clues_hltemnlp05/subjectivity_clues_hltemnlp05/subjclueslen1-HLTEMNLP05.txt") #open dictionary
                    line2=fneg2.readlines()
                    for n, data in enumerate(line2): 
                        text=data.rstrip() #make it 1 line
                        m=re.search(r'word1=+(.+?)\s.+priorpolarity=(.+)',text).groups() #search word and polarity, insert to group
                        if m[0] == i[0]:
                            if iprev[0] not in power:
                                if m[1]=="positive": #if positive
                                    positive=positive+1 #counter++
                                elif m[1]=="negative":
                                    negative=negative+1
                            elif iprev[0] in power:
                                if m[1]=="positive": #if positive
                                    positive=positive+2 #counter++
                                elif m[1]=="negative":
                                    negative=negative+2

    savefile.write(value1+' 1:'+str(positive)+' 2:'+str(negative)+' 3:'+str(concount)+'\n') #print result
    positive=negative=concount=0 #reset counter value

all_files2=os.listdir("review_polarity/txt_sentoken/pos/") #get all text file from directory
for x2 in all_files2: #iterate through all files
    fpos=open("review_polarity/txt_sentoken/pos/"+x2) #open file
    line=fpos.readlines() #read all lines from text file
    for n, sentence in enumerate(line):
        for y in contrast:
            if y in sentence:
                concount=concount+1
                if sentence.startswith(y) :
                    z=re.search(r'[,]?(.+)',sentence).groups()
                else:
                    z=re.search(r'(.+?)'+y+'.',sentence).groups()
                sentence = z[0]
        for y2 in contrast2:
            if y2 in sentence:
                concount=concount+1
                if sentence.startswith(y2):
                    z2=re.search(r''+y2+'(.+?)',sentence).groups()
                else:
                    z2=re.search(r'.'+y2+'(.+?)',sentence).groups()
                sentence=z2[0]
                
        sentence=nltk.word_tokenize(sentence)
        result=nltk.pos_tag(sentence)
        for i in result:
            if i[1]=='JJ':
                iprev=result[result.index(i)-1]
                if iprev[0] not in negation:
                    fpos2=open("subjectivity_clues_hltemnlp05/subjectivity_clues_hltemnlp05/subjclueslen1-HLTEMNLP05.txt") #open dictionary
                    line2=fpos2.readlines()
                    for n, data in enumerate(line2): 
                        text=data.rstrip() #make it 1 line
                        m=re.search(r'word1=+(.+?)\s.+priorpolarity=(.+)',text).groups() #search word and polarity, insert to group
                        if m[0] == i[0]:
                            if iprev[0] not in power:
                                if m[1]=="positive": #if positive
                                    positive=positive+1 #counter++
                                elif m[1]=="negative":
                                    negative=negative+1
                            elif iprev[0] in power:
                                if m[1]=="positive": #if positive
                                    positive=positive+2 #counter++
                                elif m[1]=="negative":
                                    negative=negative+2

    savefile.write(value2+' 1:'+str(positive)+' 2:'+str(negative)+' 3:'+str(concount)+'\n') #print result
    positive=negative=concount=0 #reset counter value
    

fneg.close()
fpos.close()
fneg2.close()
fpos2.close()
savefile.close()
    
    



