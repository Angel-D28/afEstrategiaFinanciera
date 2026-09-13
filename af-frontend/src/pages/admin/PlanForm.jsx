import { useEffect, useState } from 'react'
import { useNavigate, useParams, Link } from 'react-router-dom'
import { createPlan, getPlanById, updatePlan } from '../../api/planService'
import { FormField, TextInput, Textarea } from '../../components/FormField'

const initialForm = {
  name: '',
  description: '',
  price: '',
  durationMonths: '',
  features: '',
}

// Convierte un plan traído del backend (tipos correctos: number, array)
// al formato que necesitan los <input>/<textarea> (todo string).
function planToFormValues(plan) {
  return {
    name: plan.name ?? '',
    description: plan.description ?? '',
    price: String(plan.price ?? ''),
    durationMonths: String(plan.durationMonths ?? ''),
    features: Array.isArray(plan.features) ? plan.features.join('\n') : '',
  }
}

export function PlanForm() {
  const navigate = useNavigate()
  const { id } = useParams()
  const isEditMode = Boolean(id)

  const [form, setForm] = useState(initialForm)
  // "loadingPlan" solo aplica en modo edición, mientras traemos los datos
  // existentes; "saving" aplica a ambos modos, al enviar el formulario.
  const [loadingPlan, setLoadingPlan] = useState(isEditMode)
  const [saving, setSaving] = useState(false)
  const [error, setError] = useState(null)

  useEffect(() => {
    if (!isEditMode) return

    getPlanById(id)
      .then((plan) => setForm(planToFormValues(plan)))
      .catch(() =>
        setError('No pudimos cargar los datos de este plan.')
      )
      .finally(() => setLoadingPlan(false))
  }, [id, isEditMode])

  function update(field) {
    return (e) => setForm((f) => ({ ...f, [field]: e.target.value }))
  }

  async function handleSubmit(e) {
    e.preventDefault()
    setSaving(true)
    setError(null)

    const payload = {
      name: form.name,
      description: form.description,
      price: Number(form.price),
      durationMonths: Number(form.durationMonths),
      features: form.features
        .split('\n')
        .map((line) => line.trim())
        .filter(Boolean),
    }

    try {
      if (isEditMode) {
        await updatePlan(id, payload)
      } else {
        await createPlan(payload)
      }
      navigate('/admin/planes')
    } catch (err) {
      const message =
        err.response?.data?.message ||
        `No pudimos ${isEditMode ? 'actualizar' : 'crear'} el plan. Revisa los datos e intenta de nuevo.`
      setError(message)
    } finally {
      setSaving(false)
    }
  }

  if (loadingPlan) {
    return <p className="py-8 text-sage">Cargando plan…</p>
  }

  return (
    <section className="mx-auto max-w-lg py-4">
      <p className="font-display text-3xl text-ink">
        {isEditMode ? 'Editar plan' : 'Nuevo plan'}
      </p>
      {!isEditMode && (
        <p className="mt-2 text-ink/70">
          Se crea en estado <span className="text-accent">DRAFT</span>; lo
          activas después desde la lista de planes.
        </p>
      )}

      <form onSubmit={handleSubmit} className="mt-8 space-y-5">
        <FormField label="Nombre del plan">
          <TextInput
            required
            value={form.name}
            onChange={update('name')}
            placeholder="Plan Esencial"
          />
        </FormField>

        <FormField label="Descripción">
          <Textarea
            required
            value={form.description}
            onChange={update('description')}
            placeholder="Para quién es este plan y qué resuelve"
          />
        </FormField>

        <div className="grid grid-cols-2 gap-4">
          <FormField label="Precio (COP)">
            <TextInput
              type="number"
              min="0"
              required
              value={form.price}
              onChange={update('price')}
              placeholder="150000"
            />
          </FormField>

          <FormField label="Duración (meses)">
            <TextInput
              type="number"
              min="1"
              required
              value={form.durationMonths}
              onChange={update('durationMonths')}
              placeholder="1"
            />
          </FormField>
        </div>

        <FormField label="Beneficios (uno por línea)">
          <Textarea
            value={form.features}
            onChange={update('features')}
            placeholder={'Asesoría mensual\nReporte de indicadores\nSoporte por WhatsApp'}
          />
        </FormField>

        {error && (
          <p className="border border-danger/30 bg-danger-bg px-3 py-2 text-sm text-danger">
            {error}
          </p>
        )}

        <div className="flex gap-3">
          <button
            type="submit"
            disabled={saving}
            className="rounded-sm bg-primary px-4 py-2.5 text-ink hover:bg-primary-dark disabled:opacity-60"
          >
            {saving
              ? 'Guardando…'
              : isEditMode
                ? 'Guardar cambios'
                : 'Crear plan'}
          </button>
          <Link
            to="/admin/planes"
            className="rounded-sm border border-line px-4 py-2.5 text-ink/80 hover:border-accent hover:text-accent"
          >
            Cancelar
          </Link>
        </div>
      </form>
    </section>
  )
}
