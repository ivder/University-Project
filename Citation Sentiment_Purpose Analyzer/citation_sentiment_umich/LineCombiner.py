combine=[]

with open("citationpolarity.data") as a:
    with open("libsvmformat.txt") as b:
        with open("citationpolarityfreq.data","w") as c:
            aline=a.readlines()
            bline=b.readlines()
            for i in range(len(aline)):
                line=aline[i].strip()+''+bline[i]
                c.write(line)

a.close()
b.close()
c.close()
