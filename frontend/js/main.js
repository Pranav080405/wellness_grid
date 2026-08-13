document.addEventListener('DOMContentLoaded', loadRetreats);

async function loadRetreats() {
  try {
    const retreats = await getAllRetreats();
    renderCards(retreats);
  } catch (err) {
    document.getElementById('cards-grid').innerHTML =
      '<p style="color:red;padding:1rem;">Could not connect to server. Is Spring Boot running on port 8080?</p>';
  }
}

async function applyFilters() {
  const search  = document.getElementById('search-input').value.trim();
  const country = document.getElementById('f-country').value;
  const type    = document.getElementById('f-type').value;
  const price   = document.getElementById('f-price').value;

  let minPrice, maxPrice;
  if (price) [minPrice, maxPrice] = price.split('-').map(Number);

  const results = await searchRetreats({ search, country, type, minPrice, maxPrice });
  renderCards(results);
}

function quickFilter(type) {
  document.getElementById('f-type').value = type;
  applyFilters();
  document.querySelector('.listings').scrollIntoView({ behavior: 'smooth' });
}

function renderCards(retreats) {
  const grid  = document.getElementById('cards-grid');
  const count = document.getElementById('result-count');
  count.innerHTML = `<strong>${retreats.length}</strong> retreat${retreats.length !== 1 ? 's' : ''} found`;

  if (retreats.length === 0) {
    grid.innerHTML = '<p class="loading-msg">No retreats match your filters.</p>';
    return;
  }

  grid.innerHTML = retreats.map(r => `
    <a class="card" href="detail.html?id=${r.id}">
      <div class="card-img" style="background-image:url('${r.imageUrl}');background-color:#7a9e7e;">
        <div class="card-badges">
          <span class="badge badge-type">${r.type}</span>
          ${r.featured ? '<span class="badge badge-featured">Featured</span>' : ''}
        </div>
      </div>
      <div class="card-body">
        <div class="card-location">📍 ${r.location}</div>
        <div class="card-title">${r.title}</div>
        <div class="card-desc">${r.description}</div>
        <div class="card-footer">
          <div class="card-price">
            <small>From</small>
            ₹${r.price.toLocaleString()} <small style="display:inline;">/ ${r.duration}</small>
          </div>
          <div class="card-rating">★ <strong>${r.rating}</strong> (${r.reviewCount})</div>
        </div>
      </div>
    </a>
  `).join('');
}

function openModal(retreatId = null, retreatTitle = '') {
  document.getElementById('retreatId').value = retreatId || '';
  document.getElementById('retreatTitle').value = retreatTitle;
  document.getElementById('modal-subtitle').textContent =
    retreatTitle ? `Enquiring about: ${retreatTitle}` : "We'll get back to you within 24 hours.";
  document.getElementById('modal').style.display = 'flex';
  document.getElementById('inquiry-form').style.display = 'block';
  document.getElementById('form-success').style.display = 'none';
}

function closeModal() {
  document.getElementById('modal').style.display = 'none';
}

document.getElementById('modal').addEventListener('click', function(e) {
  if (e.target === this) closeModal();
});

async function submitInquiry(event) {
  event.preventDefault();
  const leadData = {
    firstName:    document.getElementById('firstName').value,
    lastName:     document.getElementById('lastName').value,
    email:        document.getElementById('email').value,
    phone:        document.getElementById('phone').value,
    retreatId:    document.getElementById('retreatId').value || null,
    retreatTitle: document.getElementById('retreatTitle').value,
    preferredDate: document.getElementById('preferredDate').value,
    guestCount:   document.getElementById('guestCount').value,
    message:      document.getElementById('message').value
  };
  try {
    await createLead(leadData);
    document.getElementById('inquiry-form').style.display = 'none';
    document.getElementById('form-success').style.display = 'block';
  } catch (err) {
    alert('Something went wrong. Please try again.');
  }
}