import { useEffect, useState } from 'react'
import { getActivePlans } from '../api/planService'

const currencyFormatter = new Intl.NumberFormat('es-CO', {
  style: 'currency',
  currency: 'COP',
  maximumFractionDigits: 0,
})

export function Plans() {
  const [plans, setPlans] = useState([])
  const [status, setStatus] = useState('loading') // loading | ready | error

  useEffect(() => {
    getActivePlans()
      .then((data) => {
        setPlans(data)
        setStatus('ready')
      })
      .catch(() => setStatus('error'))
  }, [])

  return (
    <section className="py-4">
      <p className="font-display text-3xl text-ink">Planes de asesoría</p>
      <p className="mt-2 max-w-xl text-ink/70">
        Elige el plan que mejor se ajuste a tu meta financiera actual.
      </p>

      {status === 'loading' && (
        <p className="mt-8 text-sage">Cargando planes…</p>
      )}

      {status === 'error' && (
        <p className="mt-8 border border-danger/30 bg-danger-bg px-3 py-2 text-sm text-danger">
          No pudimos cargar los planes. Verifica que el backend esté
          corriendo en {import.meta.env.VITE_API_URL || 'http://localhost:8080'}.
        </p>
      )}

      {status === 'ready' && plans.length === 0 && (
        <p className="mt-8 text-sage">
          Todavía no hay planes activos publicados.
        </p>
      )}

      {status === 'ready' && plans.length > 0 && (
        <div className="mt-8 grid gap-6 sm:grid-cols-2 lg:grid-cols-3">
          {plans.map((plan) => (
            <article
              key={plan.id}
              className="flex flex-col border border-line bg-paper p-6"
            >
              <p className="font-display text-xl text-ink">{plan.name}</p>
              <p className="mt-2 text-sm text-ink/70">{plan.description}</p>

              <p className="mt-6 font-display text-2xl text-accent">
                {currencyFormatter.format(plan.price)}
                <span className="ml-1 text-sm font-sans text-sage">
                  / {plan.durationMonths}{' '}
                  {plan.durationMonths === 1 ? 'mes' : 'meses'}
                </span>
              </p>

              {Array.isArray(plan.features) && plan.features.length > 0 && (
                <ul className="mt-4 space-y-1.5 text-sm text-ink/80">
                  {plan.features.map((feature) => (
                    <li key={feature} className="flex gap-2">
                      <span className="text-accent">—</span>
                      {feature}
                    </li>
                  ))}
                </ul>
              )}

              <button className="mt-auto pt-6 text-left text-sm font-medium text-accent hover:underline">
                Elegir este plan
              </button>
            </article>
          ))}
        </div>
      )}
    </section>
  )
}
