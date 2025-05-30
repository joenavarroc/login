let calendar = null;

document.addEventListener('DOMContentLoaded', function () {
  const calendarioModalEl = document.getElementById('calendarioModal');

  calendarioModalEl.addEventListener('shown.bs.modal', function () {
    if (!calendar) {
      const calendarEl = document.getElementById('calendar');

      calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        events: '/eventos',
        eventClick: function (info) {
          openEventModal(info.event);
        },
        dateClick: function (info) {
          openEventModal(null, info.dateStr);
        }
      });

      calendar.render();
    }
  });
});

function openEventModal(event = null, selectedDate = null) {
  const deleteButton = document.getElementById('deleteEvent');
  const modal = new bootstrap.Modal(document.getElementById('eventoModal'));
  document.getElementById('modalTitle').textContent = event ? 'Editar Evento' : 'Nuevo Evento';

  if (event) {
    document.getElementById('eventId').value = event.id;
    document.getElementById('titulo').value = event.title;
    document.getElementById('descripcion').value = event.extendedProps.descripcion || '';
    document.getElementById('fecha').value = event.startStr.split('T')[0];
    document.getElementById('hora').value = event.startStr.split('T')[1]?.substring(0, 5) || '00:00';
    deleteButton.style.display = 'inline-block';
  } else if (selectedDate) {
    document.getElementById('eventId').value = '';
    document.getElementById('titulo').value = '';
    document.getElementById('descripcion').value = '';
    document.getElementById('fecha').value = selectedDate;
    document.getElementById('hora').value = '00:00';
    deleteButton.style.display = 'none';
  }

  modal.show();
}

document.getElementById('eventForm').addEventListener('submit', function (e) {
  e.preventDefault();

  const id = document.getElementById('eventId').value;
  const evento = {
    titulo: document.getElementById('titulo').value,
    descripcion: document.getElementById('descripcion').value,
    fecha: document.getElementById('fecha').value,
    hora: document.getElementById('hora').value
  };

  const method = id ? 'PUT' : 'POST';
  const url = id ? `/eventos/${id}` : `/eventos`;

  fetch(url, {
    method: method,
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(evento)
  })
    .then(response => {
      if (response.ok) {
        // 🔄 Refresca los eventos del calendario sin recargar
        calendar.refetchEvents();

        // ✅ Cierra el modal del formulario si existe como modal
        const modalForm = bootstrap.Modal.getInstance(document.getElementById('eventoModal'));
        if (modalForm) modalForm.hide();
      } else {
        alert('Error al guardar el evento');
      }
    })
    .catch(error => {
      console.error('Error de red:', error);
    });
});

document.getElementById('deleteEvent').addEventListener('click', function () {
  const id = document.getElementById('eventId').value;
  if (!id) return;

  if (confirm('¿Estás seguro de que deseas eliminar este evento?')) {
    fetch(`/eventos/${id}`, {
      method: 'DELETE'
    })
      .then(response => {
        if (response.ok) {
          // 🔄 Actualiza eventos en el calendario
          calendar.refetchEvents();

          // ✅ Cierra solo el modal del formulario
          const modalForm = bootstrap.Modal.getInstance(document.getElementById('eventoModal'));
          if (modalForm) modalForm.hide();
        } else {
          alert('No se pudo eliminar el evento');
        }
      })
      .catch(error => {
        console.error('Error al eliminar:', error);
      });
  }
});

document.addEventListener('DOMContentLoaded', function () {
  var contactoModal = document.getElementById('contactoModal');

  contactoModal.addEventListener('show.bs.modal', function (event) {
    var button = event.relatedTarget;
    var id = button.getAttribute('data-id'); // null si es nuevo

    // Cambiar el título dinámicamente
    var modalTitle = contactoModal.querySelector('.modal-title');
    modalTitle.textContent = id ? 'Editar Contacto' : 'Nuevo Contacto';

    // Mostrar loader mientras se carga el contenido
    var modalBody = contactoModal.querySelector('#modal-body-content');
    modalBody.innerHTML = '<div class="text-center p-3"><div class="spinner-border text-primary" role="status"></div></div>';

    // URL del formulario (nuevo o editar)
    var url = id ? '/contactos/editar/' + id : '/contactos/formulario';

    fetch(url)
      .then(response => {
        if (!response.ok) {
          throw new Error("Error al cargar el formulario");
        }
        return response.text();
      })
      .then(html => {
        modalBody.innerHTML = html;
      })
      .catch(err => {
        modalBody.innerHTML = '<p class="text-danger">Error cargando el formulario</p>';
        console.error(err);
      });
  });
});

// whatsapp
  const openBtn = document.getElementById('openWhatsAppChat');
  const modal = document.getElementById('whatsappModal');
  const closeBtn = document.getElementById('closeWhatsAppModal');

  openBtn.addEventListener('click', () => {
    modal.style.display = 'block';
  });

  closeBtn.addEventListener('click', () => {
    modal.style.display = 'none';
  });