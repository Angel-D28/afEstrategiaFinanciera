import { useAuth } from '../../context/AuthContext'

export function MyAccount() {
  const { user } = useAuth()

  return (
    <section className="py-4">
      <p className="font-display text-3xl text-ink">Mi cuenta</p>
      <p className="mt-2 text-ink/70">Hola, {user?.name}.</p>

      <div className="mt-8 border border-line bg-paper-dim p-6">
        <p className="text-sm text-sage">
          Próximo paso: conectar aquí GET /api/subscriptions/my para mostrar
          tu plan activo, estado y pagos.
        </p>
      </div>
    </section>
  )
}
