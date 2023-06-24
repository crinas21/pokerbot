from typing import List
import math

suits = ["S", "D", "C", "H"]
numbers = ["2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14"]
num_cards_for_stage = {
    "holes": 2,
    "flop": 3,
    "turn": 1,
    "river": 1
}
face_to_num = {
    "1": "14",
    "A": "14",
    "J": "11",
    "Q": "12",
    "K": "13"
}
draws = {
    2: 3,
    5: 1,
    6: 1
}


def getValidCards(stage: str, cards_out: List[List]) -> List[List]:
    invalid = True

    while invalid:
        cards_string = input(f"Enter {stage}: ").upper().strip()
        if cards_string == "RESET":
            return None

        cards_list = cards_string.split(" ")
        num_cards = len(cards_list)
        split_cards = []

        # Checking for invalid duplicates and invalid number of cards shown for the given stage
        if len(set(cards_list)) < num_cards or num_cards != num_cards_for_stage[stage]:
            print("Invalid Format\n")
            continue

        for card in cards_list:
            num = card[:-1]
            suit = card[-1]

            if num in face_to_num:
                num = face_to_num[num]

            if num not in numbers or suit not in suits or [int(num), suit] in cards_out:
                print("Invalid Format\n")
                invalid = True
                break
            split_card = [int(num), suit]
            split_cards.append(split_card)
            invalid = False

    return split_cards


def pair(cards_out: List[List]) -> float:
    # Sort by num of each card
    cards_out.sort(key=lambda x: x[0])
    print(cards_out)
    
    # Case 1: Pair already exists
    prev_num = cards_out[0][0]
    for i in range(1, len(cards_out)):
        if cards_out[i][0] == prev_num:
            return 100
        prev_num = cards_out[i][0]

    # Case 2: All cards_out have a different num
    no_pair_cards_left = 3*len(cards_out)
    no_cards_left = 52 - len(cards_out)
    no_draws = draws[len(cards_out)]
    
    p_not_get_pair = 1
    for i in range(no_draws):
        no_non_pair_cards_left = no_cards_left - no_pair_cards_left
        p_not_get_pair *= no_non_pair_cards_left / no_cards_left    # 1 minus the probability of not getting any cards matching the nums of the current cards_out
        no_cards_left -= 1
    p_get_pair = 1 - p_not_get_pair
    
    # Alternatively:
    no_ways_get_pair = 0
    no_non_pair_cards_left = no_cards_left - no_pair_cards_left
    for i in range(no_draws):
        k = i + 1
        no_ways_get_pair += math.comb(no_pair_cards_left, k) * math.comb(no_non_pair_cards_left, no_draws - k)
    p_get_pair = no_ways_get_pair / math.comb(no_cards_left, no_draws)
    
    return p_get_pair


def main():
    play = True

    while play:
        cards_out = []
        holes = getValidCards("holes", cards_out)
        if holes is None:
            continue
        cards_out.extend(holes)
        print(cards_out)
        print(pair(cards_out))

        flop = getValidCards("flop", cards_out)
        if flop is None:
            continue
        cards_out.extend(flop)
        print(cards_out)

        print(pair(cards_out))

        turn = getValidCards("turn", cards_out)
        if turn is None:
            continue
        cards_out.extend(turn)

        river = getValidCards("river", cards_out)
        if river is None:
            continue
        cards_out.extend(river)

        while True:
            cont = input("Another Round? (Y/N) ").lower()
            if cont == "y":
                break
            elif cont == "n":
                play = False
                break



if __name__ == "__main__":
    main()