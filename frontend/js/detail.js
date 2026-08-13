let currentRetreat = null;

document.addEventListener('DOMContentLoaded', async () => {
  const params = new URLSearchParams(window.location.search);
  const id = params.get('id');
  if (!id) { document.getElementById('detail-content').innerHTML = '<p style="padding:2rem;">No retreat ID.</p>'; return; }
  try {
    currentRetreat = await getRetreatById(id);
    renderDetail(currentRetreat);
  } catch (err) {
    document.getElementById('detail-content').innerHTML = '<p style="padding:2rem;color:red;">Retreat not found.</p>';
  }
});

function renderDetail(r) {
  const amenities = r.amenities ? r.amenities.split(',').map(a => a.trim()) : [];
  const dates     = r.availableDates ? r.availableDates.split(',').map(d => d.trim()) : [];
  document.title  = `${r.title} – Trexova`;

  document.getElementById('detail-content').innerHTML = `
    <div class="detail-hero-img" style="background-image:url('${r.imageUrl}');background-color:#7a9e7e;"></div>
    <div class="detail-body">
      <div>
        <div class="detail-location">📍 ${r.location}</div>
        <h1 class="detail-title">${r.title}</h1>
        <div class="detail-meta">★ <strong>${r.rating}</strong> (${r.reviewCount} reviews) · ${r.duration} · ${r.type}</div>
        <p class="detail-desc">${r.description}</p>
        <div class="detail-section">
          <h3>What's Included</h3>
          <div class="amenities-grid">
            ${amenities.map(a => `<div class="amenity">${a}</div>`).join('')}
          </div>
        </div>
        <div class="detail-section">
          <h3>Upcoming Dates</h3>
          <div class="dates-list">
            ${dates.map(d => `<div class="date-chip">📅 ${d}</div>`).join('')}
          </div>
        </div>
        <div class="detail-section">
          <h3>Location</h3>
          <div style="background:var(--sand);border:1px solid var(--sand-mid);border-radius:12px;height:140px;display:flex;align-items:center;justify-content:center;flex-direction:column;gap:.4rem;color:var(--muted);font-size:.875rem;">
            <span style="font-size:1.5rem;">📍</span>
            <strong>${r.location}</strong>
            <span>Add Google Maps embed here</span>
          </div>
        </div>
      </div>
      <div>
        <div class="sidebar-card">
          <div class="sidebar-header">
            <div style="font-size:.75rem;color:var(--muted);text-transform:uppercase;letter-spacing:.05em;">From</div>
            <div class="sidebar-price">₹${r.price.toLocaleString()} <small>/ ${r.duration}</small></div>
          </div>
          <div class="sidebar-body">
            <table style="width:100%;font-size:.875rem;margin-bottom:1rem;">
              <tr><td style="color:var(--muted);padding:.35rem 0;">Duration</td><td style="text-align:right;font-weight:500;">${r.duration}</td></tr>
              <tr><td style="color:var(--muted);padding:.35rem 0;">Type</td><td style="text-align:right;font-weight:500;text-transform:capitalize;">${r.type}</td></tr>
              <tr><td style="color:var(--muted);padding:.35rem 0;">Rating</td><td style="text-align:right;font-weight:500;">★ ${r.rating}</td></tr>
            </table>
            <button class="btn-enquire" onclick="openModal(${r.id}, '${r.title.replace(/'/g,"\\'")}')">Enquire Now</button>
            <p class="sidebar-note">Free inquiry · No commitment<br>Responds within 24 hours</p>
          </div>
        </div>
      </div>
    </div>
  `;
}

function openModal(retreatId, retreatTitle) {
  document.getElementById('retreatId').value = retreatId;
  document.getElementById('retreatTitle').value = retreatTitle;
  document.getElementById('modal-subtitle').textContent = `Enquiring about: ${retreatTitle}`;
  document.getElementById('modal').style.display = 'flex';
  document.getElementById('inquiry-form').style.display = 'block';
  document.getElementById('form-success').style.display = 'none';
}

function closeModal() { document.getElementById('modal').style.display = 'none'; }

document.getElementById('modal').addEventListener('click', function(e) {
  if (e.target === this) closeModal();
});

async function submitInquiry(event) {
  event.preventDefault();
  const leadData = {
    firstName: document.getElementById('firstName').value,
    lastName:  document.getElementById('lastName').value,
    email:     document.getElementById('email').value,
    phone:     document.getElementById('phone').value,
    retreatId: document.getElementById('retreatId').value || null,
    retreatTitle: document.getElementById('retreatTitle').value,
    preferredDate: document.getElementById('preferredDate').value,
    guestCount: document.getElementById('guestCount').value,
    message:   document.getElementById('message').value
  };
  try {
    await createLead(leadData);
    document.getElementById('inquiry-form').style.display = 'none';
    document.getElementById('form-success').style.display = 'block';
  } catch (err) { alert('Something went wrong.'); }
}