import re
import nltk
from sklearn.model_selection import cross_val_predict
from sklearn.neighbors import KNeighborsClassifier
from sklearn.metrics import classification_report
from sklearn import svm
import  re,collections,numpy,pickle,random
from sklearn.naive_bayes import GaussianNB
from sklearn.linear_model import LogisticRegression
from sklearn.utils import shuffle
from nltk.stem.porter import *

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
    D=[v.split('\t') for v in open('citation_sentiment_release/annotated_sentences_norepeat_no0.txt').read().splitlines() if not re.search('0$',v)]
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

def MPQADataset():
    D=[v.split('\t') for v in open('citation_sentiment_release/annotated_sentences_norepeat_no0.txt').read().splitlines() if not re.search('0$',v)]
    #random.shuffle(D)
    X,y=[' '.join([v[3],v[5],v[7],v[9]]) for v in D],[int(v[12]) for v in D]
    X=[re.sub('</?[^>]+>','',v) for v in X] # remove <REF>,<TREF>, ...
    X=[' '.join(termL(v)) for v in X]
    dic=open("subjectivity_clues_hltemnlp05/subjectivity_clues_hltemnlp05/subjclueslen1-HLTEMNLP05.txt")
    linedic=dic.readlines()
    for n, data in enumerate(linedic):
        text=data.rstrip()
        mdic=re.search(r'word1=+(.+?)\s.+priorpolarity=(.+)',text).groups()
        wdic.append(mdic[0])
    w2i={w:i for i,w in enumerate(collections.Counter(wdic))}
    X2=numpy.zeros((len(X),len(w2i)),dtype=numpy.int)
    for i in range(len(X)):
        for s in X[i].split():
            for w in w2i:
                if w == s:
                    #X2[i,w2i[w]]+=1 # TF
                    X2[i,w2i[w]]=1 # binary
        # end for
    # end for
    return (X2,y)
# end def

def CRFDataset():
    D=[v.split('\t') for v in open('citation_sentiment_release/annotated_sentences_norepeat_no0.txt').read().splitlines() if not re.search('0$',v)]
    #random.shuffle(D)
    X,y=[' '.join([v[3],v[5],v[7],v[9]]) for v in D],[int(v[12]) for v in D]
    X=[re.sub('</?[^>]+>','',v) for v in X] # remove <REF>,<TREF>, ...
    X=[' '.join(termL(v)) for v in X]
    f=open("CitationSentimentDictionary.txt")
    line=f.readlines()
    for n, data in enumerate(line):
        m=re.match(r'(.+?)\t=>\t(.+)',data).groups()
        wdic.append(m[0])
    w2i={w:i for i,w in enumerate(collections.Counter(wdic))}
    X2=numpy.zeros((len(X),len(w2i)),dtype=numpy.int)
    for i in range(len(X)):
        for s in X[i].split():
            for w in w2i:
                if w == s:
                    #X2[i,w2i[w]]+=1 # TF
                    X2[i,w2i[w]]=1 # binary
        # end for
    # end for
    return (X2,y)
# end def

gnb=GaussianNB()
y=[]
wdic=[]
stemmer = PorterStemmer()
f1=open("citation_sentiment_release/annotated_sentences_norepeat_no0.txt")
f2=open("CitationSentimentDictionary.txt")
f3=open("subjectivity_clues_hltemnlp05/subjectivity_clues_hltemnlp05/subjclueslen1-HLTEMNLP05.txt")
line1=f1.readlines()
line2=f2.readlines()
line3=f3.readlines()
poscounter1=negcounter1=poscounter2=negcounter2=result=0
X1=numpy.zeros((len(line1),2),dtype=numpy.int)
X2=numpy.zeros((len(line1),2),dtype=numpy.int)

for n, str1 in enumerate(line1):
    str1=str1.rstrip()
    m=re.match(r'.+\t.+\t\d+\t(.+?)\t\d+\t(.+?)\t\d+\t(.+?)\t\d+\t(.+?)\t\d+\t(\d+)\t(\d+)',str1).groups()
    if m[5] =="1":
        y.extend("1")
    elif m[5] =="2":
        y.extend("2")
    elif m[5] =="3":
        y.extend("3")
    text=m[0]+'\t'+m[1]+'\t'+m[2]+'\t'+m[3]

    for n2, str2 in enumerate(line2):
        m=re.match(r'(.+?)\t=>\t(.+)',str2).groups()
        #if m[0] in text or stemmer.stem(m[0].decode('utf-8')) in text:
        if len(m[0])>1:
            if m[1]=="POS":
                poscounter1+=text.count(m[0])
                poscounter1+=text.count(stemmer.stem(m[0].decode('utf-8')))
            elif m[1]=="NEG":
                negcounter1+=text.count(m[0])
                negcounter1+=text.count(stemmer.stem(m[0].decode('utf-8')))
    X1[n,0]=poscounter1
    X1[n,1]=negcounter1

    for n3, str3 in enumerate(line3):
        m=re.search(r'word1=+(.+?)\s.+priorpolarity=(.+)',str3).groups()
        #if m[0] in text or stemmer.stem(m[0].decode('utf-8')) in text:
        if len(m[0])>1:
            if m[1]=="positive":
                poscounter2+=text.count(m[0])
                poscounter2+=text.count(stemmer.stem(m[0].decode('utf-8')))
            elif m[1]=="negative":
                negcounter2+=text.count(m[0])
                negcounter2+=text.count(stemmer.stem(m[0].decode('utf-8')))
    X2[n,0]=poscounter2
    X2[n,1]=negcounter2
    
    poscounter1=negcounter1=poscounter2=negcounter2=0

y=str(''.join(y))
y=int(y)
y=map(int, str(y))

Xword,yword=genDataset()
Xmpqa, ympqa=MPQADataset()
Xcrf, ycrf=CRFDataset()
print "단어 Feature Shape:",Xword.shape
print "CRF++ 활용한 단어 Feature Shape:",Xcrf.shape
print "MPQA 활용한 단어 Feature Shape:",Xmpqa.shape
print "CRF++ 감성 사전의 Positive&Negative 단어 수  Shape:",X1.shape
print "MPQA 감성 사전의 Positive&Negative 단어 수  Shape:",X2.shape

Xcombine=numpy.column_stack((X1,X2,Xword,Xmpqa,Xcrf))
print "결합된 Feature Shape:",Xcombine.shape

#y_pred=cross_val_predict(LogisticRegression(),Xcombine,y,cv=10)
#y_pred=cross_val_predict(svm.SVC(kernel='linear',C=1, class_weight={1: 10}),Xword,yword,cv=10)
#y_pred=cross_val_predict(svm.LinearSVC(),Xcombine,y,cv=10)
#y_pred=cross_val_predict(svm.SVC(decision_function_shape='ovo'),Xcombine,y,cv=10)
y_pred = cross_val_predict(gnb, X2, y, cv=10)
#y_pred=cross_val_predict(KNeighborsClassifier(n_neighbors=1),X,y,cv=10)
print classification_report(y, y_pred)
   
f1.close()
f2.close()
f3.close()
