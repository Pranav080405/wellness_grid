// All fetch() calls to the Spring Boot backend live here
const BASE_URL = 'http://localhost:8080/api';

async function getAllRetreats() {
  const res = await fetch(`${BASE_URL}/retreats`);
  return res.json();
}

async function searchRetreats(params = {}) {
  const query = Object.entries(params)
    .filter(([, val]) => val !== '' && val !== null && val !== undefined)
    .map(([key, val]) => `${key}=${encodeURIComponent(val)}`)
    .join('&');
  const res = await fetch(`${BASE_URL}/retreats/search${query ? '?' + query : ''}`);
  return res.json();
}

async function getRetreatById(id) {
  const res = await fetch(`${BASE_URL}/retreats/${id}`);
  if (!res.ok) throw new Error('Retreat not found');
  return res.json();
}

async function createRetreat(data) {
  const res = await fetch(`${BASE_URL}/retreats`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data)
  });
  return res.json();
}

async function updateRetreat(id, data) {
  const res = await fetch(`${BASE_URL}/retreats/${id}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data)
  });
  return res.json();
}

async function deleteRetreat(id) {
  const res = await fetch(`${BASE_URL}/retreats/${id}`, { method: 'DELETE' });
  return res.ok;
}

async function getAllRetreatsAdmin() {
  const res = await fetch(`${BASE_URL}/retreats/admin/all`);
  return res.json();
}

async function createLead(data) {
  const res = await fetch(`${BASE_URL}/leads`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data)
  });
  return res.json();
}

async function getAllLeads(status = '') {
  const url = status ? `${BASE_URL}/leads?status=${status}` : `${BASE_URL}/leads`;
  const res = await fetch(url);
  return res.json();
}

async function updateLeadStatus(id, status) {
  const res = await fetch(`${BASE_URL}/leads/${id}/status`, {
    method: 'PATCH',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ status })
  });
  return res.json();
}

async function deleteLead(id) {
  const res = await fetch(`${BASE_URL}/leads/${id}`, { method: 'DELETE' });
  return res.ok;
}

async function getLeadStats() {
  const res = await fetch(`${BASE_URL}/leads/stats`);
  return res.json();
}