import { BrowserRouter, Routes, Route } from 'react-router-dom'
import { AuthProvider } from './context/AuthContext'
import { ProtectedRoute } from './routes/ProtectedRoute'
import { Layout } from './components/Layout'

import { Home } from './pages/Home'
import { Login } from './pages/Login'
import { Register } from './pages/Register'
import { Plans } from './pages/Plans'
import { NotFound } from './pages/NotFound'
import { MyAccount } from './pages/client/MyAccount'
import { AdminDashboard } from './pages/admin/AdminDashboard'
import { PlansAdmin } from './pages/admin/PlansAdmin'
import { PlanForm } from './pages/admin/PlanForm'

function App() {
  return (
    <BrowserRouter>
      <AuthProvider>
        <Routes>
          <Route element={<Layout />}>
            {/* Rutas públicas */}
            <Route path="/" element={<Home />} />
            <Route path="/planes" element={<Plans />} />
            <Route path="/login" element={<Login />} />
            <Route path="/registro" element={<Register />} />

            {/* Rutas para cualquier usuario autenticado */}
            <Route element={<ProtectedRoute allowedRoles={['CLIENT', 'AGENT', 'ADMIN']} />}>
              <Route path="/cuenta" element={<MyAccount />} />
            </Route>

            {/* Rutas exclusivas de administración */}
            <Route element={<ProtectedRoute allowedRoles={['ADMIN', 'AGENT']} />}>
              <Route path="/admin" element={<AdminDashboard />} />
              <Route path="/admin/planes" element={<PlansAdmin />} />
              <Route path="/admin/planes/nuevo" element={<PlanForm />} />
              <Route path="/admin/planes/:id/editar" element={<PlanForm />} />
            </Route>

            <Route path="*" element={<NotFound />} />
          </Route>
        </Routes>
      </AuthProvider>
    </BrowserRouter>
  )
}

export default App
