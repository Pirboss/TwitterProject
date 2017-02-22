#!/usr/bin/env python

import nltk
from nltk import sent_tokenize, word_tokenize, pos_tag
from nltk.stem import WordNetLemmatizer

wordnet_lemmatizer = WordNetLemmatizer()

def load_document(inFile):
    doc = []
    for line in open(inFile):
        line = line.decode('utf8').strip()
        doc.append(line)
    return doc

def tokenize_document(doc):
    tok_doc = []
    for line in doc:
		sentence_tokenized = sent_tokenize(line)
		for sentence in sentence_tokenized:
			tok_doc.append(word_tokenize(sentence))    
    return tok_doc

def postag_document(doc):
    postag_doc = []
    for sentence in doc:
		postag_doc.append(pos_tag(sentence))
    return postag_doc

def lemmatize_document(doc):
    #lemma_doc = []
    open(outFile, 'w').close()
    file = open(outFile, "w")
    for sentence in doc:
	for couple in sentence:
            #lemma_doc.append(wordnet_lemmatizer.lemmatize(couple[0]))
	    file.write(str(wordnet_lemmatizer.lemmatize(couple[0])) + " ")
    file.close()
    return "OK" #lemma_doc
            
#inFile = 'corpus/token/brexit.txt'
inFile = 'src/resources/sample.txt'
outFile = 'src/resources/sampleLem.txt'

doc = load_document(inFile)
tokenized = tokenize_document(doc)
postagged = postag_document(tokenized)
lemmatized = lemmatize_document(postagged)
