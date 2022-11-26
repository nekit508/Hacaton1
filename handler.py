from flask import Flask

class Handler:
    def __init__(self):
        self.main = loadPage("main.html")
        self.app = Flask("")

    def loadPage(self, path):
        file = open("pages/" + path, "r")
        out = file.read()
        file.close()
        return out

    @self.app.route("/")
    def main(self):
        return "Null"