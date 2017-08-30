#!/bin/bash

show_usage() {
    echo "usage: $(basename $0) [args]
where args can include:
-m, --mode MODE:    Mode of game to play. Supports 'normal', 'file', and 'message'
                    Defaults to 'normal'
-u, --user USER:    Name of player. Defaults to 'Justin'
-h, --help     :    Display this help dialog
"
}

handle_sig_int() {
    echo $'\nAw :( thanks for playing though'
    exit 1
}

if [ $# -lt 1 ]; then
    show_usage
    exit 1
fi

# Default values
mode="normal"
user="Justin"

# Parse command line
while [ $# -gt 0 ]; do
    opt="$1"
    case $opt in
        -m|--mode)
            mode="$2"
            shift
            ;;
        -u|--user)
            user="$2"
            shift
            ;;
        -h|-\?|--help)
            show_usage
            exit 1
            ;;
        *)
            show_usage
            exit 1
            ;;
    esac
    shift
done

trap handle_sig_int SIGINT
javac Hangman.java
java Hangman $mode

case $mode in
    file|message)
        echo "Great job finding a word that describes you! Here's your next challenge ;)"
        read -p $'Fill in the blanks: (_ _ _) _ _ _ - _ _ _ _\n' num
        while ! [[ $num =~ ^[0-9]+$ ]] || [ ${#num} != 10 ]; do
            read -p $'Just type 10 numbers!\n' num
        done

        if [ $mode = 'file' ]; then
            mkdir -p nums
            echo $num > nums/num.txt
            echo "Thanks for playing :)"
        elif [ $mode = 'message' ]; then
            read -p $'iPhone? (yes/no)\n' is_iphone
            is_iphone=$(echo "$is_iphone" | tr '[a-z]' '[A-Z]')
            osascript sendMessage.applescript "$is_iphone" "$num" "Hey my name's $user :) What's yours?"

            echo "Now reply to my text :)"
        fi
esac
