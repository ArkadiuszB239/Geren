import datetime
import random

from faker import Faker
from googleapiclient.discovery import build
from googleapiclient.errors import HttpError

import credentials


def get_event(title, description, date):
    return {
        "summary": title,
        "description": description,
        "start": {
            "dateTime": date.isoformat(),
            "timeZone": "Europe/Warsaw"
        },
        "end": {
            "dateTime": (date + datetime.timedelta(minutes=30)).isoformat(),
            "timeZone": "Europe/Warsaw"
        }
    }


def create_event(title, description, date):
    creds = credentials.get_creds()
    try:
        service = build("calendar", "v3", credentials=creds)

        event = service.events().insert(calendarId="primary", body=get_event(title, description, date)).execute()
        print('Created event for: %s' % (event["summary"]))

    except HttpError as error:
        print(f"An error occurred: {error}")


between_events_periods = [30, 60, 90]
fake = Faker("pl_PL")
day_start = datetime.datetime.now().replace(hour=8, minute=0)
day_end = datetime.datetime.now().replace(hour=17, minute=0)
while day_start < day_end:
    create_event(
        fake.name(),
        fake.phone_number(),
        day_start
    )
    day_start += datetime.timedelta(minutes=random.choice(between_events_periods))
