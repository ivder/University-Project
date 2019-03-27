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
            print L2
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

D=[v.split('\t') for v in open('citation_sentiment_release/test.txt').read().splitlines() if not re.search('0$',v)]
X,y=[' '.join([v[3],v[5],v[7],v[9]]) for v in D],[int(v[12]) for v in D]
X=[re.sub('</?[^>]+>','',v) for v in X] # remove <REF>,<TREF>, ...
X=[' '.join(termL(v)) for v in X]

