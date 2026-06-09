import api from './axios';

export const authApi = {
    login: (credentials) =>
        api.post('/api/auth/login', credentials),
};