import nltk
import re

result=""
temp=""
cluesent=""
f=open("datasetnorepeat.txt")
posfile=open('PosClue.txt','w')
negfile=open('NegClue.txt','w')
tagfile=open('PosNegClueTag.txt','w')
line=f.readlines()
for n, str1 in enumerate(line):
    str1=str1.lower()
    word=nltk.word_tokenize(str1)
    dic=open("subjectivity_clues_hltemnlp05/subjectivity_clues_hltemnlp05/subjclueslen1-HLTEMNLP05.txt")
    linedic=dic.readlines()
    m=re.match(r'(.+?)\t(.+?)\t(.+?)\t(.+)\t(\d)',str1).groups()
    wordm0=nltk.word_tokenize(m[0])
    wordm1=nltk.word_tokenize(m[1])
    wordm2=nltk.word_tokenize(m[2])
    wordm3=nltk.word_tokenize(m[3])
    m=list(m)
    for i in wordm0:
        for n, data in enumerate(linedic):
            text=data.rstrip()
            mdic=re.search(r'word1=+(.+?)\s.+priorpolarity=(.+)',text).groups()
            if mdic[0] == i:
                if mdic[0] == temp:
                    temp=""
                elif mdic[0] != temp:
                    temp=mdic[0]
                    if i in wordm0:
                        if m[0].startswith(i):
                            mtag=re.search(r''+i+'(.+)',m[0]).groups()
                            if mdic[1]=="positive" and m[4]=='2':
                                if "<pos>" not in m[0]:
                                    cluesent+=m[0]+'\t'
                                m[0]="<pos>"+i+"</pos>"+mtag[0]
                            elif mdic[1]=="negative" and m[4]=='3':
                                if "<neg>" not in m[0]:
                                    cluesent+=m[0]+'\t'
                                m[0]="<neg>"+i+"</neg>"+mtag[0]
                        elif m[0].endswith(i):
                            mtag=re.search(r'(.+?)'+i+'.',m[0]).groups()
                            if mdic[1]=="positive" and m[4]=='2':
                                if "<pos>" not in m[0]:
                                    cluesent+=m[0]+'\t'
                                m[0]=mtag[0]+"<pos>"+i+"</pos>"
                            elif mdic[1]=="negative" and m[4]=='3':
                                if "<neg>" not in m[0]:
                                    cluesent+=m[0]+'\t'
                                m[0]=mtag[0]+"<neg>"+i+"</neg>"
                        else:
                            mtag=re.search(r'(.+?)'+i+'(.+)',m[0]).groups()
                            if mdic[1]=="positive" and m[4]=='2':
                                if "<pos>" not in m[0]:
                                    cluesent+=m[0]+'\t'
                                m[0]=mtag[0]+"<pos>"+i+"</pos>"+mtag[1]
                            elif mdic[1]=="negative" and m[4]=='3':
                                if "<neg>" not in m[0]:
                                    cluesent+=m[0]+'\t'
                                m[0]=mtag[0]+"<neg>"+i+"</neg>"+mtag[1]
    for i in wordm1:
        for n, data in enumerate(linedic):
            text=data.rstrip()
            mdic=re.search(r'word1=+(.+?)\s.+priorpolarity=(.+)',text).groups()
            if mdic[0] == i:
                if mdic[0] == temp:
                    temp=""
                elif mdic[0] != temp:
                    temp=mdic[0]             
                    if i in wordm1:
                        if m[1].startswith(i):
                            mtag=re.search(r''+i+'(.+)',m[1]).groups()
                            if mdic[1]=="positive" and m[4]=='2':
                                if "<pos>" not in m[1]:
                                    cluesent+=m[1]+'\t'
                                m[1]="<pos>"+i+"</pos>"+mtag[0]
                            elif mdic[1]=="negative" and m[4]=='3':
                                if "<neg>" not in m[1]:
                                    cluesent+=m[1]+'\t'
                                m[1]="<neg>"+i+"</neg>"+mtag[0]
                        elif m[1].endswith(i):
                            mtag=re.search(r'(.+?)'+i+'.',m[1]).groups()
                            if mdic[1]=="positive" and m[4]=='2':
                                if "<pos>" not in m[1]:
                                    cluesent+=m[1]+'\t'
                                m[1]=mtag[0]+"<pos>"+i+"</pos>"
                            elif mdic[1]=="negative" and m[4]=='3':
                                if "<neg>" not in m[1]:
                                    cluesent+=m[1]+'\t'
                                m[1]=mtag[0]+"<neg>"+i+"</neg>"
                        else:
                            mtag=re.search(r'(.+?)'+i+'(.+)',m[1]).groups()
                            if mdic[1]=="positive" and m[4]=='2':
                                if "<pos>" not in m[1]:
                                    cluesent+=m[1]+'\t'
                                m[1]=mtag[0]+"<pos>"+i+"</pos>"+mtag[1]
                            elif mdic[1]=="negative" and m[4]=='3':
                                if "<neg>" not in m[1]:
                                    cluesent+=m[1]+'\t'
                                m[1]=mtag[0]+"<neg>"+i+"</neg>"+mtag[1] 
    for i in wordm2:
        for n, data in enumerate(linedic):
            text=data.rstrip()
            mdic=re.search(r'word1=+(.+?)\s.+priorpolarity=(.+)',text).groups()
            if mdic[0] == i:
                if mdic[0] == temp:
                    temp=""
                elif mdic[0] != temp:
                    temp=mdic[0]
                    if i in wordm2:
                        if m[2].startswith(i):
                            mtag=re.search(r''+i+'(.+)',m[2]).groups()
                            if mdic[1]=="positive" and m[4]=='2':
                                if "<pos>" not in m[2]:
                                    cluesent+=m[2]+'\t'
                                m[2]="<pos>"+i+"</pos>"+mtag[0]
                            elif mdic[1]=="negative" and m[4]=='3':
                                if "<neg>" not in m[2]:
                                    cluesent+=m[2]+'\t'
                                m[2]="<neg>"+i+"</neg>"+mtag[0]
                        elif m[2].endswith(i):
                            mtag=re.search(r'(.+?)'+i+'.',m[2]).groups()
                            if mdic[1]=="positive" and m[4]=='2':
                                if "<pos>" not in m[2]:
                                    cluesent+=m[2]+'\t'
                                m[2]=mtag[0]+"<pos>"+i+"</pos>"
                            elif mdic[1]=="negative" and m[4]=='3':
                                if "<neg>" not in m[2]:
                                    cluesent+=m[2]+'\t'
                                m[2]=mtag[0]+"<neg>"+i+"</neg>"
                        else:
                            mtag=re.search(r'(.+?)'+i+'(.+)',m[2]).groups()
                            if mdic[1]=="positive" and m[4]=='2':
                                if "<pos>" not in m[2]:
                                    cluesent+=m[2]+'\t'
                                m[2]=mtag[0]+"<pos>"+i+"</pos>"+mtag[1]
                            elif mdic[1]=="negative" and m[4]=='3':
                                if "<neg>" not in m[2]:
                                    cluesent+=m[2]+'\t'
                                m[2]=mtag[0]+"<neg>"+i+"</neg>"+mtag[1]
    for i in wordm3:
        for n, data in enumerate(linedic):
            text=data.rstrip()
            mdic=re.search(r'word1=+(.+?)\s.+priorpolarity=(.+)',text).groups()
            if mdic[0] == i:
                if mdic[0] == temp:
                    temp=""
                elif mdic[0] != temp:
                    temp=mdic[0]
                    if i in wordm3:
                        if m[3].startswith(i):
                            mtag=re.search(r''+i+'(.+)',m[3]).groups()
                            if mdic[1]=="positive" and m[4]=='2':
                                if "<pos>" not in m[3]:
                                    cluesent+=m[3]+'\t'
                                m[3]="<pos>"+i+"</pos>"+mtag[0]
                            elif mdic[1]=="negative" and m[4]=='3':
                                if "<neg>" not in m[3]:
                                    cluesent+=m[3]+'\t'
                                m[3]="<neg>"+i+"</neg>"+mtag[0]
                        elif m[3].endswith(i):
                            mtag=re.search(r'(.+?)'+i+'.',m[3]).groups()
                            if mdic[1]=="positive" and m[4]=='2':
                                if "<pos>" not in m[3]:
                                    cluesent+=m[3]+'\t'
                                m[3]=mtag[0]+"<pos>"+i+"</pos>"
                            elif mdic[1]=="negative" and m[4]=='3':
                                if "<neg>" not in m[3]:
                                    cluesent+=m[3]+'\t'
                                m[3]=mtag[0]+"<neg>"+i+"</neg>"
                        else:
                            mtag=re.search(r'(.+?)'+i+'(.+)',m[3]).groups()
                            if mdic[1]=="positive" and m[4]=='2':
                                if "<pos>" not in m[3]:
                                    cluesent+=m[3]+'\t'
                                m[3]=mtag[0]+"<pos>"+i+"</pos>"+mtag[1]
                            elif mdic[1]=="negative" and m[4]=='3':
                                if "<neg>" not in m[3]:
                                    cluesent+=m[3]+'\t'
                                m[3]=mtag[0]+"<neg>"+i+"</neg>"+mtag[1]
                
    tagfile.write(m[0]+'\t'+m[1]+'\t'+m[2]+'\t'+m[3]+'\n')
    #print m[0]+'\t'+m[1]+'\t'+m[2]+'\t'+m[3]+'\n'
    #print cluesent+'\n'
    if m[4]=="2":
        posfile.write(cluesent+'\n')
    elif m[4]=="3":
        negfile.write(cluesent+'\n')
        
    cluesent=""
posfile.close()
negfile.close()
tagfile.close()
f.close()
dic.close()

