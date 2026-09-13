import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import * as authService from '../api/authService'
import { FormField, TextInput } from '../components/FormField'

export function Register() {
  const navigate = useNavigate()
  const [form, setForm] = useState({ name: '', email: '', password: '' })
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState(null)
  const [success, setSuccess] = useState(false)

  function update(field) {
    return (e) => setForm((f) => ({ ...f, [field]: e.target.value }))
  }

  async function handleSubmit(e) {
    e.preventDefault()
    setLoading(true)
    setError(null)
    try {
      await authService.register(form)
      setSuccess(true)
      setTimeout(() => navigate('/login'), 1500)
    } catch (err) {
      const message =
        err.response?.data?.message ||
        'No pudimos crear tu cuenta. Verifica los datos e intenta de nuevo.'
      setError(message)
    } finally {
      setLoading(false)
    }
  }

  return (
    <section className="mx-auto max-w-sm py-8">
      <p className="font-display text-3xl text-ink">Crear cuenta</p>
      <p className="mt-2 text-sm text-ink/70">
        Regístrate para elegir un plan de asesoría financiera.
      </p>

      {success ? (
        <p className="mt-8 border border-success/30 bg-success-bg px-3 py-2 text-sm text-success">
          Cuenta creada. Redirigiendo a inicio de sesión…
        </p>
      ) : (
        <form onSubmit={handleSubmit} className="mt-8 space-y-5">
          <FormField label="Nombre completo">
            <TextInput
              required
              value={form.name}
              onChange={update('name')}
              placeholder="Tu nombre"
            />
          </FormField>

          <FormField label="Correo electrónico">
            <TextInput
              type="email"
              required
              value={form.email}
              onChange={update('email')}
              placeholder="tucorreo@ejemplo.com"
            />
          </FormField>

          <FormField label="Contraseña">
            <TextInput
              type="password"
              required
              minLength={8}
              value={form.password}
              onChange={update('password')}
              placeholder="Mínimo 8 caracteres"
            />
          </FormField>

          {error && (
            <p className="border border-danger/30 bg-danger-bg px-3 py-2 text-sm text-danger">
              {error}
            </p>
          )}

          <button
            type="submit"
            disabled={loading}
            className="w-full rounded-sm bg-primary px-4 py-2.5 text-ink hover:bg-primary-dark disabled:opacity-60"
          >
            {loading ? 'Creando cuenta…' : 'Crear cuenta'}
          </button>
        </form>
      )}

      <p className="mt-6 text-sm text-ink/70">
        ¿Ya tienes cuenta?{' '}
        <Link to="/login" className="text-accent hover:underline">
          Ingresa aquí
        </Link>
      </p>
    </section>
  )
}
