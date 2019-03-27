import re
f=open("PerformanceResult")
line=f.readlines()
count=0
for n, data in enumerate(line):
    m=re.match(r'\d+\s\d+\s(.+)',data).groups()
    count+=int(m[0])
print float(count)/684
f.close()

'''
for n, str1 in enumerate(line1):
    str1.rstrip()
    if "<pos>" in str1:
        for n2, str2 in enumerate(line2):
            m=re.match(r'(.+?)\t=>\t(.+)',str2).groups()
            if m[0] in str1:
                if m[1]=="POS":
                    truecounter+=1
                elif m[1]=="NEG":
                    falsecounter+=1
    if "<neg>" in str1:
        for n3, str3 in enumerate(line2):
            m2=re.match(r'(.+?)\t=>\t(.+)',str3).groups()
            if m2[0] in str1:
                if m2[1]=="POS":
                    falsecounter+=1
                elif m2[1]=="NEG":
                    truecounter+=1

    if truecounter > falsecounter:
        result=1
        acccounter+=1
    elif truecounter < falsecounter :
        result=0
    print truecounter, falsecounter, result            
    truecounter=falsecounter=result=0
    '''
