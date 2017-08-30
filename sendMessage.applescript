on run {isIphone, targetBuddyPhone, targetMessage}
    tell application "Messages"
        if (isIphone is equal to "Y" or isIphone is equal to "YES")
            set targetService to 1st service whose service type = iMessage
            set targetBuddy to buddy targetBuddyPhone of targetService
        else
            set targetBuddy to buddy targetBuddyPhone of service "SMS"
        end if

        send targetMessage to targetBuddy
    end tell
end run
