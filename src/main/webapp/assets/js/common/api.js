const API = {
    get: function (url) {
    return $.ajax({
                    url: '/amac-portal/api' + url,
                    method: 'GET',
                    dataType: 'json'
            });
    }
};

function loadCourses() {
    $.ajax({
        url: API + '/courses',
        method: 'GET',
        success: function(courses) {
            const container = $('#course-cards-container');
            container.empty();
            courses.forEach(course => {
                const card = `
          <div class="card shadow-sm h-100">
            <div class="card-body">
              <h5 class="card-title">${course.title}</h5>
              <p class="card-text">${course.description}</p>
              <a href="lesson.html?courseId=${course.id}" class="btn btn-primary">Follow to course</a>
            </div>
          </div>
        `;
                container.append(card);
            });
        }
    });
}
