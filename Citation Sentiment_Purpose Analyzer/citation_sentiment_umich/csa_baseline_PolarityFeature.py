import re
import sys
import nltk
from nltk import PorterStemmer
from nltk.corpus import stopwords
from sklearn.model_selection import cross_val_predict
from sklearn.neighbors import KNeighborsClassifier
from sklearn.metrics import classification_report
from sklearn import svm
import  re,collections,numpy,pickle,random

contrast=['despite','whereas','although','even though','nevertheless','aside from','beside','nonetheless']
contrast+=['but','however','on the other hand','on the contrary','in contrast']
negation=['no','not','never']
comparison=['similarly','likewise','also','like','just as','just like','similar to','same as','compare','compared','not only','comparison']
power=['very','super','extremely','really','importantly','quite','exceedingly','excessively','highly','hugely','immensely','intensely','overly','remarkably','severly','terribly','totally','utterly']
firstp=['i','we','me','us','mine','ours','my','our']
thirdp=['he','she','it','they','him','her','hit','it','them','his','hers','its','theirs','his','her','its','their']

word=[]
refcount=trefcount=totrefcount=0
concount=negcount=comp=powercount=0
trefpos=0
adj=verb=adv=0
pos=neg=refpos=refneg=0
fp=tp=0
section=0
y=[]

#savefile=open('featurepolarity.txt','w')
#stopwordslist=stopwords.words('english')
#stemmer=PorterStemmer()

def ngram(L,ngramN):
    L2,size=[],len(L)
    for i in range(size):
        for n in ngramN:
            if i+n<=size: L2.append('_'.join(L[i:i+n]))
        # end for
    # end for
    return L2
# end def

def termL(v):
    L=re.findall('[a-z0-9]{2,}',v.lower())
    #L=ngram(L,[1,2])
    L=ngram(L,[1])
    return L
# end def

def genDataset():
    #D=[v.split('\t') for v in open('citation_sentiment_release/annotated_sentences.txt').read().splitlines()]
    D=[v.split('\t') for v in open('citation_sentiment_release/annotated_sentences.txt').read().splitlines() if not re.search('0$',v)]
    #random.shuffle(D)
    X,y=[' '.join([v[3],v[5],v[7],v[9]]) for v in D],[int(v[12]) for v in D]

    
    X=[re.sub('</?[^>]+>','',v) for v in X] # remove <REF>,<TREF>, ...
    X=[' '.join(termL(v)) for v in X]
    w2i={w:i for i,w in enumerate(collections.Counter(' '.join(X).split()))}   
    X2=numpy.zeros((len(X),len(w2i)),dtype=numpy.int)
    for i in range(len(X)):
        for w in X[i].split():
            #X2[i,w2i[w]]+=1 # TF
            X2[i,w2i[w]]=1 # binary
        # end for
    # end for
    return (X2,y)
# end def


f=open("dataset.txt") #open file
f2=open("class.txt")
line=f.readlines() #read all lines from text file
X=numpy.zeros((len(line),11),dtype=numpy.int)

liney=f2.readlines()
for ny, stry in enumerate(liney):
    y.extend(stry.rstrip())
y=str(''.join(y))
y=int(y)
y=map(int, str(y))

