:: ---------------------------------------------------------------------
:: JAP COURSE - SCRIPT
:: SCRIPT CST8221 - Fall 2023
:: ---------------------------------------------------------------------
:: Begin of Script (A12 - F23)
:: ---------------------------------------------------------------------

CLS

:: LOCAL VARIABLES
SET LIBDIR=lib
SET SRCDIR=Level 4 JAP\src
SET BINDIR=Level 4 JAP\bin
SET BINERR=jap-javac.err
SET JARNAME=jap.jar
SET JAROUT=jap-jar.out
SET JARERR=jap-jar.err
SET DOCDIR=Level 4 JAP\doc
SET DOCPACK=cs
SET PACKAGE=cs
SET DOCERR=jap-javadoc.err
SET MAINCLASSSRC=Level 4 JAP\src\cs\CSModel.java
SET MAINCLASSBIN=cs.CSModel

@echo off

ECHO "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
ECHO "@                    JAP COURSE - SCRIPT                             @"
ECHO "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
ECHO "[LABS SCRIPT ---------------------]"
ECHO "0. Preconfiguring ................."
IF NOT EXIST "%BINDIR%" (
    mkdir "%BINDIR%"
)
IF NOT EXIST "%BINDIR%\%PACKAGE%" (
    mkdir "%BINDIR%\%PACKAGE%"
)
copy "%SRCDIR%\%PACKAGE%\*.png" "%BINDIR%\%PACKAGE%"
copy "%SRCDIR%\%PACKAGE%\*.gif" "%BINDIR%\%PACKAGE%"

ECHO "1. Compiling ......................"
javac -Xlint -cp ".;%SRCDIR%" %MAINCLASSSRC% -d %BINDIR% 2> %BINERR%

ECHO "2. Creating Jar ..................."
cd %BINDIR%
jar cvfe %JARNAME% %MAINCLASSBIN% . > ../%JAROUT% 2> ../%JARERR%
cd ..

ECHO "3. Creating Javadoc ..............."
javadoc -cp ".;%BINDIR%" -d %DOCDIR% -sourcepath %SRCDIR% -subpackages %DOCPACK% 2> %DOCERR%

ECHO "4. Running Jar ...................."
cd %BINDIR%
start java -jar %JARNAME%
cd ..

ECHO "[END OF SCRIPT -------------------]"
ECHO " "
@echo on

:: ---------------------------------------------------------------------
:: End of Script (A12 - F23)
:: ---------------------------------------------------------------------