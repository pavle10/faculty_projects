import requests
from bs4 import BeautifulSoup
import re
from datetime import datetime
import smtplib
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText


USERNAME = "username"
PASSWORD = "password"
HOST = "smtp-mail.outlook.com"
TO_ADDRESS = "some_address"
SUBJECT = "Kosarkaske vesti"
B92 = "https://www.b92.net"


def get_news():
    page_url = "https://www.b92.net/sport/kosarka/vesti.php?order=hrono&nav_category=2"
    page = requests.get(page_url)

    soup = BeautifulSoup(page.text, 'html.parser')
    articles = soup.find_all('article', attrs={'class': 'item'})

    news = []
    datetime_re = re.compile(r'[0-9]+\.[0-9]+\.')
    for article in articles:
        link = article.a.get('href')
        headline = article.a.string
        date = article.find_all('span')[1].string
        date = re.search(datetime_re, date).group()
        if check_date(date):
            news.append((link, headline))

    return news


def check_date(date):
    parsed = datetime.strptime(date, '%d.%m.')
    today = datetime.now()
    if today.day == parsed.day and today.month == parsed.month:
        return True
    return False


def create_message(news):
    msg = "Danasnje vesti:\n"
    for n in news:
        msg += "Naslov: "
        msg += n[1]
        msg += "\nLink: "
        msg += B92 + n[0]
        msg += "\n\n"

    return msg


def send_email(message):
    server = smtplib.SMTP(HOST, 587)
    server.starttls()

    server.login(USERNAME, PASSWORD)

    from_address = USERNAME
    to_address = TO_ADDRESS
    email = MIMEMultipart()
    email['From'] = from_address
    email['To'] = to_address
    email['Subject'] = SUBJECT
    email.attach(MIMEText(message, 'plain'))
    email_string = email.as_string()
    server.sendmail(from_address, to_address, email_string)

    server.quit()


if __name__ == '__main__':
    print("Basketball news")
    # Scrape new news about basketball from B92 site
    print("Scraping news...")
    news = get_news()
    print("News are scraped")

    # Create message
    print("Creating message...")
    message = create_message(news)
    print("Message is created")

    # Send email with new news about basketball
    print("Sending email...")
    send_email(message)
    print("Email is sent")