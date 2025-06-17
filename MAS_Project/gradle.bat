@echo off
REM Uruchomienie aplikacji z odpowiednimi parametrami kodowania
gradlew run -Dfile.encoding=UTF-8 -Dconsole.encoding=UTF-8 %*
