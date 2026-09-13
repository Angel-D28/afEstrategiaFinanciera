import { Link, useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'

export function Navbar() {
  const { isAuthenticated, user, role, logout } = useAuth()
  const navigate = useNavigate()

  function handleLogout() {
    logout()
    navigate('/login')
  }

  const dashboardPath =
    role === 'ADMIN' ? '/admin' : role === 'AGENT' ? '/admin' : '/cuenta'

  return (
    <header className="border-b border-line bg-paper">
      <div className="mx-auto flex max-w-5xl items-center justify-between px-6 py-4">
        <Link to="/" className="font-display text-xl tracking-tight text-ink">
          AF Estrategia Financiera
        </Link>

        <nav className="flex items-center gap-6 text-sm">
          <Link to="/planes" className="text-ink/80 hover:text-accent">
            Planes
          </Link>

          {isAuthenticated ? (
            <>
              <Link to={dashboardPath} className="text-ink/80 hover:text-accent">
                Mi cuenta
              </Link>
              <span className="hidden text-sage sm:inline">{user?.name}</span>
              <button
                onClick={handleLogout}
                className="rounded-sm border border-line px-3 py-1.5 text-ink hover:border-accent hover:text-accent"
              >
                Cerrar sesión
              </button>
            </>
          ) : (
            <>
              <Link to="/login" className="text-ink/80 hover:text-accent">
                Ingresar
              </Link>
              <Link
                to="/registro"
                className="rounded-sm bg-primary px-3 py-1.5 text-ink hover:bg-primary-dark"
              >
                Crear cuenta
              </Link>
            </>
          )}
        </nav>
      </div>
    </header>
  )
}
