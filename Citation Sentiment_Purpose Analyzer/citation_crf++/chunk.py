# -*- coding: utf-8 -*-

from nltk.chunk import conlltags2tree, tree2conlltags
from nltk import word_tokenize, pos_tag, ne_chunk
from nltk.corpus import conll2000
import random
import pickle
from collections import Iterable
from nltk import ChunkParserI, ClassifierBasedTagger
from nltk.stem.snowball import SnowballStemmer
 
shuffled_conll_sents = list(conll2000.chunked_sents())
random.shuffle(shuffled_conll_sents)
train_sents = shuffled_conll_sents[:int(len(shuffled_conll_sents) * 0.9)]
test_sents = shuffled_conll_sents[int(len(shuffled_conll_sents) * 0.9 + 1):]

def features(tokens, index, history):
    """
    `tokens`  = a POS-tagged sentence [(w1, t1), ...]
    `index`   = the index of the token we want to extract features for
    `history` = the previous predicted IOB tags
    """
 
    # init the stemmer
    stemmer = SnowballStemmer('english')
 
    # Pad the sequence with placeholders
    tokens = [('__START2__', '__START2__'), ('__START1__', '__START1__')] + list(tokens) + [('__END1__', '__END1__'), ('__END2__', '__END2__')]
    history = ['__START2__', '__START1__'] + list(history)
 
    # shift the index with 2, to accommodate the padding
    index += 2
 
    word, pos = tokens[index]
    prevword, prevpos = tokens[index - 1]
    prevprevword, prevprevpos = tokens[index - 2]
    nextword, nextpos = tokens[index + 1]
    nextnextword, nextnextpos = tokens[index + 2]
 
    return {
        'word': word,
        'lemma': stemmer.stem(word),
        'pos': pos,
 
        'next-word': nextword,
        'next-pos': nextpos,
 
        'next-next-word': nextnextword,
        'nextnextpos': nextnextpos,
 
        'prev-word': prevword,
        'prev-pos': prevpos,
 
        'prev-prev-word': prevprevword,
        'prev-prev-pos': prevprevpos,
    }
 
 
class ClassifierChunkParser(ChunkParserI):
    def __init__(self, chunked_sents, **kwargs):
        assert isinstance(chunked_sents, Iterable)
 
        # Transform the trees in IOB annotated sentences [(word, pos, chunk), ...]
        chunked_sents = [tree2conlltags(sent) for sent in chunked_sents]
 
        # Transform the triplets in pairs, make it compatible with the tagger interface [((word, pos), chunk), ...]
        def triplets2tagged_pairs(iob_sent):
            return [((word, pos), chunk) for word, pos, chunk in iob_sent]
        chunked_sents = [triplets2tagged_pairs(sent) for sent in chunked_sents]
 
        self.feature_detector = features
        self.tagger = ClassifierBasedTagger(
            train=chunked_sents,
            feature_detector=features,
            **kwargs)
 
    def parse(self, tagged_sent):
        chunks = self.tagger.tag(tagged_sent)
 
        # Transform the result from [((w1, t1), iob1), ...] 
        # to the preferred list of triplets format [(w1, t1, iob1), ...]
        iob_triplets = [(w, t, c) for ((w, t), c) in chunks]
 
        # Transform the list of triplets to nltk.Tree format
        return conlltags2tree(iob_triplets)
 
classifier_chunker = ClassifierChunkParser(train_sents)
#print classifier_chunker.evaluate(test_sents)

f1=open("PosCluefix.txt")
f2=open("NegCluefix.txt")
savefile1=open("CRFPositiveClueTagger.txt",'w')
savefile2=open("CRFNegativeClueTagger.txt",'w')
line1=f1.readlines()
line2=f2.readlines()
for n1, str1 in enumerate(line1):
    sentences=str1
    iob_tagged= tree2conlltags(classifier_chunker.parse(pos_tag(word_tokenize(sentences))))
    for i,data in enumerate(iob_tagged):
        #savefile1.write(data[0]+' '+data[1]+' '+data[2][0]+'\n')
        savefile1.write(data[0]+' '+data[1]+'\n')
    savefile1.write("\n")
for n2, str2 in enumerate(line2):
    sentences2=str2
    iob_tagged2= tree2conlltags(classifier_chunker.parse(pos_tag(word_tokenize(sentences2))))
    for i2,data2 in enumerate(iob_tagged2):
        #savefile2.write(data2[0]+' '+data2[1]+' '+data2[2][0]+'\n')
        savefile2.write(data2[0]+' '+data2[1]+'\n')
    savefile2.write("\n")

f1.close()
f2.close()
savefile1.close()
savefile2.close()
'''f=open("NewCitingSentenceCollection.txt")
savefile=open("NewCitingSentenceNOIOB.txt",'w')
line=f.readlines()
for n, str1 in enumerate(line):
    sentences=str1.decode('utf-8','ignore')
    iob_tagged= tree2conlltags(classifier_chunker.parse(pos_tag(word_tokenize(sentences))))
    for i,data in enumerate(iob_tagged):
        s=data[0]+' '+data[1]+' '+data[2][0]+'\n'
        #s=data[0]+' '+data[1]+'\n'
        s1=s.encode('utf-8')
        savefile.write(s1)
    savefile.write("\n")
f.close()
savefile.close()'''
