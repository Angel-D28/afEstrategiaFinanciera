import api from './axios';

export const subscriptionsApi = {
    subscribe: (planId) =>
        api.post('/api/subscriptions', { planId }),

    getMySubs: () =>
        api.get('/api/subscriptions/my'),

    getAll: () =>
        api.get('/api/subscriptions'),

    getById: (id) =>
        api.get(`/api/subscriptions/${id}`),

    updateStatus: (id, status) =>
        api.patch(`/api/subscriptions/${id}/status`, { status }),
};