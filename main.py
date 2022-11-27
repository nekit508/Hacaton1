import vk_api

def post():
    import sys
    import random

    def vk_auth(login, password):
        vk = vk_api.VkApi(login, password)

        vk.auth()

        return vk

    LOGIN = sys.argv[1]
    PASSWORD = sys.argv[2]
    OWNER_ID = sys.argv[3]
    MESSAGE = sys.argv[4]

    if __name__ == '__main__':
        vk = vk_auth(LOGIN, PASSWORD)

        hrefs = [
            'https://danbooru.donmai.us/data/sample/--saber-and-saber-alter-fate-stay-night-and-fate-series-drawn-by-goldengear870--sample-5c66a91632c80384fd9a1e8c43bb5fd7.jpg'
        ]

        random.shuffle(hrefs)

        for href in hrefs:
            rs = vk.method('wall.post', {
                'owner_id': OWNER_ID,
                'message': MESSAGE,
                'attachments': href,
            })
            print(rs)

post()