# Graduation Invitation

Invitation website for a HUST graduation ceremony. The repository contains a Next.js frontend, a Spring Boot REST API, and a MariaDB database. Docker Compose can run the application services together and can optionally expose them through the configured Cloudflare Tunnel.

## Stack

- Frontend: Next.js 15, React 18, TypeScript, Tailwind CSS
- Backend: Spring Boot 4.0.5, Java 21, Maven, Spring Data JPA
- Database: MariaDB
- Optional deployment: Docker Compose and Cloudflare Tunnel

## Prerequisites

Choose either the local-development prerequisites or the Docker prerequisites.

### Local development

- Node.js 20 or later and npm
- Java 21
- Docker Desktop, or a locally installed MariaDB server
- Git

### Docker deployment

- Docker Desktop with Docker Compose v2
- A valid Cloudflare Tunnel configuration and credentials if the `cloudflared` service is enabled

## Repository layout

```text
frontend/     Next.js web application
server/       Spring Boot API
docker/       Docker Compose, MariaDB data, and Cloudflare Tunnel configuration
```

Generated or local-only directories such as `frontend/node_modules`, `frontend/.next`, `server/target`, and `docker/mariadb_data` do not need to be recreated manually.

## Configuration

The Compose stack reads `docker/.env`. A fresh checkout must have that file before running Compose. Use the existing file as a template, replace its passwords and API key with deployment-specific values, and do not expose those values in documentation or source control.

Important variables:

| Variable | Used by | Description |
| --- | --- | --- |
| `MARIADB_DATABASE` | MariaDB | Database created on first startup |
| `MARIADB_USER` | MariaDB and backend | Application database user |
| `MARIADB_PASSWORD` | MariaDB and backend | Application database password |
| `MARIADB_ROOT_PASSWORD` | MariaDB | MariaDB root password |
| `DB_URL` | Backend | JDBC URL; use `mariadb_db` inside Compose and `localhost` for local backend development |
| `DB_USERNAME` / `DB_PASSWORD` | Backend | Database credentials |
| `API_SECURITY_KEY` | Backend | Value expected in the `X-API-KEY` header for protected endpoints |
| `NEXT_PUBLIC_API_URL` | Frontend | Full invitation endpoint URL, for example `http://localhost:8080/api/v1/invitation` |

The frontend reads `NEXT_PUBLIC_API_URL` when the Next.js app is built or started. Set it before `npm run dev` or pass it as a Docker build argument when building the frontend image.

## Local development

The following PowerShell workflow starts MariaDB in Docker and runs the backend and frontend from their source directories.

### 1. Start MariaDB

```powershell
cd docker
docker compose up -d mariadb
cd ..
```

The database is available on `localhost:3306`. The first startup creates the database configured by `MARIADB_DATABASE` and persists data in `docker/mariadb_data`.

### 2. Run the backend

Open a new PowerShell terminal at the repository root:

```powershell
cd server
$env:DB_URL = "jdbc:mariadb://localhost:3306/graduation?serverTimezone=Asia/Ho_Chi_Minh"
$env:DB_USERNAME = "graduation"
$env:DB_PASSWORD = "replace-with-your-MARIADB_PASSWORD"
./mvnw.cmd spring-boot:run
```

The API listens on `http://localhost:8080` by default. The backend creates or updates its JPA tables because `JPA_DDL_AUTO` defaults to `update`.

To run the backend with the Maven executable instead of the wrapper:

```powershell
mvn spring-boot:run
```

### 3. Run the frontend

Open another PowerShell terminal:

```powershell
cd frontend
npm install
$env:NEXT_PUBLIC_API_URL = "http://localhost:8080/api/v1/invitation"
npm run dev
```

Open `http://localhost:3000` in a browser. The invitation lookup sends a `GET` request with an `identifier` query parameter.

Example request:

```text
http://localhost:8080/api/v1/invitation?identifier=0981722618
```

The invitation endpoint is public. Guest, template, and configuration management endpoints require the `X-API-KEY` header configured by `API_SECURITY_KEY`.

