# -*- coding: utf-8 -*-
import re

f=open("RecordExtraction.txt") #open file
line=f.readlines()[1:] #read the file line by line

for n, data in enumerate(line):
    text=data.rstrip() #delete the new line \n
    #use regex matching
    try:
        m=re.match(r'(.+?)[.,]\s+\“+(.+?),\”\s+(.+?),\s+(.+?),\s.+(\d+-\d+)\S\s+(\d+)', text).groups() #match each line and assing the wanted value to group
        print m
        print "저자명: "+m[0] #group 1
        print "출판년도: "+m[5] #group 6
        print "Title: "+m[1] #group 2
        print "Publication: "+m[2]  #group 3
        print "Volume: "+m[3] #group 4
        print "페이지: "+m[4]+"\n" #group 5
    #for citation without page and volume number
    except AttributeError:
        m=re.match(r'(.+?)[.,]\s+\“+(.+?),\”\s+(.+?),\s+(\d+)', text).groups()
        print m
        print "저자명: "+m[0]
        print "출판년도: "+m[3]
        print "Title: "+m[1]
        print "Publication: "+m[2]+"\n"
    except AttributeError:
        m=re.match(r'(.+?)[.,]\s+\“+(.+?),\”\s+(.+?),\s.+(\d+-\d+),\s+(\d+)', text).groups()
        print m
        print "저자명: "+m[0]
        print "출판년도: "+m[4]
        print "Title: "+m[1]
        print "Publication: "+m[2]
        print "페이지: "+m[3]+"\n"
     

f.close()
        





