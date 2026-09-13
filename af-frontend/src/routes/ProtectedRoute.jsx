import { Navigate, Outlet, useLocation } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'

/**
 * Protege rutas que requieren sesión activa, y opcionalmente un rol
 * específico (ADMIN, AGENT, CLIENT). Si no hay sesión, redirige a /login
 * conservando la ruta de origen para volver tras iniciar sesión.
 */
export function ProtectedRoute({ allowedRoles }) {
  const { isAuthenticated, role } = useAuth()
  const location = useLocation()

  if (!isAuthenticated) {
    return <Navigate to="/login" state={{ from: location }} replace />
  }

  if (allowedRoles && !allowedRoles.includes(role)) {
    return <Navigate to="/" replace />
  }

  return <Outlet />
}
