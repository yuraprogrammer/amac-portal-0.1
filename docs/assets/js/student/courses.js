$(function () {
    loadCourses();
});

function loadCourses() {
    API.get("/student/courses", function (courses) {
        renderCourses(courses);
    }, function (err) {
        alert("Failed to load courses");
        console.error(err);
    });
}

function renderCourses(courses) {
    const row = $("#coursesRow");
    row.empty();

    if (!courses || courses.length === 0) {
        row.append(`
            <div class="col-12">
                <div class="alert alert-info">
                    You are not enrolled in any courses yet.
                </div>
            </div>
        `);
        return;
    }

    courses.forEach(c => {
        row.append(courseCard(c));
    });
}

function courseCard(course) {
    return `
        <div class="col-md-4">
            <div class="card h-100 shadow-sm">
                <div class="card-body d-flex flex-column">
                    <h5 class="card-title">${escapeHtml(course.title)}</h5>
                    <p class="card-text text-muted flex-grow-1">
                        ${escapeHtml(course.description || "")}
                    </p>
                    <a href="course.html?id=${course.id}"
                       class="btn btn-primary mt-3">
                        Open course
                    </a>
                </div>
            </div>
        </div>
    `;
}

/* basic XSS safety */
function escapeHtml(text) {
    return $("<div>").text(text).html();
}
