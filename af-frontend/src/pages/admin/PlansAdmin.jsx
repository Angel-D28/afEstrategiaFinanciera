import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { getAllPlans } from '../../api/planService'

const currencyFormatter = new Intl.NumberFormat('es-CO', {
  style: 'currency',
  currency: 'COP',
  maximumFractionDigits: 0,
})

// Colores de estado: reutilizamos los tokens de la marca en vez de
// inventar colores nuevos, para que las etiquetas se sientan parte
// del mismo sistema visual.
const statusStyles = {
  ACTIVE: 'bg-success-bg text-success',
  INACTIVE: 'bg-danger-bg text-danger',
  DRAFT: 'bg-paper-dim text-sage',
}

export function PlansAdmin() {
  const [plans, setPlans] = useState([])
  const [status, setStatus] = useState('loading') // loading | ready | error

  useEffect(() => {
    getAllPlans()
      .then((data) => {
        setPlans(data)
        setStatus('ready')
      })
      .catch(() => setStatus('error'))
  }, [])

  return (
    <section className="py-4">
      <div className="flex items-center justify-between">
        <p className="font-display text-3xl text-ink">Planes</p>
        <Link
          to="/admin/planes/nuevo"
          className="rounded-sm bg-primary px-4 py-2 text-sm text-ink hover:bg-primary-dark"
        >
          Nuevo plan
        </Link>
      </div>
      <p className="mt-2 text-ink/70">
        Todos los planes registrados, activos e inactivos.
      </p>

      {status === 'loading' && (
        <p className="mt-8 text-sage">Cargando planes…</p>
      )}

      {status === 'error' && (
        <p className="mt-8 border border-danger/30 bg-danger-bg px-3 py-2 text-sm text-danger">
          No pudimos cargar los planes. Revisa que tu sesión tenga rol ADMIN
          y que el backend esté corriendo.
        </p>
      )}

      {status === 'ready' && (
        <div className="mt-8 overflow-x-auto border border-line">
          <table className="w-full text-left text-sm">
            <thead>
              <tr className="border-b border-line text-sage">
                <th className="px-4 py-3 font-medium">Nombre</th>
                <th className="px-4 py-3 font-medium">Precio</th>
                <th className="px-4 py-3 font-medium">Duración</th>
                <th className="px-4 py-3 font-medium">Estado</th>
              </tr>
            </thead>
            <tbody>
              {plans.length === 0 && (
                <tr>
                  <td colSpan={4} className="px-4 py-6 text-center text-sage">
                    Todavía no hay planes creados.
                  </td>
                </tr>
              )}
              {plans.map((plan) => (
                <tr key={plan.id} className="border-b border-line last:border-0">
                  <td className="px-4 py-3 text-ink">{plan.name}</td>
                  <td className="px-4 py-3 text-ink/80">
                    {currencyFormatter.format(plan.price)}
                  </td>
                  <td className="px-4 py-3 text-ink/80">
                    {plan.durationMonths}{' '}
                    {plan.durationMonths === 1 ? 'mes' : 'meses'}
                  </td>
                  <td className="px-4 py-3">
                    <span
                      className={`rounded-sm px-2 py-1 text-xs font-medium ${
                        statusStyles[plan.status] ?? 'bg-paper-dim text-sage'
                      }`}
                    >
                      {plan.status}
                    </span>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </section>
  )
}
