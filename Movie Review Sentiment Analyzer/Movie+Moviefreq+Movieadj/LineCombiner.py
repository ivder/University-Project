combine=[]

with open("movieadj.data") as a:
    with open("movie.data") as b:
        with open("moviefreq.data") as c:
            with open("moviecombine.data","w") as d:
                aline=a.readlines()
                bline=b.readlines()
                cline=c.readlines()
                for i in range(len(aline)):
                    line=aline[i].strip()+' '+bline[i].strip()+cline[i]
                    d.write(line)

a.close()
b.close()
c.close()
d.close()
