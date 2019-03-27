f=open("PosNegClueTag.txt")
savefile=open("PosNegClueTagFix.txt",'w')
line=f.readlines()
for n, data in enumerate(line):
    data.rstrip()
    if "<pos>" in data or "<neg>" in data:
        savefile.write(data)
savefile.close()
f.close()
