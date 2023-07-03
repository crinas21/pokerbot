# pokerbot
- Python program that takes input of what cards are currently known at a certain stage in a poker round, and outputs the probability of achieving each type of hand in the next stages.

# Input
- When prompted for input, a card should be represented as its number followed by the first letter of its suit.
- When entering multiple cards, they should be separated by a single space.
- Case insensitive.
- For example, say our hole cards are the two of spades and the king of hearts. The following are examples of valid inputs:
    - 2S KH
    - 2s 13h
- As another example, say the flop shows the ace of diamonds, 5 of clubs, and jack of hearts. The following are examples of valid inputs:
    - AD 5C JH
    - 14d 5c 11h
    - ad 5C jH
- If an invalid format is given, a message will display "Invalid Format" and you will be prompted again.
- If the wrong number of cards is provided at the given stage, a message will tell you how many cards should be given and you will be prompted again.
- If you have inputted an incorrect card by mistake, enter 'reset' into any prompt and the round will be reset, asking you for the hole cards again.