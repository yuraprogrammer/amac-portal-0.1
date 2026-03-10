$(function () {
    const moduleId = new URLSearchParams(window.location.search).get('moduleId');

    API.get('/lessons/by-module/' + moduleId)
        .done(renderLessons);
});

function renderLessons(lessons) {
    const container = $('#lessons');
    container.empty();

    lessons.forEach(l => {
        container.append(`
            <div class="card mb-4">
                <div class="card-body">
                    <h5>${l.title}</h5>
                    <div>${l.content || ''}</div>

                    <button class="btn btn-outline-secondary mt-3"
                        onclick="loadAssignments('${l.id}', this)">
                        Show assignments
                    </button>

                    <div class="assignments mt-3"></div>
                </div>
            </div>
        `);
    });
}
