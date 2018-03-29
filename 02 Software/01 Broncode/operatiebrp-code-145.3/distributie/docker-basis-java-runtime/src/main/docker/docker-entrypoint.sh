#!/bin/sh
JAVA_OPTS="$JAVA_OPTS -cp conf:lib/*"

for c in $@; do
    if [ "$c" = "debug" ]; then
        JAVA_OPTS="$JAVA_OPTS -agentlib:jdwp=transport=dt_socket,address=8000,server=y,suspend=n"
    fi
done

for c in $@; do
    if [ "$c" = "java" ]; then
        COMMAND="$COMMAND $c $JAVA_OPTS"
    elif [ "$c" != "debug" ]; then
        COMMAND="$COMMAND $c"
    fi
done

echo Executing $COMMAND >&2
/bin/sh -c "$COMMAND"
