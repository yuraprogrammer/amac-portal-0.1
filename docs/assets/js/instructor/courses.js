$(function () {
    loadCourses();
});

function loadCourses() {
    apiGet("/api/instructor/courses", function (courses) {
        renderCourses(courses);
    });
}

function renderCourses(courses) {

    const row = $("#coursesRow");
    row.empty();

    if (!courses || courses.length === 0) {
        row.append(`
            <div class="col-12">
                <div class="alert alert-info">
                    You don't have any courses yet.
                </div>
            </div>
        `);
        return;
    }

    courses.forEach(c => row.append(courseCard(c)));
}

function courseCard(course) {
    return `
        <div class="col-md-4">
            <div class="card shadow-sm h-100">

                <div class="card-body d-flex flex-column">

                    <h5>${escapeHtml(course.title)}</h5>

                    <p class="text-muted flex-grow-1">
                        ${escapeHtml(course.description || "")}
                    </p>

                    <a href="course.html?id=${course.id}"
                       class="btn btn-outline-primary mt-2">
                        Manage
                    </a>

                </div>

            </div>
        </div>
    `;
}

function createCourse() {

    const title = prompt("Course title");

    if (!title) return;

    apiPost("/api/instructor/courses",
        {title: title},
        function () {
            loadCourses();
        });
}

function escapeHtml(text) {
    return $("<div>").text(text).html();
}