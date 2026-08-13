document.addEventListener('DOMContentLoaded', () => {
  loadDashboard();
  loadRetreatsTable();
  loadLeadsTable();
});

function showTab(name, el) {
  document.querySelectorAll('.admin-tab').forEach(t => t.classList.remove('active'));
  document.querySelectorAll('.admin-nav-item').forEach(i => i.classList.remove('active'));
  document.getElementById('tab-' + name).classList.add('active');
  if (el) el.classList.add('active');
}

async function loadDashboard() {
  try {
    const [retreats, stats] = await Promise.all([getAllRetreats(), getLeadStats()]);
    document.getElementById('stat-retreats').textContent  = retreats.length;
    document.getElementById('stat-leads').textContent     = stats.totalLeads;
    document.getElementById('stat-new-leads').textContent = stats.newLeads;
    if (stats.newLeads > 0) document.getElementById('new-leads-badge').textContent = stats.newLeads;

    const leads = await getAllLeads();
    document.getElementById('recent-leads-body').innerHTML =
      leads.slice(0, 5).map(l => `
        <tr>
          <td><strong>${l.firstName} ${l.lastName}</strong></td>
          <td>${l.email}</td>
          <td>${l.retreatTitle || '—'}</td>
          <td><span class="status status-${l.status}">${l.status}</span></td>
        </tr>
      `).join('') || '<tr><td colspan="4">No leads yet.</td></tr>';
  } catch (err) { console.error(err); }
}

async function loadRetreatsTable() {
  try {
    const retreats = await getAllRetreatsAdmin();
    document.getElementById('retreats-body').innerHTML = retreats.map(r => `
      <tr>
        <td><strong>${r.title}</strong></td>
        <td>${r.location}</td>
        <td style="text-transform:capitalize;">${r.type}</td>
        <td>$${r.price ? r.price.toLocaleString() : '—'}</td>
        <td><span class="status ${r.active ? 'status-active' : 'status-inactive'}">${r.active ? 'Active' : 'Inactive'}</span></td>
        <td>
          <button class="btn-edit" onclick="editRetreat(${r.id})">Edit</button>
          <button class="btn-delete" onclick="confirmDelete(${r.id}, '${r.title.replace(/'/g,"\\'")}')">Delete</button>
        </td>
      </tr>
    `).join('') || '<tr><td colspan="6">No retreats.</td></tr>';
  } catch (err) {
    document.getElementById('retreats-body').innerHTML = '<tr><td colspan="6" style="color:red;">Failed to load.</td></tr>';
  }
}

let isEditing = false;

function toggleAddForm() {
  const form = document.getElementById('add-retreat-form');
  form.style.display = form.style.display === 'none' ? 'block' : 'none';
  if (form.style.display === 'block') { document.getElementById('form-title').textContent = 'Add New Retreat'; clearForm(); isEditing = false; }
}

function cancelForm() { document.getElementById('add-retreat-form').style.display = 'none'; clearForm(); }

function clearForm() {
  ['r-title','r-location','r-country','r-price','r-duration','r-description','r-imageUrl','r-amenities','r-dates']
    .forEach(id => { document.getElementById(id).value = ''; });
  document.getElementById('edit-id').value = '';
  document.getElementById('r-type').value = 'yoga';
}

async function editRetreat(id) {
  try {
    const r = await getRetreatById(id);
    document.getElementById('edit-id').value       = r.id;
    document.getElementById('r-title').value       = r.title;
    document.getElementById('r-location').value    = r.location;
    document.getElementById('r-country').value     = r.country;
    document.getElementById('r-type').value        = r.type;
    document.getElementById('r-price').value       = r.price;
    document.getElementById('r-duration').value    = r.duration;
    document.getElementById('r-description').value = r.description;
    document.getElementById('r-imageUrl').value    = r.imageUrl;
    document.getElementById('r-amenities').value   = r.amenities;
    document.getElementById('r-dates').value       = r.availableDates;
    document.getElementById('form-title').textContent = 'Edit Retreat';
    document.getElementById('add-retreat-form').style.display = 'block';
    isEditing = true;
  } catch (err) { alert('Could not load retreat.'); }
}

async function saveRetreat() {
  const data = {
    title: document.getElementById('r-title').value,
    location: document.getElementById('r-location').value,
    country: document.getElementById('r-country').value,
    type: document.getElementById('r-type').value,
    price: parseFloat(document.getElementById('r-price').value) || null,
    duration: document.getElementById('r-duration').value,
    description: document.getElementById('r-description').value,
    imageUrl: document.getElementById('r-imageUrl').value,
    amenities: document.getElementById('r-amenities').value,
    availableDates: document.getElementById('r-dates').value,
    active: true, featured: false
  };
  if (!data.title || !data.location) { alert('Title and Location are required.'); return; }
  try {
    const editId = document.getElementById('edit-id').value;
    if (isEditing && editId) await updateRetreat(editId, data);
    else await createRetreat(data);
    cancelForm();
    loadRetreatsTable();
    loadDashboard();
    alert('Saved!');
  } catch (err) { alert('Failed to save.'); }
}

async function confirmDelete(id, title) {
  if (!confirm(`Delete "${title}"?`)) return;
  try { await deleteRetreat(id); loadRetreatsTable(); loadDashboard(); }
  catch (err) { alert('Failed to delete.'); }
}

async function loadLeadsTable(status = '') {
  try {
    const leads = await getAllLeads(status);
    document.getElementById('leads-body').innerHTML = leads.map(l => `
      <tr>
        <td><strong>${l.firstName} ${l.lastName}</strong></td>
        <td>${l.email}<br><span style="color:var(--muted);font-size:.75rem;">${l.phone || ''}</span></td>
        <td>${l.retreatTitle || '—'}</td>
        <td style="max-width:140px;font-size:.8125rem;color:var(--muted);">${(l.message||'').slice(0,50)}${(l.message||'').length>50?'...':''}</td>
        <td style="font-size:.8125rem;">${formatDate(l.createdAt)}</td>
        <td>
          <select onchange="changeStatus(${l.id}, this.value)" style="border:1px solid var(--sand-dark);border-radius:6px;padding:.25rem .5rem;font-size:.75rem;">
            ${['NEW','HOT','REPLIED','CLOSED'].map(s => `<option value="${s}" ${l.status===s?'selected':''}>${s}</option>`).join('')}
          </select>
        </td>
        <td><button class="btn-delete" onclick="confirmDeleteLead(${l.id})">Delete</button></td>
      </tr>
    `).join('') || '<tr><td colspan="7">No leads yet.</td></tr>';
  } catch (err) {
    document.getElementById('leads-body').innerHTML = '<tr><td colspan="7" style="color:red;">Failed to load.</td></tr>';
  }
}

function filterLeads(status) { loadLeadsTable(status); }

async function changeStatus(id, status) {
  try { await updateLeadStatus(id, status); loadDashboard(); }
  catch (err) { alert('Failed to update.'); }
}

async function confirmDeleteLead(id) {
  if (!confirm('Delete this lead?')) return;
  try { await deleteLead(id); loadLeadsTable(); loadDashboard(); }
  catch (err) { alert('Failed.'); }
}

function formatDate(str) {
  if (!str) return '—';
  return new Date(str).toLocaleDateString('en-IN', { day:'numeric', month:'short', year:'numeric' });
}