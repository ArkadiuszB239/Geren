import os.path

from google.auth.transport.requests import Request
from google.oauth2.credentials import Credentials
from google_auth_oauthlib.flow import InstalledAppFlow
from googleapiclient.discovery import build

SCOPES = ["https://www.googleapis.com/auth/calendar"]


def get_creds():
    creds = None
    if os.path.exists("../tokens/token.json"):
        creds = Credentials.from_authorized_user_file("../tokens/token.json", SCOPES)

    if not creds or not creds.valid:
        if creds and creds.expired and creds.refresh_token:
            creds.refresh(Request())
        else:
            flow = InstalledAppFlow.from_client_secrets_file(
                "./../google-calendar-api/src/main/resources/credentials.json",
                SCOPES
            )
            creds = flow.run_local_server(port=0)

        with open("../tokens/token.json", "w") as token:
            token.write(creds.to_json())
    return creds


def get_calendar_api_service():
    creds = get_creds()
    service = build("calendar", "v3", credentials=creds)
    return service


def get_calendar_id(calendar_name):
    calendar_list = get_calendar_api_service().calendarList().list().execute()
    for calendar_list_entry in calendar_list['items']:
        if calendar_list_entry['summary'] == calendar_name:
            return calendar_list_entry['id']
    raise KeyError(f'Calendar id for {calendar_name} not found')
