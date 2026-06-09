import api from './axios';

export const paymentsApi = {
    register: (data) =>
        api.post('/api/payments', data),

    getAll: () =>
        api.get('/api/payments'),

    getById: (id) =>
        api.get(`/api/payments/${id}`),

    getBySubscription: (subscriptionId) =>
        api.get(`/api/payments/subscription/${subscriptionId}`),

    updateStatus: (id, status) =>
        api.patch(`/api/payments/${id}/status`, { status }),
};