for n1, str1 in enumerate(line):
    str1=str1.lower()
    refcount=str1.count("<ref>")
    trefcount=str1.count("<tref>")
    totrefcount=refcount+trefcount

    for c in contrast:
        if c in str1:
            concount+=1
    for x in negation:
        if x in str1:
            negcount+=1
    for z in comparison:
        if z in str1:
            comp+=1
    for e in power:
        if e in str1:
            powercount+=1
    for a in firstp:
        if a in str1:
            fp=1
    for b in thirdp:
        if b in str1:
            tp=1
            
    word=nltk.word_tokenize(str1)
    result=nltk.pos_tag(word)
    dic=open("subjectivity_clues_hltemnlp05/subjectivity_clues_hltemnlp05/subjclueslen1-HLTEMNLP05.txt")
    linedic=dic.readlines()
    m=re.match(r'(.+?)~~~(.+?)~~~(.+?)~~~(.+)',str1).groups()
    for i in result:
        if i[1]=='JJ':
            #adj=adj+1
            for n, data in enumerate(linedic):
                text=data.rstrip()
                mdic=re.search(r'word1=+(.+?)\s.+priorpolarity=(.+)',text).groups()
                if mdic[0] == i[0]:
                    if mdic[1]=="positive":
                        pos=pos+1
                    elif mdic[1]=="negative":
                        neg=neg+1
        '''elif i[1]=='VB':
            verb=verb+1
        elif i[1]=='RB':
            adv=adv+1'''
            
    if "tref" in m[0]:
        trefpos=m[0].index("<tref>")
        wordm=nltk.word_tokenize(m[0])
        resultm=nltk.pos_tag(wordm)
        for i in resultm:
            if i[1]=='JJ':
                for n, data in enumerate(linedic):
                    text=data.rstrip()
                    mdic=re.search(r'word1=+(.+?)\s.+priorpolarity=(.+)',text).groups()
                    if mdic[0] in m[0]:
                        if mdic[1]=="positive":
                            refpos=refpos+1
                        elif mdic[1]=="negative":
                            refneg=refneg+1
    elif "tref" in m[1]:
        trefpos=m[1].index("<tref>")
        wordm=nltk.word_tokenize(m[1])
        resultm=nltk.pos_tag(wordm)
        for i in resultm:
            if i[1]=='JJ':
                for n, data in enumerate(linedic):
                    text=data.rstrip()
                    mdic=re.search(r'word1=+(.+?)\s.+priorpolarity=(.+)',text).groups()
                    if mdic[0]==i[0]:
                        if mdic[1]=="positive":
                            refpos=refpos+1
                        elif mdic[1]=="negative":
                            refneg=refneg+1
    elif "tref" in m[2]:
        trefpos=m[2].index("<tref>")
        wordm=nltk.word_tokenize(m[2])
        resultm=nltk.pos_tag(wordm)
        for i in resultm:
            if i[1]=='JJ':
                for n, data in enumerate(linedic):
                    text=data.rstrip()
                    mdic=re.search(r'word1=+(.+?)\s.+priorpolarity=(.+)',text).groups()
                    if mdic[0]==i[0]:
                        if mdic[1]=="positive":
                            refpos=refpos+1
                        elif mdic[1]=="negative":
                            refneg=refneg+1
    elif "tref" in m[3]:
        trefpos=m[3].index("<tref>")
        wordm=nltk.word_tokenize(m[0])
        resultm=nltk.pos_tag(wordm)
        for i in resultm:
            if i[1]=='JJ':
                for n, data in enumerate(linedic):
                    text=data.rstrip()
                    mdic=re.search(r'word1=+(.+?)\s.+priorpolarity=(.+)',text).groups()
                    if mdic[0]==i[0]:
                        if mdic[1]=="positive":
                            refpos=refpos+1
                        elif mdic[1]=="negative":
                            refneg=refneg+1
    
    #print('1:'+str(totrefcount)+' 2:'+str(concount)+' 3:'+str(negcount)+' 4:'+str(comp)+' 5:'+str(fp)+' 6:'+str(tp)+' 7:'+str(trefpos)+' 8:'+str(refpos)+' 9:'+str(refneg)+' 10:'+str(pos)+' 11:'+str(neg)+'\n')
    X[n1,0]=totrefcount
    X[n1,1]=concount
    X[n1,2]=negcount
    X[n1,3]=comp
    X[n1,4]=powercount
    X[n1,5]=fp
    X[n1,6]=tp
    #X[n1,6]=adj
    #X[n1,7]=verb
    #X[n1,8]=adv
    #X[n1,7]=trefpos
    X[n1,7]=refpos
    X[n1,8]=refneg
    X[n1,9]=pos
    X[n1,10]=neg
    concount=negcount=comp=powercount=pos=neg=refpos=refneg=fp=tp=adj=verb=adv=0
    
print "Feature Shape:",X.shape
Xword,yword=genDataset()
print "Word Frequency Feature Shape:",Xword.shape

Xcombine=numpy.column_stack((X,Xword))
print "Combined Feature Shape:",Xcombine.shape

#y_pred=cross_val_predict(svm.SVC(kernel='linear',C=1),X,y,cv=10)
y_pred=cross_val_predict(KNeighborsClassifier(n_neighbors=1),Xcombine,y,cv=10)
print classification_report(y, y_pred)

sys.stdout.flush()
#savefile.close()
f.close()
f2.close()
dic.close()
