import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import { useAuth } from './hooks/useAuth';

// Layout
import AdminLayout from './components/layout/AdminLayout';

// Páginas públicas
import LandingPage from './pages/public/LandingPage.jsx';
import LoginPage from './pages/public/LoginPage';
import RegisterPage from './pages/public/RegisterPage';

// Páginas admin
import DashboardPage from './pages/admin/DashboardPage';
import UsersPage from './pages/admin/UsersPage';
import PlansPage from './pages/admin/PlansPage';
import SubscriptionsPage from './pages/admin/SubscriptionsPage';
import PaymentsPage from './pages/admin/PaymentsPage';

// Páginas cliente
import ClientDashboard from './pages/client/ClientDashboard';
import MyPlansPage from './pages/client/MyPlansPage';
import MyPaymentsPage from './pages/client/MyPaymentsPage';

import LoadingSpinner from './components/ui/LoadingSpinner';

// Rutas protegidas
function ProtectedRoute({ children, requiredRole }) {
    const { isAuthenticated, isAdmin, loading } = useAuth();

    if (loading) return (
        <div className="min-h-screen flex items-center justify-center">
            <LoadingSpinner size="lg" />
        </div>
    );

    if (!isAuthenticated()) return <Navigate to="/login" replace />;
    if (requiredRole === 'ADMIN' && !isAdmin()) return <Navigate to="/client" replace />;

    return children;
}

function AppRoutes() {
    return (
        <Routes>
            {/* Públicas */}
            <Route path="/" element={<LandingPage />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/register" element={<RegisterPage />} />

            {/* Admin */}
            <Route path="/admin" element={
                <ProtectedRoute requiredRole="ADMIN">
                    <AdminLayout />
                </ProtectedRoute>
            }>
                <Route index element={<DashboardPage />} />
                <Route path="users" element={<UsersPage />} />
                <Route path="plans" element={<PlansPage />} />
                <Route path="subscriptions" element={<SubscriptionsPage />} />
                <Route path="payments" element={<PaymentsPage />} />
            </Route>

            {/* Cliente */}
            <Route path="/client" element={
                <ProtectedRoute>
                    <ClientDashboard />
                </ProtectedRoute>
            } />
            <Route path="/client/plans" element={
                <ProtectedRoute>
                    <MyPlansPage />
                </ProtectedRoute>
            } />
            <Route path="/client/payments" element={
                <ProtectedRoute>
                    <MyPaymentsPage />
                </ProtectedRoute>
            } />

            {/* 404 */}
            <Route path="*" element={<Navigate to="/" replace />} />
        </Routes>
    );
}

export default function App() {
    return (
        <BrowserRouter>
            <AuthProvider>
                <AppRoutes />
            </AuthProvider>
        </BrowserRouter>
    );
}