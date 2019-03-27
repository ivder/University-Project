from sklearn.model_selection import cross_val_predict
from sklearn.neighbors import KNeighborsClassifier
from sklearn.metrics import classification_report
from sklearn import svm
import  re,collections,numpy,pickle,random

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
    random.shuffle(D)
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

X,y=genDataset()
print X.shape

#y_pred=cross_val_predict(svm.SVC(kernel='linear',C=1),X,y,cv=10)
y_pred=cross_val_predict(KNeighborsClassifier(n_neighbors=1),X,y,cv=10)
print classification_report(y, y_pred)

