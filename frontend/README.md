# Graduation Day Invitation

A Next.js graduation ceremony invitation for 26 September 2026 at Hanoi University of Science and Technology (HUST).

## Features

- Graduation hero with a lookup field for guest name, phone number, or student ID
- Personal invitation letter fetched from the backend
- Countdown to 7:00 AM on graduation day
- HUST event details with map and calendar actions
- Responsive presentation for desktop and mobile

## Backend integration

Set `NEXT_PUBLIC_LETTER_API_URL` to the backend lookup endpoint. The frontend sends:

```json
{ "identifier": "guest name, phone number, or student ID" }
```

The endpoint should return either a letter object directly, or an object under `letter` or `data`:

```json
{
  "recipient": "Guest name",
  "greeting": "Dear Guest name,",
  "body": "Your invitation letter...",
  "signature": "Your Name"
}
```

`body` can also be returned as `message` or `content`. Without the environment variable, the frontend uses `/api/letters/lookup`.

## Development

```bash
npm install
npm run dev
```

Update event details and the graduate's name in [graduation-config.ts](src/config/graduation-config.ts).

## Deployment

The static export uses `/graduation-invitation` as its production base path. Run `npm run build` to generate the export.
