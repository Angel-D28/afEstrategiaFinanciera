import { Link } from 'react-router-dom'
import { useAuth } from '../../context/AuthContext'

const sections = [
  { title: 'Usuarios', to: null },
  { title: 'Planes', to: '/admin/planes' },
  { title: 'Suscripciones y pagos', to: null },
]

export function AdminDashboard() {
  const { user, role } = useAuth()

  return (
    <section className="py-4">
      <p className="font-display text-3xl text-ink">Panel de administración</p>
      <p className="mt-2 text-ink/70">
        Sesión de {user?.name} ({role}).
      </p>

      <div className="mt-8 grid gap-4 sm:grid-cols-3">
        {sections.map(({ title, to }) => {
          const content = (
            <>
              <p className="font-display text-lg text-ink">{title}</p>
              <p className="mt-2 text-sm text-sage">
                {to ? 'Ver y gestionar' : 'Pendiente de construir'}
              </p>
            </>
          )
          return to ? (
            <Link
              key={title}
              to={to}
              className="border border-line bg-paper-dim p-6 hover:border-accent"
            >
              {content}
            </Link>
          ) : (
            <div key={title} className="border border-line bg-paper-dim p-6">
              {content}
            </div>
          )
        })}
      </div>
    </section>
  )
}
