import api from './axios';

export const plansApi = {
    getActive: () =>
        api.get('/api/plans/active'),

    getById: (id) =>
        api.get(`/api/plans/${id}`),

    getAll: () =>
        api.get('/api/plans'),

    create: (data) =>
        api.post('/api/plans', data),

    update: (id, data) =>
        api.put(`/api/plans/${id}`, data),

    updateStatus: (id, status) =>
        api.patch(`/api/plans/${id}/status`, { status }),

    delete: (id) =>
        api.delete(`/api/plans/${id}`),
};