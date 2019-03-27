import re
import glob
import os

positive=0
negative=0
firstpos=0
firstneg=0
finalpos=0
finalneg=0
word=[]
result="neg"

all_files=os.listdir("review_polarity/txt_sentoken/neg/") #get all text file from directory
for x in all_files: #iterate through all files
    f=open("review_polarity/txt_sentoken/neg/"+x) #open file
    line=f.readlines() #read all lines from text file
    str=''.join(line) #to string
    word=str.split(" ") #split the string to each word
    f.seek(0) #reset file pointer
    firstline=f.readline() #first line
    firststr=''.join(firstline)
    firstword=firststr.split(" ")
    finalline=line[len(line)-1] #final line
    finalstr=''.join(finalline)
    finalword=finalstr.split(" ")

    f=open("subjectivity_clues_hltemnlp05/subjectivity_clues_hltemnlp05/subjclueslen1-HLTEMNLP05.txt") #open dictionary
    line=f.readlines()
    for n, data in enumerate(line): 
        text=data.rstrip() #make it 1 line
        m=re.search(r'word1=+(.+?)\s.+priorpolarity=(.+)',text).groups() #search word and polarity, insert to group
        if m[0] in word: #match the group 1 = word dict with word varibale where the review data is stored
            if m[1]=="positive": #if positive
                positive=positive+1 #counter++
            elif m[1]=="negative":
                negative=negative+1
        if m[0] in firstword:
            if m[1]=="positive":
                firstpos=firstpos+1
            elif m[1]=="negative":
                firstneg=firstneg+1
        if m[0] in finalword:
            if m[1]=="positive":
                finalpos=finalpos+1
            elif m[1]=="negative":
                finalneg=finalneg+1

    print positive,",",negative,",",firstpos,",",firstneg,",",finalpos,",",finalneg,",",result #print result
    positive=negative=firstpos=firstneg=finalpos=finalneg=0 #reset counter value
    

f.close()
    
    



