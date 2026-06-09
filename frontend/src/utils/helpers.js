// Formatear precio en pesos colombianos
export const formatPrice = (price) => {
    return new Intl.NumberFormat('es-CO', {
        style: 'currency',
        currency: 'COP',
        minimumFractionDigits: 0,
    }).format(price);
};

// Formatear fecha
export const formatDate = (date) => {
    return new Intl.DateTimeFormat('es-CO', {
        year: 'numeric',
        month: 'long',
        day: 'numeric',
    }).format(new Date(date));
};

// Traducir estados
export const translateStatus = (status) => {
    const translations = {
        ACTIVE: 'Activo',
        INACTIVE: 'Inactivo',
        PENDING: 'Pendiente',
        SUSPENDED: 'Suspendido',
        CANCELLED: 'Cancelado',
        EXPIRED: 'Vencido',
        PAUSED: 'Pausado',
        COMPLETED: 'Completado',
        FAILED: 'Fallido',
        REFUNDED: 'Reembolsado',
        DRAFT: 'Borrador',
        ADMIN: 'Administrador',
        AGENT: 'Asesor',
        CLIENT: 'Cliente',
    };
    return translations[status] || status;
};

// Traducir métodos de pago
export const translatePaymentMethod = (method) => {
    const translations = {
        CASH: 'Efectivo',
        BANK_TRANSFER: 'Transferencia',
        CARD: 'Tarjeta',
        NEQUI: 'Nequi',
        DAVIPLATA: 'Daviplata',
    };
    return translations[method] || method;
};