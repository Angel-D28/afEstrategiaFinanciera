import { createContext, useState, useEffect } from 'react';
import { authApi } from '../api/authApi';
import toast from 'react-hot-toast';

export const AuthContext = createContext(null);

export function AuthProvider({ children }) {
    const [user, setUser] = useState(null);
    const [token, setToken] = useState(null);
    const [loading, setLoading] = useState(true);

    // Cargar sesión al iniciar
    useEffect(() => {
        const savedToken = localStorage.getItem('token');
        const savedUser = localStorage.getItem('user');

        if (savedToken && savedUser) {
            setToken(savedToken);
            setUser(JSON.parse(savedUser));
        }
        setLoading(false);
    }, []);

    const login = async (email, password) => {
        try {
            const response = await authApi.login({ email, password });
            const { token, name, role } = response.data;

            const userData = { name, email, role };

            localStorage.setItem('token', token);
            localStorage.setItem('user', JSON.stringify(userData));

            setToken(token);
            setUser(userData);

            toast.success(`¡Bienvenido, ${name}!`);
            return { success: true, role };
        } catch (error) {
            const message = error.response?.data?.message
                || 'Error al iniciar sesión';
            toast.error(message);
            return { success: false };
        }
    };

    const logout = () => {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        setToken(null);
        setUser(null);
        toast.success('Sesión cerrada');
    };

    const isAdmin = () => user?.role === 'ADMIN';
    const isClient = () => user?.role === 'CLIENT';
    const isAuthenticated = () => !!token;

    return (
        <AuthContext.Provider value={{
            user, token, loading,
            login, logout,
            isAdmin, isClient, isAuthenticated
        }}>
            {children}
        </AuthContext.Provider>
    );
}