from flask import Flask, request, render_template, flash, redirect, url_for
from wtforms import Form, StringField, TextAreaField
from pymongo import MongoClient

INDEX = 0
FETCH_NUMBER = 20

app = Flask(__name__)

@app.route("/")
def hello():
	client = MongoClient()
	db = client["flip_key"]
	collection = db["rentals"]
	data = collection.find().max_scan(FETCH_NUMBER)
	global INDEX
	INDEX = INDEX + FETCH_NUMBER
	client.close()
	return render_template('home_page.html', rentals = data)

class AddForm(Form):
	destination = StringField('Destination')
	name = StringField('Name')
	sleeps = StringField('Sleeps')
	price = StringField('Price')
	amenities = TextAreaField('amenitis')

@app.route("/add_rental", methods=['GET', 'POST'])
def add_rental():
	form = AddForm(request.form)
	if request.method == 'POST':
		destination = form.destination.data
		name = form.name.data
		sleeps = form.sleeps.data
		price = form.price.data
		amenities = form.amenities.data
		amenities = amenities.split(sep=",")
		amenities = [a.strip() for a in amenities]
		data = {'destination': destination, 'name': name, 'guest_number': sleeps, 'price': price, 'amenities': amenities}
		client = MongoClient()
		db = client["flip_key"]
		collection = db["rentals"]
		collection.insert_one(data)
		client.close()
		return redirect(url_for('hello'))
	return render_template('add_rental.html', form=form)

@app.route("/edit_rental/<string:name>", methods=['GET', 'POST'])
def edit_rental(name):
	client = MongoClient()
	db = client["flip_key"]
	collection = db["rentals"]
	if request.method == 'POST':
		destination = request.form["destination"]
		name = request.form["name"]
		sleeps = request.form["sleeps"]
		price = request.form["price"]
		amenities = request.form["amenities"]
		amenities = amenities.split(sep=",")
		amenities = [a.strip() for a in amenities]
		data = {'destination': destination, 'name': name, 'guest_number': sleeps, 'price': price, 'amenities': amenities}
		collection.update({'name': name}, data)
		client.close()
		return redirect(url_for('hello'))
	rental = collection.find_one({'name': name})
	if rental:
		form = AddForm(request.form)
		form.destination.data = rental['destination']
		form.name.data = rental["name"]
		form.sleeps.data = rental["guest_number"]
		form.price.data = rental["price"]
		form.amenities.data = rental["amenities"]
	client.close()
	return render_template('add_rental.html', form=form)

@app.route("/delete/<string:name>", methods=['POST'])
def delete(name):
	client = MongoClient()
	db = client["flip_key"]
	collection = db["rentals"]
	collection.remove({'name': name})
	client.close()
	return redirect(url_for('hello'))

@app.route("/fetch", methods=['GET', 'POST'])
def fetch():
	global INDEX
	fetch_num = request.form["fetch"]
	FETCH_NUMBER = int(fetch_num)
	client = MongoClient()
	db = client["flip_key"]
	collection = db["rentals"]
	data = collection.find().skip(INDEX).max_scan(INDEX+FETCH_NUMBER)
	INDEX = INDEX + FETCH_NUMBER
	client.close()
	return render_template('home_page.html', rentals = data)

@app.route("/reload")
def reload():
	global INDEX
	global FETCH_NUMBER
	INDEX = 0
	FETCH_NUMBER = 20
	client = MongoClient()
	db = client["flip_key"]
	collection = db["rentals"]
	data = collection.find().max_scan(FETCH_NUMBER)
	INDEX = INDEX + FETCH_NUMBER
	client.close()
	return render_template('home_page.html', rentals = data)

@app.route("/find", methods=['POST'])
def find():
	find_col = request.form["find_col"]
	value = request.form["find"]
	asds = request.form["order"]
	client = MongoClient()
	db = client["flip_key"]
	collection = db["rentals"]
	if find_col == "price" or find_col == "guest_number":
		if value.startswith('>'):
			data = collection.find({find_col:{"$gt":int(value[1:])}}).sort([(find_col,int(asds))])
		elif value.startswith('<'):
			data = collection.find({find_col: {"$lt":int(value[1:])}}).sort([(find_col,int(asds))])
		else:
			data = collection.find({find_col: int(value)}).sort([(find_col,int(asds))])
	else:
		if find_col == "amenities":
			data = collection.find({find_col: {"$elemMatch": {'$regex': value}}}).sort([(find_col,int(asds))])
		elif value.startswith('^') or value.endswith('$'):
			data = collection.find({find_col: {'$regex': value}}).sort([(find_col,int(asds))])
		else:
			data = collection.find({find_col: value}).sort([(find_col,int(asds))])
	client.close()
	return render_template('home_page.html', rentals = data)

if __name__ == "__main__":
	app.secret_key='flip_key'
	app.run(debug=True)