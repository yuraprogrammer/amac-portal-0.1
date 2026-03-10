function loadAssignments(lessonId, btn) {
    const container = $(btn).siblings('.assignments');

    API.get('/assignments/by-lesson/' + lessonId)
        .done(assignments => {
            if (assignments.length === 0) {
                container.html('<p class="text-muted">No assignments</p>');
                return;
            }

            container.html(assignments.map(a => `
                <div class="border rounded p-3 mb-2">
                    <strong>${a.title}</strong>
                    <p>${a.description || ''}</p>
                    <small class="text-muted">
                        Due: ${a.dueAt ? new Date(a.dueAt).toLocaleString() : '—'}
                        | Type: ${a.type}
                    </small>
                </div>
            `).join(''));
        });
}

let currentAssignmentId = null;

$(async function () {
    const courseId = new URLSearchParams(location.search).get('id');
    const assignments = await API.assignments(courseId);

    assignments.forEach(a =>
        $('#assignments').append(`
            <div class="card mb-2">
              <div class="card-body">
                <h5>${a.title}</h5>
                <p>${a.description}</p>
                <button class="btn btn-sm btn-outline-primary submit"
                        data-id="${a.id}">
                  Submit
                </button>
              </div>
            </div>
        `)
    );
});

$(document).on('click', '.submit', function () {
    currentAssignmentId = $(this).data('id');
    $('#submitModal').modal('show');
});

$('#submitBtn').on('click', async function () {
    const answer = $('#answer').val();

    await fetch(`/amac-portal/api/assignments/${currentAssignmentId}/submission`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({answer})
    });

    $('#submitModal').modal('hide');
    $('#answer').val('');
    alert('Submitted');
});
