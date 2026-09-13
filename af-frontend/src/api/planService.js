import { apiClient } from './client'

// GET /api/plans/active -> catálogo público de planes disponibles
export async function getActivePlans() {
  const { data } = await apiClient.get('/api/plans/active')
  return data
}

// GET /api/plans/{id} -> detalle público de un plan
export async function getPlanById(id) {
  const { data } = await apiClient.get(`/api/plans/${id}`)
  return data
}

// GET /api/plans -> ADMIN: todos los planes
export async function getAllPlans() {
  const { data } = await apiClient.get('/api/plans')
  return data
}

// POST /api/plans -> ADMIN: crear un plan nuevo
export async function createPlan(plan) {
  const { data } = await apiClient.post('/api/plans', plan)
  return data
}

// PUT /api/plans/{id} -> ADMIN: reemplazar los datos de un plan
export async function updatePlan(id, plan) {
  const { data } = await apiClient.put(`/api/plans/${id}`, plan)
  return data
}

// PATCH /api/plans/{id}/status -> ADMIN: cambiar solo el estado
export async function updatePlanStatus(id, status) {
  const { data } = await apiClient.patch(`/api/plans/${id}/status`, { status })
  return data
}

// DELETE /api/plans/{id} -> ADMIN: desactivar un plan
export async function deletePlan(id) {
  await apiClient.delete(`/api/plans/${id}`)
}
