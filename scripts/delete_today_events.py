import datetime

from googleapiclient.discovery import build
from googleapiclient.errors import HttpError

import credentials


def get_events(day):
    creds = credentials.get_creds()

    try:
        service = build("calendar", "v3", credentials=creds)

        print("Getting events from provided day " + str(day))
        events_result = (
            service.events()
            .list(
                calendarId="primary",
                timeMin=datetime.datetime.combine(day, datetime.time.min).isoformat() + "Z",
                timeMax=datetime.datetime.combine(day, datetime.time.max).isoformat() + "Z",
                singleEvents=True,
                orderBy="startTime",
            )
            .execute()
        )
        events = events_result.get("items", [])

        if not events:
            print("No upcoming events found.")
            return

        return events

    except HttpError as error:
        print(f"An error occurred: {error}")


def delete_event(event_id):
    creds = credentials.get_creds()
    try:
        service = build("calendar", "v3", credentials=creds)
        service.events().delete(calendarId='primary', eventId=event_id).execute()

    except HttpError as error:
        print(f"An error occurred: {error}")


events = get_events(datetime.datetime.now().replace(hour=0))
for event in events:
    delete_event(event["id"])
    print("Deleted event with id: " + event["id"] + " for: " + event["summary"])
