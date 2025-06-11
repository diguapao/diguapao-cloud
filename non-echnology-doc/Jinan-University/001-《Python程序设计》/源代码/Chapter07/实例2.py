import random


class Player:
    def __init__(self):
        self.dict_gesture = {0: '剪刀', 1: '石头', 2: '布'}

    def gesture(self, num):
        return self.dict_gesture[num]


class AIPlayer(Player):
    play_data = []  # 存储玩家猜拳手势

    def ai_gesture(self):
        while True:
            computer = random.randint(0, 2)
            if len(self.play_data) >= 4:
                # 获取玩家出拳的最大概率
                max_prob = max(self.play_data, key=self.play_data.count)
                if max_prob == '剪刀':
                    return '石头'
                elif max_prob == '石头':
                    return '布'
                else:
                    return '剪刀'
            else:
                return self.dict_gesture[computer]


class Game(Player):
    def game_judge(self):
        player_num = int(input("请输入(0剪刀、1石头、2布:)"))  # 玩家输入的手势
        player = self.gesture(player_num)
        AIPlayer().play_data.append(player)
        ai_player = AIPlayer().ai_gesture()
        if (player == '剪刀' and ai_player == '布') or \
                (player == '石头' and ai_player == '剪刀') \
                or (player == '布' and ai_player == '石头'):
            print(f"电脑出的手势是{ai_player},恭喜，你赢了!")
        elif (player == '剪刀' and ai_player == '剪刀') or \
                (player == '石头' and ai_player == '石头') \
                or (player == '布' and ai_player == '布'):
            print(f"电脑出的手势是{ai_player},打成平局了！")
        else:
            print(f"电脑出的手势是{ai_player},你输了，再接再厉！")

    def main(self):
        self.game_judge()
        while True:
            option = input("是否继续:y/n\n")
            if option == 'y':
                self.game_judge()
            else:
                break
game = Game()
game.main()
