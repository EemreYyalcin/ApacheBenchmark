#!/bin/bash

CONCURENCY=1000;
REQUEST=90000;
URL="http://localhost:8080/benchmark/bench/big"

exit_()
{
    exit
}

POSITIONAL=()
while [[ $# -gt 0 ]]
do
key="$1"

case $key in
    -c)
    CONCURENCY="$2"
    shift # past argument
    shift # past value
    ;;
    -n)
    REQUEST="$2"
    shift # past argument
    shift # past value
    ;;
    -url)
    URL="$2"
    shift # past argument
    shift # past value
    ;;
    *)    # unknown option
    POSITIONAL+=("$1") # save it in an array for later
    shift # past argument
    ;;
esac

done

SEPERATE="_"
FILE="logFile/Tomcat_FileConcurencyRequest$SEPERATE$CONCURENCY$SEPERATE$REQUEST$SEPERATE.txt";

echo "" > $FILE
COUNT=0;

TIME=$(date '+%d/%m/%Y %H:%M:%S');

echo "Started_Executing " $TIME

while [ $COUNT -lt 51 ];do

SECONDS=0;

let COUNT+=1;

echo "START_N" $COUNT

TIME=$(date '+%d/%m/%Y %H:%M:%S');

echo "SESSION_START " $TIME >> $FILE

ab -c $CONCURENCY -n $REQUEST $URL >> $FILE

echo "SESSION_TIME : $SECONDS" >> $FILE

TIME=$(date '+%d/%m/%Y %H:%M:%S');

echo "SESSION_END " $TIME >> $FILE 

trap exit_ int ;

done




