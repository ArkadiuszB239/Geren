import datetime
import os.path

from google.auth.transport.requests import Request
from google.oauth2.credentials import Credentials
from google_auth_oauthlib.flow import InstalledAppFlow
from googleapiclient.discovery import build
from googleapiclient.errors import HttpError

from faker import Faker

SCOPES = ["https://www.googleapis.com/auth/calendar"]


def get_creds():
    creds = None
    if os.path.exists("./../../tokens/token.json"):
        creds = Credentials.from_authorized_user_file("./../../tokens/token.json", SCOPES)

    if not creds or not creds.valid:
        if creds and creds.expired and creds.refresh_token:
            creds.refresh(Request())
        else:
            flow = InstalledAppFlow.from_client_secrets_file(
                "./../../google-calendar-api/src/main/resources/credentials.json",
                SCOPES
            )
            creds = flow.run_local_server(port=0)

        with open("../../tokens/token.json", "w") as token:
            token.write(creds.to_json())
    return creds


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
    creds = get_creds()
    try:
        service = build("calendar", "v3", credentials=creds)

        event = service.events().insert(calendarId="primary", body=get_event(title, description, date)).execute()
        print('Event created: %s' % (event.get('htmlLink')))

    except HttpError as error:
        print(f"An error occurred: {error}")


def get_events(day):
    creds = get_creds()

    try:
        service = build("calendar", "v3", credentials=creds)

        print("Getting events from provided day " + str(day))
        events_result = (
            service.events()
            .list(
                calendarId="primary",
                timeMin=datetime.datetime.combine(day, datetime.time.min).isoformat() + "Z",
                singleEvents=True,
                orderBy="startTime",
            )
            .execute()
        )
        events = events_result.get("items", [])

        if not events:
            print("No upcoming events found.")
            return

        # Prints the start and name of the next 10 events
        for event in events:
            start = event["start"].get("dateTime", event["start"].get("date"))
            print(start, event["summary"])

    except HttpError as error:
        print(f"An error occurred: {error}")


if __name__ == "__main__":
    fake = Faker("pl_PL")
    get_events(datetime.datetime.now() - datetime.timedelta(days=2))
    for i in range(10):
        create_event(
            fake.name(),
            fake.phone_number(),
            datetime.datetime.now().replace(hour=8, minute=0) + datetime.timedelta(hours=i)
        )
