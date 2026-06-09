import api from './axios';

export const usersApi = {
    register: (data) =>
        api.post('/api/users/register', data),

    getAll: () =>
        api.get('/api/users'),

    getById: (id) =>
        api.get(`/api/users/${id}`),

    getByRole: (role) =>
        api.get(`/api/users/role/${role}`),

    updateStatus: (id, status) =>
        api.patch(`/api/users/${id}/status`, { status }),

    getMyProfile: () =>
        api.get('/api/users/me'),
};