import re
import requests
from pymongo import MongoClient


def parse(text):
    terminal = text.find("<h4>")
    result = []
    length = len(text)
    index = text.find("</i>", 0)
    while index < terminal and index != -1:
        p = text.find("</li>", index, length)
        result.append(text[index+4:p])
        index = text.find("</i>", p, length)
    return result

#website url
url = "https://www.flipkey.com"


page = requests.get(url)

# Gathering links for top destinations
top_destinations_urls = re.findall(r'/sr/[a-z-]*/', page.text, flags=0)
top_destinations = re.findall(r'<div class="caption">([\sa-zA-Z"]*)<span class="count">', page.text, flags=0)
number_of_top_destinations = len(top_destinations_urls)
for i in range(number_of_top_destinations):
    top_destinations_urls[i] = url+top_destinations_urls[i]

# Gathering links for rentals
rental_units_urls = []
for i in range(number_of_top_destinations):
    page = requests.get(top_destinations_urls[i])
    rental_units_urls.append(re.findall(r'data-rental-unit-url="([a-z0-9:\./]*)"', page.text, flags=0))

# Gathering data
data = []
number_of_units = len(rental_units_urls[0])
for i in range(number_of_top_destinations):
    print("Working on " + top_destinations[i].strip() + "...")
    for j in range(number_of_units):
        print("Working on unit number: " + str(j))
        page = requests.get(rental_units_urls[i][j])
        name = re.findall(r'<h1 itemprop="name">([\s\S]*)</h1>', page.text, flags=0)
        name = name[0].strip()
        guest_number = re.findall(r'data-sleeps-max="([\d]+)"', page.text, flags=0)
        guest_number = int(guest_number[0])
        price = re.findall(r'<span class="from">From</span>[\s]*<strong>([\s\d$]*)</strong>', page.text, flags=0)
        price = price[0].strip()
        price = int(price[1:])
        amenities = re.findall(r'General</h4>[\s\S]*class="faqs-content"', page.text, flags=0)
        if len(amenities) > 0:
            amenities = parse(amenities[0])
        data.append({'destination': top_destinations[i].strip(), 'name': name, 'guest_number': guest_number,
                     'price': price, 'amenities': amenities})

# Save data to Mongo
client = MongoClient()
db = client["flip_key"]
collection = db["rentals"]
for i in range(len(data)):
    result = collection.insert_one(data[i])
