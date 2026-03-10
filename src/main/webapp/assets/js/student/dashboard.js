$(function(){
    // Пример данных — позже заменить API вызовами
    const courses = [
        {id:1, title:"Precalculus", desc:"Intro to algebra", progress:45},
        {id:2, title:"Physics", desc:"Mechanics basics", progress:70},
        {id:3, title:"Government", desc:"USA politics structure", progress:60}
    ];

    const assignments = [
        {id:1, title:"Algebra HW1", course:"Mathematics 101", deadline:"2026-02-15", status:"Pending"},
        {id:2, title:"Physics Lab 1", course:"Physics 101", deadline:"2026-02-18", status:"Submitted"},
        {id:3, title:"Government Seminar", course:"Government", deadline:"2026-02-20", status:"Pending"}
    ];

    // Рендер курсов
    const $coursesList = $("#courses-list");
    courses.forEach(c=>{
        $coursesList.append(`
            <div class="col-md-4">
                <div class="card h-100 shadow-sm">
                    <div class="card-body">
                        <h5 class="card-title">${c.title}</h5>
                        <p class="card-text">${c.desc}</p>
                        <div class="progress mb-3">
                            <div class="progress-bar bg-success" role="progressbar" style="width:${c.progress}%">${c.progress}%</div>
                        </div>
                        <a href="course.html?id=${c.id}" class="btn btn-primary">Open Course</a>
                    </div>
                </div>
            </div>
        `);
    });

    // Рендер заданий
    const $assignTable = $("#assignments-table tbody");
    assignments.forEach(a=>{
        $assignTable.append(`
            <tr>
                <td>${a.title}</td>
                <td>${a.course}</td>
                <td>${a.deadline}</td>
                <td><span class="badge ${a.status==="Pending"?"bg-warning":"bg-success"}">${a.status}</span></td>
                <td><a href="task.html?id=${a.id}" class="btn btn-sm btn-outline-primary">${a.status==="Pending"?"Open":"View"}</a></td>
            </tr>
        `);
    });
});
