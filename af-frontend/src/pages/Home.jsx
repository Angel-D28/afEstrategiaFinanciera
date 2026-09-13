import { Link } from 'react-router-dom'

export function Home() {
  return (
    <section className="grid gap-12 py-8 md:grid-cols-[1.2fr_1fr] md:items-center">
      <div>
        <p className="font-display text-5xl leading-[1.1] text-ink">
          Un plan financiero claro,
          <br />
          construido con un asesor real.
        </p>
        <p className="mt-6 max-w-md text-ink/70">
          AF Estrategia Financiera conecta a personas con planes de asesoría
          diseñados para metas concretas: ahorro, deuda, inversión y
          protección. Sin letra pequeña, sin promesas vacías.
        </p>
        <div className="mt-8 flex gap-4">
          <Link
            to="/planes"
            className="rounded-sm bg-primary px-5 py-2.5 text-ink hover:bg-primary-dark"
          >
            Ver planes
          </Link>
          <Link
            to="/registro"
            className="rounded-sm border border-line px-5 py-2.5 text-ink hover:border-accent hover:text-accent"
          >
            Crear cuenta
          </Link>
        </div>
      </div>

      <div className="border border-line bg-paper-dim p-6">
        <p className="font-display text-lg text-ink">Cómo funciona</p>
        <ol className="mt-4 space-y-4 text-sm text-ink/80">
          <li>
            <span className="font-medium text-accent">1.</span> Eliges un
            plan según tu meta financiera.
          </li>
          <li>
            <span className="font-medium text-accent">2.</span> Un asesor
            gestiona tu suscripción y da seguimiento.
          </li>
          <li>
            <span className="font-medium text-accent">3.</span> Pagas por
            Nequi, Daviplata o transferencia, y consultas tu estado en línea.
          </li>
        </ol>
      </div>
    </section>
  )
}
