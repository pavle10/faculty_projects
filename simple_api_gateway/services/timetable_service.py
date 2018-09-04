from pymongo import MongoClient
from flask import Flask, request, jsonify
from flask_cors import CORS
import csv
from functools import wraps
import jwt
from bson.json_util import dumps


MESSAGE = "Message"
SUCCESS = "Successful operation"
OK = "OK"
DATA = "data"
SECRET_KEY = "apigateway"
TOKEN = "token"
MISSING_TOKEN = "Token is missing!"
INVALID_TOKEN = "Token is invalid!"

DATABASE = "timetabledb"
COLLECTION = "timetable"

FD_SUBJECT = "subject"
FD_TYPE = "s_type"
FD_LECTURER = "lecturer"
FD_GROUP = "group"
FD_ALL_GROUPS = "all_groups"
FD_DAY = "day"
FD_START = "start"
FD_END = "end"
FD_CLASSROOM = "classroom"
FD_FILEPATH = "filepath"


app = Flask(__name__)
CORS(app)


def fix(day):
    if 'PON' in day[4]:
        day[4] = 'PON'
    elif 'UTO' in day[4]:
        day[4] = 'UTO'
    elif 'SRE' in day[4]:
        day[4] = 'SRE'
    elif 'PET' in day[4]:
        day[4] = 'PET'
    elif 'ET' in day[4]:
        day[4] = 'CET'
    elif 'SUB' in day[4]:
        day[4] = 'SUB'

    termin = day[5]
    del(day[5])
    termin = termin.split('-')
    day.extend([termin[0], termin[1] + ":00"])
    day[3] = day[3].split(',')


def token_required(f):
    @wraps(f)
    def decorated(*args, **kwargs):
        req_data = request.get_json(force=True)
        token = req_data[TOKEN]

        if not token:
            return jsonify({MESSAGE: MISSING_TOKEN})

        try:
            data = jwt.decode(token, SECRET_KEY)
        except:
            return jsonify({MESSAGE: INVALID_TOKEN})

        return f(*args, **kwargs)

    return decorated


@app.route("/test")
def test():
    return "Timetable service works"


@app.route("/update", methods=['POST', 'GET'])
@token_required
def update_timetable():
    req_data = request.get_json(force=True)
    filepath = req_data[FD_FILEPATH]
    client = MongoClient()
    db = client[DATABASE]
    collection = db[COLLECTION]
    groups = []

    with open(filepath) as ifile:
        schedule = csv.reader(ifile, delimiter=',', quotechar='"')
        next(schedule, None)
        for row in schedule:
            fix(row)
            collection.insert_one({FD_SUBJECT: row[0], FD_TYPE: row[1], FD_LECTURER: row[2], FD_GROUP: row[3],
                                   FD_DAY: row[4], FD_START: row[6], FD_END: row[7], FD_CLASSROOM: row[5]})
            for group in row[3]:
                group = group.strip()
                if group not in groups:
                    groups.append(group)
    collection.insert_one({FD_ALL_GROUPS: groups})

    client.close()

    return jsonify({MESSAGE: SUCCESS})


@app.route("/groups", methods=['POST'])
@token_required
def get_groups():
    client = MongoClient()
    db = client[DATABASE]
    collection = db[COLLECTION]
    data = collection.find_one({FD_ALL_GROUPS: "401"})
    client.close()
    return jsonify({MESSAGE: OK, DATA: data[FD_ALL_GROUPS]})


@app.route("/check", methods=['POST'])
@token_required
def check_token():
    return jsonify({MESSAGE: OK})


@app.route("/remove", methods=['POST'])
@token_required
def remove_collection():
    client = MongoClient()
    db = client[DATABASE]
    collection = db[COLLECTION]
    collection.remove({})
    client.close()
    return jsonify({MESSAGE: SUCCESS})


@app.route("/find", methods=['POST'])
@token_required
def find_schedule():
    req_data = request.get_json(force=True)
    group = req_data[FD_GROUP]
    classroom = req_data[FD_CLASSROOM]
    day = req_data[FD_DAY]
    value = ""
    client = MongoClient()
    db = client[DATABASE]
    collection = db[COLLECTION]

    if group != "":
        if classroom != "":
            if day != "":
                value = collection.find({FD_GROUP: group, FD_CLASSROOM: classroom, FD_DAY: day})
            else:
                value = collection.find({FD_GROUP: group, FD_CLASSROOM: classroom})
        elif day != "":
                value = collection.find({FD_GROUP: group, FD_DAY: day})
        else:
            value = collection.find({FD_GROUP: group})
    elif classroom != "":
        if day != "":
            value = collection.find({FD_CLASSROOM: classroom, FD_DAY: day})
        else:
            value = collection.find({FD_CLASSROOM: classroom})
    elif day != "":
        value = collection.find({FD_DAY: day})

    client.close()
    return dumps(value)


@app.route("/api_test", methods=['POST'])
def api_test():
    json_data = request.get_json(force=True)
    print(json_data['id'])
    print(json_data['name'])
    return jsonify(json_data)


if __name__ == '__main__':
    app.run(port=7770)
