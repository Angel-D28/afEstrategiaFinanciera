import { apiClient } from './client'

// POST /api/auth/login -> { token, name, email, Role } (nota: "Role" con
// mayúscula, según la respuesta real observada del backend)
export async function login(email, password) {
  const { data } = await apiClient.post('/api/auth/login', { email, password })
  return data
}

// POST /api/users/register -> registro público de CLIENT
export async function register({ name, email, password }) {
  const { data } = await apiClient.post('/api/users/register', {
    name,
    email,
    password,
  })
  return data
}

// GET /api/users/me -> perfil del usuario autenticado
export async function getMyProfile() {
  const { data } = await apiClient.get('/api/users/me')
  return data
}
