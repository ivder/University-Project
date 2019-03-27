import sys
with open("NegCluefix.txt") as f:
    for line in f:
        if not line.isspace():
            sys.stdout.write(line)
