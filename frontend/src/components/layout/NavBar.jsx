import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../../hooks/useAuth';
import { LogOut, User, Menu, X } from 'lucide-react';
import { useState } from 'react';

export default function Navbar() {
    const { user, logout, isAuthenticated, isAdmin } = useAuth();
    const navigate = useNavigate();
    const [menuOpen, setMenuOpen] = useState(false);

    const handleLogout = () => {
        logout();
        navigate('/');
    };

    return (
        <nav className="bg-white border-b border-gray-200 sticky top-0 z-40">
            <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                <div className="flex justify-between items-center h-16">
                    {/* Logo */}
                    <Link to="/" className="flex items-center gap-2">
                        <div className="w-8 h-8 bg-sky-600 rounded-lg flex items-center
                            justify-center text-white font-bold text-sm">
                            AF
                        </div>
                        <span className="font-bold text-gray-900 hidden sm:block">
              AF Estrategia Financiera
            </span>
                    </Link>

                    {/* Desktop nav */}
                    <div className="hidden md:flex items-center gap-4">
                        <Link to="/#planes"
                              className="text-gray-600 hover:text-sky-600 text-sm font-medium
                         transition-colors">
                            Planes
                        </Link>

                        {isAuthenticated() ? (
                            <>
                                {isAdmin() ? (
                                    <Link to="/admin" className="btn-primary text-sm py-2">
                                        Panel Admin
                                    </Link>
                                ) : (
                                    <Link to="/client" className="btn-primary text-sm py-2">
                                        Mi Panel
                                    </Link>
                                )}
                                <div className="flex items-center gap-2 text-sm text-gray-600">
                                    <User size={16} />
                                    <span>{user?.name}</span>
                                </div>
                                <button onClick={handleLogout}
                                        className="btn-secondary text-sm py-2 flex items-center gap-2">
                                    <LogOut size={16} />
                                    Salir
                                </button>
                            </>
                        ) : (
                            <>
                                <Link to="/login" className="btn-secondary text-sm py-2">
                                    Iniciar sesión
                                </Link>
                                <Link to="/register" className="btn-primary text-sm py-2">
                                    Registrarse
                                </Link>
                            </>
                        )}
                    </div>

                    {/* Mobile menu button */}
                    <button className="md:hidden p-2 text-gray-600"
                            onClick={() => setMenuOpen(!menuOpen)}>
                        {menuOpen ? <X size={20} /> : <Menu size={20} />}
                    </button>
                </div>

                {/* Mobile menu */}
                {menuOpen && (
                    <div className="md:hidden py-4 border-t border-gray-200 space-y-2">
                        {isAuthenticated() ? (
                            <>
                                {isAdmin()
                                    ? <Link to="/admin" className="block btn-primary text-sm text-center">
                                        Panel Admin
                                    </Link>
                                    : <Link to="/client" className="block btn-primary text-sm text-center">
                                        Mi Panel
                                    </Link>
                                }
                                <button onClick={handleLogout}
                                        className="block w-full btn-secondary text-sm">
                                    Cerrar sesión
                                </button>
                            </>
                        ) : (
                            <>
                                <Link to="/login"
                                      className="block btn-secondary text-sm text-center">
                                    Iniciar sesión
                                </Link>
                                <Link to="/register"
                                      className="block btn-primary text-sm text-center">
                                    Registrarse
                                </Link>
                            </>
                        )}
                    </div>
                )}
            </div>
        </nav>
    );
}