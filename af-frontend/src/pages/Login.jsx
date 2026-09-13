import { useState } from 'react'
import { Link, useLocation, useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'
import { FormField, TextInput } from '../components/FormField'

export function Login() {
  const { login, loading, error } = useAuth()
  const navigate = useNavigate()
  const location = useLocation()

  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')

  async function handleSubmit(e) {
    e.preventDefault()
    const result = await login(email, password)
    if (result.ok) {
      const redirectTo = location.state?.from?.pathname || '/'
      navigate(redirectTo, { replace: true })
    }
  }

  return (
    <section className="mx-auto max-w-sm py-8">
      <p className="font-display text-3xl text-ink">Ingresar</p>
      <p className="mt-2 text-sm text-ink/70">
        Accede a tu cuenta para ver tu plan y tus pagos.
      </p>

      <form onSubmit={handleSubmit} className="mt-8 space-y-5">
        <FormField label="Correo electrónico">
          <TextInput
            type="email"
            required
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            placeholder="tucorreo@ejemplo.com"
          />
        </FormField>

        <FormField label="Contraseña">
          <TextInput
            type="password"
            required
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            placeholder="••••••••"
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
          {loading ? 'Ingresando…' : 'Ingresar'}
        </button>
      </form>

      <p className="mt-6 text-sm text-ink/70">
        ¿No tienes cuenta?{' '}
        <Link to="/registro" className="text-accent hover:underline">
          Regístrate
        </Link>
      </p>
    </section>
  )
}
