import re

line=open("DataSet.txt").readlines()
result=open('movie.data','w')
for n, data in enumerate(line):
    text=data.rstrip()
    try:
        m=re.match(r'(\d+)\s,\s+(\d+)\s,\s+(\d+)\s,\s+(\d+)\s,\s+(\d+)\s,\s+(\d+)\s,\s+(.+)',text).groups()
        m=list(m)
        if m[6]=="pos":
            m[6]="+1"
        elif m[6]=="neg":
            m[6]="-1"
        format=m[6]+' 1:'+m[0]+' 2:'+m[1]+' 3:'+m[2]+' 4:'+m[3]+' 5:'+m[4]+' 6:'+m[5]+'\n'
        result.write(format)
    except AttributeError:
        print " "
result.close()

