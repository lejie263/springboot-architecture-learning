#!/bin/bash

# confirm current directory is bin
if [ "${PWD##*/}" != "bin" ]; then
    echo "Error: You must execute the command './service.sh start|stop'in the bin directory."
    exit 1
fi

if [ -z "$1" ]; then
	echo "Usage: ./service.sh start|stop"
	exit 1
fi

start() {
    cd ..
    appName="${PWD##*/}"

    #check jar only one exist
    jarFileArr=($appName-*.jar)
    if [ ! -f "${jarFileArr[0]}" ]; then
        echo "Error: confirm ${appName}-*.jar exist"
        exit 1
    elif [ ${#jarFileArr[@]} -ne 1 ]; then
        echo "Error: exist ${#jarFileArr[@]} jar, but only one ${appName}-*.jar required."
        exit 1
    fi
    jarFile="${jarFileArr[0]}"

    # check JDK
    JAVA_OPT="$JAVA_HOME/bin/java"
    if [ ! -x "$JAVA_OPT" ]; then
        echo "Error: please confirm 'JAVA_HOME' environmental variable."
        exit
    fi

    # confirm ${appName} service already stop.
    if [ -f "bin/${appName}.pid" ] &&  kill -0 `cat "bin/${appName}.pid"`>/dev/null 2>&1 ; then
        echo "Error: process "`cat bin/${appName}.pid` " already exist."
        exit
    fi

    if [ ! -d "logs" ]; then
        mkdir logs
    fi
    echo "${appName} startup now ........................................."> logs/${appName}.log
    nohup $JAVA_OPT -Dfile.encoding=utf-8 -Xms256m -Xmx2g -XX:+HeapDumpOnOutOfMemoryError -jar $jarFile > /dev/null 2>&1 &
    echo $! > "bin/${appName}.pid"
    echo "${appName} startup finished, please check starting logs ........">> logs/${appName}.log
    tailf -n +5 logs/${appName}.log
}

stop(){
    cd ..
    appName="${PWD##*/}"
    cd bin/
    if [ -f "${appName}.pid" ] &&  kill -0 `cat "${appName}.pid"`>/dev/null 2>&1 ; then
        kill -15 `cat "${appName}.pid"`
        echo "${appName} process is stoping ................................."
        while :
        do
            if kill -0 `cat "${appName}.pid"`>/dev/null 2>&1 ; then
                sleep 1
            else
                rm "${appName}.pid"
                break
            fi
        done
        echo "${appName} process stoped success."
    else
        echo "${appName} process is't exist, do't need to stop."
    fi
}

case "$1" in
    "start")
        start
            ;;
    "stop")
        stop
            ;;
    *) echo "Usage: ./service.sh start|stop" ;;
esac
