f1=open("NewPositiveClue.txt")
f2=open("NewNegativeClue.txt")
f3=open("OldPositiveClue.txt")
f4=open("OldNegativeClue.txt")
savefile=open("CitationSentimentDictionary.txt",'w')
line1=f1.readlines()
line2=f2.readlines()
line3=f3.readlines()
line4=f4.readlines()

for n,data in enumerate(line1):
    data=data.rstrip()
    savefile.write(data+'\t=>\tPOS\n')
for n2,data2 in enumerate(line2):
    data2=data2.rstrip()
    savefile.write(data2+'\t=>\tNEG\n')
'''
for n3,data3 in enumerate(line3):
    data3=data3.rstrip()
    savefile.write(data3+'\t=>\tPOS\n')
for n4,data4 in enumerate(line4):
    data4=data4.rstrip()
    savefile.write(data4+'\t=>\tNEG\n')
    '''
f1.close()
f2.close()
f3.close()
f4.close()
savefile.close()
