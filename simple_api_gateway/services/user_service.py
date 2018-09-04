from pymongo import MongoClient
from flask import Flask, request, jsonify, make_response
from flask_cors import CORS
from datetime import datetime, timedelta
import jwt


MESSAGE = "Message"
SUCCESS = "Successful operation"
ER_LOGIN = "Error"
ER_ALREADY_EXIST = "False! Already exists"
SECRET_KEY = "apigateway"
EXP = "exp"
TOKEN = "token"

DATABASE = "usersdb"
CL_ADMINS = "admins"
CL_STUDENTS = "students"

FD_USERNAME = "username"
FD_PASSWORD = "password"
FD_FNAME = "first_name"
FD_LNAME = "last_name"
FD_INDEX = "index_num"
FD_GROUP = "group"
FD_EMAIL = "email"
FD_OP = "op"

app = Flask(__name__)
CORS(app)


@app.route("/test")
def test():
    return "User service works"


@app.route("/register", methods=['POST'])
def registration():
    client = MongoClient()
    db = client[DATABASE]
    req_data = request.get_json(force=True)
    op = req_data[FD_OP]
    ack = ""

    if request.method == 'POST':
        if op == CL_STUDENTS:
            username = req_data[FD_USERNAME]
            password = req_data[FD_PASSWORD]
            first_name = req_data[FD_FNAME]
            last_name = req_data[FD_LNAME]
            index = req_data[FD_INDEX]

            students = db[CL_STUDENTS]

            found = students.find_one({FD_USERNAME: username})
            if found is not None:
                ack = ER_ALREADY_EXIST
            else:
                value = students.insert_one({FD_USERNAME: username, FD_PASSWORD: password, FD_FNAME: first_name,
                                            FD_LNAME: last_name, FD_INDEX: index})
                ack = value.acknowledged
        elif op == CL_ADMINS:
            username = req_data[FD_USERNAME]
            password = req_data[FD_PASSWORD]
            first_name = req_data[FD_FNAME]
            last_name = req_data[FD_LNAME]
            email = req_data[FD_EMAIL]

            admins = db[CL_ADMINS]
            found = admins.find_one({FD_USERNAME: username})
            if found is not None:
                ack = ER_ALREADY_EXIST
            else:
                value = admins.insert_one({FD_USERNAME: username, FD_PASSWORD: password, FD_FNAME: first_name,
                                          FD_LNAME: last_name, FD_EMAIL: email})
                ack = value.acknowledged

    client.close()
    return make_response(jsonify({MESSAGE: ack}), 200)


@app.route("/login", methods=['POST'])
def login():
    req_data = request.get_json(force=True)
    username = req_data[FD_USERNAME]
    password = req_data[FD_PASSWORD]
    op = req_data[FD_OP]

    client = MongoClient()
    db = client[DATABASE]
    collection = db[op]
    data = collection.find_one({FD_USERNAME: username, FD_PASSWORD: password})
    client.close()

    if data is None:
        return make_response(jsonify({MESSAGE: ER_LOGIN}), 200)

    expire = datetime.utcnow() + timedelta(hours=1)
    token = jwt.encode({FD_USERNAME: username, EXP: expire}, SECRET_KEY)
    return make_response(jsonify({MESSAGE: SUCCESS, TOKEN: token.decode("UTF-8")}), 200)


@app.route("/add_group", methods=['POST'])
def add_group():
    req_data = request.get_json(force=True)
    group = req_data[FD_GROUP]
    username = req_data[FD_USERNAME]
    client = MongoClient()
    db = client[DATABASE]
    collection = db[CL_STUDENTS]
    user = collection.find_one({FD_USERNAME: username})
    value = collection.update_one({"_id": user['_id']}, {"$set": {FD_GROUP: group}})
    return jsonify({MESSAGE: value.acknowledged})


if __name__ == '__main__':
    app.run(port=7700)
