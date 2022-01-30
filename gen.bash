#!/bin/bash

#
# How to use this script: type name of the file you want to create and
# it will create pdf with all tasks from Main
#

javac ./generation/*.java Main.java -d out

NAME=$1

java -cp out Main ${NAME}

pdflatex --shell-escape ${NAME}.tex
    bibtex ${NAME}
    pdflatex --shell-escape ${NAME}.tex
    pdflatex --shell-escape ${NAME}.tex

rm -f *.cls *.sty *.bst *.aux *.auxlock *.toc *.blg *.bbl *.log *.out *.fdb_latexmk *.fls *.synctex.gz *.tex

rm -f -r out