## Run with Docker Compose

From the `docker` directory:

```powershell
cd docker
docker compose --env-file .env up -d --build mariadb backend frontend
```

View service status and logs:

```powershell
docker compose ps
docker compose logs -f backend
docker compose logs -f frontend
```

Stop the application services while keeping the database files:

```powershell
docker compose down
```

The Compose file also defines `cloudflared`. Start it only when the tunnel configuration and credentials are valid:

```powershell
docker compose --env-file .env up -d --build
docker compose logs -f cloudflared
```

The checked-in tunnel configuration routes `kvbhust.id.vn` to the frontend and `api.kvbhust.id.vn` to the backend. Update the hostnames and credentials for a different deployment.

### Docker networking notes

- Containers communicate with MariaDB using the service/container hostname `mariadb_db`, not `localhost`.
- The backend container exposes port 8080 internally. The current Compose file does not publish the backend or frontend ports to the host; access is intended to go through the Cloudflare Tunnel.
- `NEXT_PUBLIC_API_URL` is a Next.js build-time value. The current Compose file sets a runtime environment value for the frontend but does not declare a Docker build argument. For a different API URL, pass the value during the image build or update the Compose build configuration before rebuilding the frontend image.
- The current `docker/.env` sets `BACKEND_PORT=8000`, but the backend resource currently uses the default Spring Boot port 8080 because the property is named `spring.port`. The tunnel and Dockerfile correctly target port 8080.

## API overview

Base URL: `http://localhost:8080` during local development.

| Method | Endpoint | Auth | Purpose |
| --- | --- | --- | --- |
| `GET` | `/api/v1/invitation?identifier=...` | Public | Look up an invitation |
| `GET` | `/api/v1/guests` | `X-API-KEY` | List guests |
| `POST` | `/api/v1/guests` | `X-API-KEY` | Create a guest |
| `POST` | `/api/v1/guests/import` | `X-API-KEY` | Import guests |
| `GET` / `PUT` / `DELETE` | `/api/v1/guests/{id}` | `X-API-KEY` | Manage one guest |
| `GET` / `POST` | `/api/v1/templates` | `X-API-KEY` | Manage templates |
| `GET` / `PUT` / `DELETE` | `/api/v1/templates/{id}` | `X-API-KEY` | Manage one template |
| `GET` | `/api/v1/config?keys=...` | `X-API-KEY` | Read configuration |
| `POST` | `/api/v1/config` | `X-API-KEY` | Update configuration |

Example protected request:

```powershell
curl.exe -H "X-API-KEY: replace-with-your-API_SECURITY_KEY" http://localhost:8080/api/v1/guests
```

## Build and test

Frontend production build:

```powershell
cd frontend
npm run build
npm start
```

Backend tests:

```powershell
cd server
./mvnw.cmd test
```

The current backend test suite contains a Spring application context smoke test. It needs a reachable MariaDB instance and valid database environment variables unless the test configuration is changed.

## Customizing the invitation

Edit `frontend/src/config/graduation-config.ts` to change the graduate name, degree, event date, location, map link, and contact details. The lookup response is mapped in `frontend/src/services/letter-api.ts`.

## Troubleshooting

### Backend cannot connect to MariaDB

Check that MariaDB is running with `docker compose ps`, then verify that a locally running backend uses a JDBC URL containing `localhost:3306`. The hostname `mariadb_db` works only from another container on the Compose network.

### Frontend shows the default letter or cannot find the API

Verify `NEXT_PUBLIC_API_URL` is set before starting or building Next.js and points to `/api/v1/invitation`. Restart the development server after changing it.

### Protected API returns 401 or 403

Send the exact `API_SECURITY_KEY` value in the `X-API-KEY` header. Invitation lookup is public, but guest, template, and configuration routes are protected.

### Reset the local database

Stop the Compose stack, back up any needed data, and remove `docker/mariadb_data` only when a full database reset is intended. The next MariaDB startup will initialize a new database.

## License

See [frontend/LICENSE](frontend/LICENSE).