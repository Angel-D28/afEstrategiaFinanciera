import { createContext, useContext, useEffect, useState } from 'react'
import { jwtDecode } from 'jwt-decode'
import * as authService from '../api/authService'

const AuthContext = createContext(null)

const TOKEN_KEY = 'af_token'
const USER_KEY = 'af_user'

function readStoredSession() {
  const token = localStorage.getItem(TOKEN_KEY)
  const rawUser = localStorage.getItem(USER_KEY)
  if (!token || !rawUser) return { token: null, user: null }

  try {
    const decoded = jwtDecode(token)
    const isExpired = decoded.exp && decoded.exp * 1000 < Date.now()
    if (isExpired) {
      localStorage.removeItem(TOKEN_KEY)
      localStorage.removeItem(USER_KEY)
      return { token: null, user: null }
    }
    return { token, user: JSON.parse(rawUser) }
  } catch {
    return { token: null, user: null }
  }
}

export function AuthProvider({ children }) {
  const [session, setSession] = useState(() => readStoredSession())
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState(null)

  useEffect(() => {
    // Revalida la sesión almacenada al montar (por si expiró entre visitas).
    setSession(readStoredSession())
  }, [])

  async function handleLogin(email, password) {
    setLoading(true)
    setError(null)
    try {
      const data = await authService.login(email, password)
      // El backend devuelve la llave como "Role" (con mayúscula) en vez
      // de "role". Leemos ambas variantes por robustez, mientras se
      // decide si el DTO del backend se corrige a "role" en minúscula.
      const role = data.role ?? data.Role
      const user = { name: data.name, role, email }
      localStorage.setItem(TOKEN_KEY, data.token)
      localStorage.setItem(USER_KEY, JSON.stringify(user))
      setSession({ token: data.token, user })
      return { ok: true }
    } catch (err) {
      // Si err.response no existe, la petición nunca llegó a completarse
      // en el navegador (CORS bloqueado, backend caído, sin red). Eso es
      // un problema distinto a "credenciales incorrectas" y merece un
      // mensaje distinto para no confundir al usuario.
      const message = err.response
        ? err.response.data?.message || 'Correo o contraseña incorrectos.'
        : 'No se pudo conectar con el servidor. Verifica que el backend esté corriendo y que CORS lo permita.'
      setError(message)
      return { ok: false, message }
    } finally {
      setLoading(false)
    }
  }

  function handleLogout() {
    localStorage.removeItem(TOKEN_KEY)
    localStorage.removeItem(USER_KEY)
    setSession({ token: null, user: null })
  }

  const value = {
    token: session.token,
    user: session.user,
    isAuthenticated: Boolean(session.token),
    role: session.user?.role ?? null,
    loading,
    error,
    login: handleLogin,
    logout: handleLogout,
  }

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
}

export function useAuth() {
  const ctx = useContext(AuthContext)
  if (!ctx) throw new Error('useAuth debe usarse dentro de <AuthProvider>')
  return ctx
}
