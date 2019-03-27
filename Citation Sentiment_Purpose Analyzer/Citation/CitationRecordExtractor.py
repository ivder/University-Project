data=open("ReferenceListSample.txt").readlines() #open text and read each line, assign value to data
for n, line in enumerate(data): 
    if line.startswith('['): #detect [ 1 ] (number)
        data[n]=line[5:] #start from position 5 after [ 1 ] 
        if data[n].startswith(' '): #for data with blank space start
            data[n]=data[n][1:] #get the value start from bit after blank space
            data[n]="\n"+data[n].rstrip() #delete new line \n at the end of the data
        else:
            data[n]="\n"+data[n].rstrip() #if no blank space, directly strip the \n
    else:
        data[n]=line.rstrip() #for line starts without [ 1 ] directly strip the \n
result=open('RecordExtraction.txt','w') #result file
s=' '.join(data) #join all the data to become string
result.write(s) #write the string
result.close()
