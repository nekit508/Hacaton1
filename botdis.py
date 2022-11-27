import discord
from discord.ext import commands
import sys
import time


def discordbot(id, token, talk):
    intents = discord.Intents.default()
    intents.presences = True
    intents.members = True
    intents.message_content = True

    prefix = "/"
    bot = commands.Bot(prefix, intents=intents)

    @bot.event
    async def on_ready():
        print('BOT READY')
        channel = bot.get_channel(id)
        await channel.send(talk)
        sys.exit(-9)

    @bot.event
    async def on_message(message):
        print(message.content)
        await bot.process_commands(message)

    bot.run(token)

discordbot(int(sys.argv[1]), sys.argv[2], sys.argv[3])