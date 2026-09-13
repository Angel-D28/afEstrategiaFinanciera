import { Link } from 'react-router-dom'

export function NotFound() {
  return (
    <section className="py-16 text-center">
      <p className="font-display text-4xl text-ink">Página no encontrada</p>
      <p className="mt-2 text-ink/70">
        La ruta que buscas no existe o fue movida.
      </p>
      <Link to="/" className="mt-6 inline-block text-accent hover:underline">
        Volver al inicio
      </Link>
    </section>
  )
}
