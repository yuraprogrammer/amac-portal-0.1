$(function () {
    const id = getCourseId();
    if (!id) {
        alert("Course id not provided");
        return;
    }

    loadCourse(id);
});

function getCourseId() {
    const params = new URLSearchParams(window.location.search);
    return params.get("id");
}

function loadCourse(id) {
    API.get(`/student/courses/${id}`, function (course) {
        renderCourse(course);
    }, function (err) {
        alert("Failed to load course");
        console.error(err);
    });
}

function renderCourse(course) {
    $("#course-title").text(course.title);
    $("#course-desc").text(course.description);
}
