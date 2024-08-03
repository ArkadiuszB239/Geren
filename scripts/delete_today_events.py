import datetime

import sys
from googleapiclient.errors import HttpError

import base_google_calendar_operations


def get_events(day, calendar_id):
    try:
        print("Getting events from provided day " + str(day))
        events_result = (
            service.events()
            .list(
                calendarId=calendar_id,
                timeMin=datetime.datetime.combine(day, datetime.time.min).isoformat() + "Z",
                timeMax=datetime.datetime.combine(day, datetime.time.max).isoformat() + "Z",
                singleEvents=True,
                orderBy="startTime",
            )
            .execute()
        )
        events_items = events_result.get("items", [])

        if not events_items:
            print("No upcoming events found.")
            return

        return events_items

    except HttpError as error:
        print(f"An error occurred: {error}")


def delete_event(calendar_id, event_id):
    try:
        service.events().delete(calendarId=calendar_id, eventId=event_id).execute()

    except HttpError as error:
        print(f"An error occurred: {error}")


service = base_google_calendar_operations.get_calendar_api_service()
for calendar_name in sys.argv[1:]:
    calendarId = base_google_calendar_operations.get_calendar_id(calendar_name)
    events = get_events(datetime.datetime.now().replace(hour=0), calendarId)
    print(f"Deleting {len(events)} events from {calendar_name}")
    for event in events:
        delete_event(calendarId, event["id"])
        print("Deleted event with id: " + event["id"] + " for: " + event["summary"])
