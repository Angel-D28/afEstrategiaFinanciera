import axios from 'axios';

const api = axios.create({
    baseURL: 'http://localhost:8080',
    headers: {
        'Content-Type': 'application/json',
    },
});

// ── Interceptor de REQUEST ──────────────────────────────────
// Agrega el token JWT automáticamente a cada petición
api.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('token');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => Promise.reject(error)
);

// ── Interceptor de RESPONSE ─────────────────────────────────
// Maneja errores globalmente
api.interceptors.response.use(
    (response) => response,
    (error) => {
        // Si el token expiró o es inválido → logout automático
        if (error.response?.status === 401) {
            localStorage.removeItem('token');
            localStorage.removeItem('user');
            window.location.href = '/login';
        }
        return Promise.reject(error);
    }
);

export default api;