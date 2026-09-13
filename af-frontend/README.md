# AF Estrategia Financiera — Frontend

Frontend en React + Vite + Tailwind CSS v4 para la plataforma AF Estrategia
Financiera, consumiendo la API REST documentada del backend (Spring Boot,
arquitectura hexagonal, JWT).

## Cómo correrlo

```bash
npm install
npm run dev
```

La app queda disponible en `http://localhost:5173`.

Asegúrate de que el backend esté corriendo en `http://localhost:8080`
(o ajusta `VITE_API_URL` en `.env`) y que `FRONTEND_URL` en el backend
apunte a `http://localhost:5173` para que CORS lo permita.

## Estructura

```
src/
├── api/            # axios client + servicios por dominio (auth, planes...)
├── context/        # AuthContext: sesión, JWT decodificado, rol
├── routes/         # ProtectedRoute (guard por rol)
├── components/     # Navbar, Layout, inputs reutilizables
└── pages/
    ├── admin/      # vistas exclusivas ADMIN/AGENT
    └── client/     # vistas del cliente autenticado
```

## Ya implementado

- Login y registro conectados a `/api/auth/login` y `/api/users/register`.
- JWT guardado en `localStorage`, decodificado con `jwt-decode` para
  detectar expiración y leer el rol.
- Interceptor de Axios que agrega `Authorization: Bearer <token>` a cada
  petición y cierra sesión automáticamente ante un 401.
- Rutas protegidas por rol (`ProtectedRoute`) para `/cuenta` (cualquier
  usuario autenticado) y `/admin` (ADMIN/AGENT).
- Página pública de planes (`/planes`) consumiendo `GET /api/plans/active`.

## Siguientes pasos sugeridos

1. `/cuenta`: consumir `GET /api/subscriptions/my` y mostrar plan activo,
   estado y pagos del cliente.
2. `/admin`: CRUD de planes (`POST/PUT/PATCH /api/plans`), listado y
   filtro de usuarios (`GET /api/users`, `/api/users/role/{role}`).
3. Flujo de suscripción: `POST /api/subscriptions` desde la página de
   planes cuando el usuario hace clic en "Elegir este plan".
4. Registro de pagos desde el panel ADMIN (`POST /api/payments`).

## Identidad visual

Paleta y tipografía definidas en `src/index.css` (tokens `@theme` de
Tailwind v4): verde profundo (`--color-primary`) y dorado apagado
(`--color-accent`) sobre fondo papel, con `Fraunces` para títulos y
`IBM Plex Sans` para el resto de la interfaz.
