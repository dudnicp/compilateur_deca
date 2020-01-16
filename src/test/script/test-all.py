#! /usr/bin/env python3

import os
from filecmp import cmp
from sys import argv

rPath = "../../test/deca"
lexValidPath = rPath + "/syntax/valid/lex/"
lexInvalidPath = rPath + "/syntax/invalid/lex/"
syntValidPath = rPath + "/syntax/valid/synt/"
syntInvalidPath = rPath + "/syntax/invalid/synt/"
contValidPath = rPath + "/syntax/valid/"
contInvalidPath = rPath + "/syntax/invalid/"


def listdirectory(path):
    fichier=[]
    for root, dirs, files in os.walk(path):
        for i in files:
            fichier.append(os.path.join(root, i))
    return fichier


def exeScript(path, X):
    """
    call the script test_X on all the files and compare the output with a log
    file created before, if this file doesn't exist you can create it
    X = lex, synt or context
    """
    passed = 0
    didntexist = 0
    total = 0
    summary = []
    files = listdirectory(path)

    print("TESTING {} FILES".format(X))

    for fichier in files:
        if fichier[-5:] == '.deca':
            total += 1
            print(fichier, end="\n\n")
            print("Programme deca :", end ="\n")
            os.system("cat {}".format(fichier))
            print()
            fichierDeca = fichier.split('/')[-1]
            fichierLog = path + "log/" + fichierDeca.replace('.deca', '.log' + X)
            if fichierLog not in files:
                print("{} output :".format(X))
                os.system("launchers/test_{} < {}".format(X, fichier))
                print("there is no log file yet! Do you want to use this output as a log file?")
                if input() in ["yes", "y", "o", "oui"]:
                    os.system("touch " + fichierLog)
                    os.system("launchers/test_{} < {} > {}".format(X, fichier, fichierLog))
                    print("choice saved")
                    didntexist += 1
            else:
                tmp = fichier.replace('.deca', '.logtmp')
                os.system("touch " + tmp)
                os.system("launchers/test_{} < {} > {}".format(X, fichier, tmp))
                if cmp(fichierLog, tmp):
                    print("passed")
                    passed += 1
                    summary.append((fichierDeca, True))
                else:
                    print("unexpected output! Diff:")
                    os.system("diff {} {}".format(fichierLog, tmp))
                    summary.append((fichierDeca, False))

                os.system("rm -r {}".format(tmp))
            print("-----------------------------------------------")
    for fichierD, resultat in summary:
        print(fichierD, "passed" if resultat else "failed")
    print("{} out of {} passed, {} weren't commited yet".format(passed, total, didntexist))


def main():
    if "lex" in argv:
        exeScript(lexValidPath, "lex")
    if "synt" in argv:
        exeScript(syntValidPath, "synt")
    if "context" in argv:
        exeScript(contValidPath, "context")
    if "lexv" in argv:
        exeScript(lexInvalidPath, "lex")
    if "syntv" in argv:
        exeScript(syntInvalidPath, "synt")
    if "contextv" in argv:
        exeScript(contInvalidPath, "context")


if __name__ == "__main__":
    main()
