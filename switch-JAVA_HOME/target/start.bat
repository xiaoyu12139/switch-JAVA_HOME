@ECHO OFF
IF %JAVA_HOME% == "" GOTO :ERROR
%JAVA_HOME%\bin\java.exe -jar .\switch-JAVA_HOME-1.0.0.jar
GOTO :END

:ERROR
wscript .\conf\vbs\error.vbe "%JAVA_HOME%Ϊ�գ������Ƿ�װ������java" "Not Found JAVA"
GOTO :END

:END