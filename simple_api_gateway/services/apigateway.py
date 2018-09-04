from flask import Flask, request, jsonify
from flask_cors import CORS
import requests


LOCALHOST = "http://localhost:"


PORTS = {"login": "7700/",
         "register": "7700/",
         "update": "7770/",
         "check": "7770/",
         "remove": "7770/",
         "groups": "7770/",
         "add_group": "7700/",
         "find": "7770/"}

app = Flask(__name__)
CORS(app)


@app.route("/test")
def test():
    return "Api gateway service works"


@app.route("/api_test")
def api_test():
    response = requests.post(LOCALHOST + "7770/api_test", json={'id': 2415, 'name': 'Unknown'})
    return response.content


@app.route("/apigateway", methods=['POST'])
def api_call():
    req_data = request.get_json(force=True)
    req = req_data['request']
    port = PORTS[req]
    response = requests.post(LOCALHOST + port + req, json=req_data)
    return response.content


if __name__ == '__main__':
    app.run(port=7777)
