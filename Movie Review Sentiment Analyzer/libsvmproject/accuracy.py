import re
from decimal import Decimal
'''
TP=file1=+1 && file2=+1
FP=file1=-1 && file2=+1
TN=file1=-1 && file2=-1
FN=file1=+1 && file2=-1
'''
count=length=0
tp=fp=tn=fn=0
file1=open("movie1.test").readlines()
file2=open("output").readlines()
for n, data in enumerate(file1):
    text=data.rstrip()
    m=re.match(r'(.+?)\s.',text).groups()
    if file2[n].rstrip()=="1":
        file2[n]="+1"
    elif file2[n].rstrip()=="-1":
        file2[n]="-1"
    if m[0]==file2[n]:
        count=count+1
    length=length+1

    if m[0]=="+1" and file2[n].rstrip()=="+1":
        tp=tp+1
    elif m[0]=="-1" and file2[n].rstrip()=="+1":
        fp=fp+1
    elif m[0]=="-1" and file2[n].rstrip()=="-1":
        tn=tn+1
    elif m[0]=="+1" and file2[n].rstrip()=="-1":
        fn=fn+1
    
accuracy=count/float(length)
print "Accuracy =",round(accuracy*100,2),"%","(",count,"/",length,")",'\n'
print "TP,FP,TN,FN=",tp,fp,tn,fn,"\n"
precision=tp/float(tp+fp)
recall=tp/float(tp+fn)
fscore=2*precision*recall/float(precision+recall)
precision2=tn/float(fn+tn)
recall2=tn/float(fp+tn)
fscore2=2*precision2*recall2/float(precision2+recall2)
print "Class\tPrecision\tRecall\tF-Measure"
print "+1\t",round(precision,3),"\t\t",round(recall,3),"\t",round(fscore,3)
print "-1\t",round(precision2,3),"\t\t",round(recall2,3),"\t",round(fscore2,3)
print "Avg\t",round(((precision+precision2)/2)*accuracy,3),"\t\t",round(((recall+recall2)/2)*accuracy,3),"\t",round(((fscore+fscore2)/2)*accuracy,3)
