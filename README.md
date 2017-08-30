# Hangman...with a twist

## How did this start
I was on a flight from San Francisco to New York City, and as with many flights, I ran out of things to do 15 minutes after takeoff. When my plane reached a stable cruising altitude, I pulled out a game controller so conveniently located to my left. Looking through the plane's selections, I spotted Hangman.

Just a few rounds was enough to put me close to sleep. I couldn't fall asleep either, so I decided to code up the game myself from scratch. And hence this was born.

Halfway into writing the code, I noticed an attractive young lady, who looked just as bored as I was, sitting just diagonal from my aisle seat. I wondered if the 2 hours of heart and soul I poured into this program could help me strike up a conversation with her. So I made a few changes...

And it did. And now it can help you.

## Description
A simple command-line Hangman game built with a little Bash, Java, and a dash of AppleScript. Currently the `message` mode only works on Macs with iMessage properly configured.

## How to play
Just run `./game.sh -m MODE -u USER`.
* `MODE` can be `file`, `message`, or `normal`. `file` writes the input number to a file, `message` sends a pre-written text message directly to the input number, and `normal` just runs a normal version of Hangman. The game defaults to `normal` if no mode option is provided.
* `USER` specifies the user's name. This defaults to my name (Justin) if no user option is provided.

## Challenges
* My Java was rusty.
* I had no wifi to look up syntax so I tried a lot of library function names before guessing the right one.
* Turbulence periodically made me airsick while looking at the screen.
* Bash

## FAQ

### There's no way you wrote this in 2 hours you idiot
The original version only had a Java file

### You can't write Java that fast
Ok

### Your lines are lame
I was focused more on finishing the code before the end of the flight

### Where's the UI?
Not here right now
