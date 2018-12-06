@echo off
set CLASSPATH=.;%CLASSPATH%;.\xxx.jar
set JAVA=%JAVA_HOME%\bin\java
"%JAVA%" -jar WipeData.jar
pause