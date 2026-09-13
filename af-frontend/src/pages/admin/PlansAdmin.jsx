import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { deletePlan, getAllPlans, updatePlanStatus } from '../../api/planService'

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
  // Guardamos el id del plan sobre el que hay una acción en curso, para
  // deshabilitar solo sus botones (no toda la tabla) mientras responde.
  const [actioningId, setActioningId] = useState(null)
  const [actionError, setActionError] = useState(null)

  function loadPlans() {
    setStatus('loading')
    getAllPlans()
      .then((data) => {
        setPlans(data)
        setStatus('ready')
      })
      .catch(() => setStatus('error'))
  }

  useEffect(loadPlans, [])

  async function handleToggleStatus(plan) {
    const nextStatus = plan.status === 'ACTIVE' ? 'INACTIVE' : 'ACTIVE'
    setActioningId(plan.id)
    setActionError(null)
    try {
      await updatePlanStatus(plan.id, nextStatus)
      // Actualizamos solo ese plan en el estado local en vez de volver a
      // pedir la lista completa al backend: es una respuesta instantánea
      // y ya sabemos exactamente qué cambió.
      setPlans((prev) =>
        prev.map((p) => (p.id === plan.id ? { ...p, status: nextStatus } : p))
      )
    } catch {
      setActionError(`No pudimos cambiar el estado de "${plan.name}".`)
    } finally {
      setActioningId(null)
    }
  }

  async function handleDelete(plan) {
    const confirmed = window.confirm(
      `¿Desactivar el plan "${plan.name}"? Los clientes ya no podrán elegirlo, pero no se borra su historial.`
    )
    if (!confirmed) return

    setActioningId(plan.id)
    setActionError(null)
    try {
      await deletePlan(plan.id)
      setPlans((prev) =>
        prev.map((p) => (p.id === plan.id ? { ...p, status: 'INACTIVE' } : p))
      )
    } catch {
      setActionError(`No pudimos desactivar "${plan.name}".`)
    } finally {
      setActioningId(null)
    }
  }

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

      {actionError && (
        <p className="mt-4 border border-danger/30 bg-danger-bg px-3 py-2 text-sm text-danger">
          {actionError}
        </p>
      )}

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
                <th className="px-4 py-3 font-medium"></th>
              </tr>
            </thead>
            <tbody>
              {plans.length === 0 && (
                <tr>
                  <td colSpan={5} className="px-4 py-6 text-center text-sage">
                    Todavía no hay planes creados.
                  </td>
                </tr>
              )}
              {plans.map((plan) => {
                const isBusy = actioningId === plan.id
                return (
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
                    <td className="px-4 py-3">
                      <div className="flex justify-end gap-4 text-sm">
                        <Link
                          to={`/admin/planes/${plan.id}/editar`}
                          className="text-accent hover:underline"
                        >
                          Editar
                        </Link>
                        <button
                          onClick={() => handleToggleStatus(plan)}
                          disabled={isBusy}
                          className="text-ink/70 hover:text-accent disabled:opacity-50"
                        >
                          {plan.status === 'ACTIVE' ? 'Desactivar' : 'Activar'}
                        </button>
                        <button
                          onClick={() => handleDelete(plan)}
                          disabled={isBusy || plan.status === 'INACTIVE'}
                          className="text-danger hover:underline disabled:opacity-40"
                        >
                          Eliminar
                        </button>
                      </div>
                    </td>
                  </tr>
                )
              })}
            </tbody>
          </table>
        </div>
      )}
    </section>
  )
}
