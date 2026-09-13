export function FormField({ label, error, children }) {
  return (
    <label className="block">
      <span className="mb-1.5 block text-sm text-ink/80">{label}</span>
      {children}
      {error && <span className="mt-1 block text-sm text-danger">{error}</span>}
    </label>
  )
}

const baseInputStyles =
  'w-full rounded-sm border border-line bg-paper px-3 py-2 text-ink outline-none transition-colors placeholder:text-sage-light focus:border-primary'

export function TextInput(props) {
  return <input {...props} className={`${baseInputStyles} ${props.className ?? ''}`} />
}
