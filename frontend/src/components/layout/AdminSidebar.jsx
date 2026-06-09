import { NavLink } from 'react-router-dom';
import { useAuth } from '../../hooks/useAuth';
import {
    Users, Package, CreditCard, BarChart3,
    LogOut, ChevronRight, TrendingUp
} from 'lucide-react';

const links = [
    { to: '/admin', label: 'Dashboard', icon: BarChart3, end: true },
    { to: '/admin/users', label: 'Usuarios', icon: Users },
    { to: '/admin/plans', label: 'Planes', icon: Package },
    { to: '/admin/subscriptions', label: 'Suscripciones', icon: TrendingUp },
    { to: '/admin/payments', label: 'Pagos', icon: CreditCard },
];

export default function AdminSidebar() {
    const { user, logout } = useAuth();

    return (
        <aside className="w-64 min-h-screen bg-gray-900 text-white flex flex-col">
            {/* Logo */}
            <div className="p-6 border-b border-gray-700">
                <h1 className="text-xl font-bold text-sky-400">AF Estrategia</h1>
                <p className="text-xs text-gray-400 mt-1">Panel Administrador</p>
            </div>

            {/* Usuario */}
            <div className="p-4 border-b border-gray-700">
                <div className="flex items-center gap-3">
                    <div className="w-9 h-9 rounded-full bg-sky-600 flex items-center
                          justify-center text-sm font-bold">
                        {user?.name?.charAt(0).toUpperCase()}
                    </div>
                    <div>
                        <p className="text-sm font-medium">{user?.name}</p>
                        <p className="text-xs text-gray-400">Administrador</p>
                    </div>
                </div>
            </div>

            {/* Navegación */}
            <nav className="flex-1 p-4 space-y-1">
                {links.map(({ to, label, icon: Icon, end }) => (
                    <NavLink
                        key={to}
                        to={to}
                        end={end}
                        className={({ isActive }) =>
                            `flex items-center gap-3 px-3 py-2.5 rounded-lg text-sm
               font-medium transition-all duration-200 group
               ${isActive
                                ? 'bg-sky-600 text-white'
                                : 'text-gray-400 hover:bg-gray-800 hover:text-white'
                            }`
                        }
                    >
                        <Icon size={18} />
                        <span className="flex-1">{label}</span>
                        <ChevronRight size={14} className="opacity-0 group-hover:opacity-100
                                               transition-opacity" />
                    </NavLink>
                ))}
            </nav>

            {/* Logout */}
            <div className="p-4 border-t border-gray-700">
                <button
                    onClick={logout}
                    className="flex items-center gap-3 w-full px-3 py-2.5 rounded-lg
                     text-sm font-medium text-gray-400 hover:bg-gray-800
                     hover:text-white transition-all duration-200"
                >
                    <LogOut size={18} />
                    Cerrar sesión
                </button>
            </div>
        </aside>
    );
}