@ECHO OFF
IF %JAVA_HOME% == "" GOTO :ERROR
%JAVA_HOME%\bin\java.exe -jar .\switch-JAVA_HOME-1.0.0.jar
GOTO :END

:ERROR
wscript .\conf\vbs\error.vbe "%JAVA_HOME%为空，请检查是否安装并配置java" "Not Found JAVA"
GOTO :END

:END