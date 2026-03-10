$(function () {
    const courseId = new URLSearchParams(window.location.search).get('id');

    API.get('/courses').done(courses => {
        const course = courses.find(c => c.id === courseId);
        $('#courseTitle').text(course.title);
    });

    API.get('/modules/by-course/' + courseId)
        .done(renderModules);
});

function renderModules(modules) {
    const list = $('#modules');
    list.empty();

    modules.forEach(m => {
        list.append(`
            <a class="list-group-item list-group-item-action"
               href="lesson.html?moduleId=${m.id}">
                ${m.position}. ${m.title}
            </a>
        `);
    });
}
