#########################################################################
# File Name: start.sh
#########################################################################
#!/bin/bash

git pull

mvn clean package -Dmaven.test.skip

pkill -9 -f tineng

nohup java -jar target/tineng.jar &
