import datetime
import random

import sys
from faker import Faker
from googleapiclient.errors import HttpError

import base_google_calendar_operations


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


def create_event(calendar_id, title, description, date):
    try:
        event = service.events().insert(calendarId=calendar_id,
                                        body=get_event(title, description, date)).execute()
        print('Created event for: %s' % (event["summary"]))

    except HttpError as error:
        print(f"An error occurred: {error}")


service = base_google_calendar_operations.get_calendar_api_service()
between_events_periods = [30, 60, 90]
fake = Faker("pl_PL")
for calendar_name in sys.argv[1:]:
    calendarId = base_google_calendar_operations.get_calendar_id(calendar_name)
    dayStart = datetime.datetime.now().replace(hour=8, minute=0)
    dayEnd = datetime.datetime.now().replace(hour=17, minute=0)
    print(f"Creating events for {calendar_name}")
    while dayStart < dayEnd:
        create_event(
            calendarId,
            fake.name(),
            fake.phone_number(),
            dayStart
        )
        dayStart += datetime.timedelta(minutes=random.choice(between_events_periods))
