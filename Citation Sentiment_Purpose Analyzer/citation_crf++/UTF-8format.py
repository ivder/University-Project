# -*- coding: utf-8 -*-

f=open("NewCitingSentenceCollection.txt")
line=f.readlines()
for n, str1 in enumerate(line):
    sentences=str1.decode('utf-8','ignore')
    print sentences

f.close()
