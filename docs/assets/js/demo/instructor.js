let assignmentsCount = 0;
let submissionsCount = 0;
let grades = [];
let submissions = [];
let assignments = [];

renderAssignments();
const STATUS = {

    ASSIGNED:"assigned",
    IN_PROGRESS:"in_progress",
    SUBMITTED:"submitted",
    AI_REVIEW:"ai_review",
    TEACHER_REVIEW:"teacher_review",
    GRADED:"graded"

};

function statusBadge(status){

    const colors = {

        assigned:"secondary",
        in_progress:"primary",
        submitted:"warning",
        ai_review:"info",
        teacher_review:"dark",
        graded:"success"

    };

    return `<span class="badge bg-${colors[status]}">${status}</span>`;

}


$(function () {

    Sync.listen(msg => {

        /*if (msg.type === "assignmentCreated") {
            assignmentsCount++;
            updateStats();
        }*/

        if (msg.type === "submissionCreated") {
            submissionsCount++;
            submissions.push(msg.payload);
            addSubmission(msg.payload);
            updateStats();
        }

        /*if (msg.type === "gradeAssigned") {


        }*/

        if (msg.type === "studentOnline") {
            addStudent(msg.payload);
        }

        if (msg.type === "chat") {
            renderChat(msg.payload);
        }

    });

});

function renderChat(msg) {

    $("#chatBox").append(`

        <div class="mb-2">

            <strong>${msg.sender}</strong>
            <small class="text-muted">${msg.time}</small>

            <div>${msg.text}</div>

        </div>

    `);

    const box = $("#chatBox");

    box.scrollTop(box[0].scrollHeight);

}

function showLoading() {
    $("#loading").show();
}

function hideLoading() {
    $("#loading").hide();
}

function addStudent(student) {

    $("#studentsOnline").append(`
        <li class="list-group-item">
            🟢 ${student.name}
        </li>
    `);

}

function addNotification(data) {

    $("#notifications").prepend(`
        <li class="list-group-item">
            🔔 ${data.student} submitted assignment at ${data.time}
        </li>
    `);

}

function createAssignment(){

    const title = $("#assignmentTitle").val();
    const deadline = $("#deadlineInput").val();
    if (title === "") {
        alert("Please enter title");
        return;
    }
    if (deadline === "") {
        alert("Please enter deadline");
        return;
    }
    const assignment = {

        id:Date.now(),
        title:title,
        deadline:new Date(deadline).getTime(),
        created:Date.now(),
        submissions:[]

    }
    assignments.push(assignment);
    Store.addAssignment(assignment);

    $("#assignmentTitle").val("");
    $("#deadlineInput").val("");

   // addAssignmentToList(assignment);
    toast("Assignment created: "+title)
    activity("Assignment created: "+title);
    renderAssignments();
    assignmentsCount++;
    updateStats();

}

function addAssignmentToList(a) {

    $("#assignments").append(`
        <li class="list-group-item">
            <strong>${a.title}.</strong> Created: ${new Date(a.created).toLocaleString()}. Deadline: ${new Date(a.deadline).toLocaleString()}
        </li>
    `);
    toast("Assignment created")
}

function addSubmission(s) {

    $("#submissions").append(`

        <li id="submission-${s.id}"
            class="list-group-item">

            ${s.student} submitted assignment

            <div class="text-muted ai-status">
                🤖 AI evaluation pending...
            </div>

            <div class="ai-result mt-2"></div>

            <button class="btn btn-sm btn-primary mt-2 grade-btn"
                    style="display:none"
                    onclick="grade(${s.id})">
                Grade
            </button>

        </li>

    `);

    simulateAI(s);
    activity('AI evaluation completed');
}

function simulateAI(submission) {

    setTimeout(() => {

        const result = evaluateAI(submission.code);

        const block = $(`#submission-${submission.id}`);

        block.find(".ai-status").remove();

        block.find(".ai-result").html(`

            🤖 <strong>AI Evaluation</strong>

            <ul>
                <li>Code quality: ${result.quality}</li>
                <li>Logic: ${result.logic}</li>
                <li>Style: ${result.style}</li>
            </ul>

            <strong>Suggested Grade: ${result.grade}</strong>

        `);

        //block.find(".grade-btn").show();
        submission.status = STATUS.AI_REVIEW;
        submission.ai_review = result.grade;
        //submissions.push(submission);
        const assignment = assignments.find(s=>s.id===submission.assignmentId);
        assignment.submissions.push(submission);

        Store.reviewAI(submission);
    }, 2000);

}

function evaluateAI(code){

    let quality="good";
    let logic="correct";
    let style="acceptable";

    /*if(code.includes("for"))
        logic="loop detected";

    if(code.includes("System.out"))
        quality="Java console output";

    if(code.length<50)
        style="very short code";
    */
    const grade =
        (3 + Math.random()*2).toFixed(1);

    return{

        quality,
        logic,
        style,
        grade

    };

}

function activity(text){

    $("#activityFeed").prepend(`

<li class="list-group-item">

${new Date().toLocaleTimeString()}
 — ${text}

</li>

`);

}


function aiExplanation(){

    const explanations = [

        "Code structure is clear and readable.",

        "Logic is correct but could be optimized.",

        "Good use of programming patterns.",

        "Minor style issues detected."

    ];

    return explanations[
        Math.floor(Math.random()*explanations.length)
        ];

}

let studentGrades = {};

function grade(submissionId) {
    let studentName;

    const grade = prompt("Enter grade (1-5)");

    if (!grade) return;

    const confirmGrade =
        confirm("Confirm grade " + grade + "?");

    if (!confirmGrade) return;

    for (let s of submissions){
        if (s.id === submissionId) {
            studentName = s.student;
            break;
        }
    }

    const result = {
        submissionId: submissionId,
        grade: grade,
        student: studentName
    };

    //Sync.send("gradeAssigned", result);
    $(`#submission-${submissionId}`)
        .append(`<div class="text-success">
        Grade confirmed: ${grade}
    </div>`);
    if (!studentGrades[studentName]) {
        studentGrades[studentName] = [];
    }

    studentGrades[studentName].push(grade);
    //updateLeaderboard();

    /*gradesChart.data.labels.push("Grade");

    gradesChart.data.datasets[0]
        .data.push(grade);

    gradesChart.update();*/
    const sub = submissions.find(s=>s.id===submissionId);

    sub.grade = grade;

    sub.status = STATUS.GRADED;
    sub.finished = Date.now();
    const assignment = assignments.find(s=>s.id===sub.assignmentId);
    let curSub = assignment.submissions.find(s=>s.id===sub.id);
    curSub = sub;
    grades.push(grade);
    Store.gradeAssigned(result);
    addGradeHistory(curSub);
    activity("Grade assigned");
    renderAssignments();
    updateStats();
}

function updateLeaderboard(){

    $("#leaderboard").html("");

    const ranking =
        Object.entries(studentGrades)
            .map(([name,grades])=>{

                const avg =
                    grades.reduce((a,b)=>a+b,0)/grades.length;

                return {name,avg};

            })
            .sort((a,b)=>b.avg-a.avg);

    ranking.forEach(r=>{

        $("#leaderboard").append(`

            <li class="list-group-item">

            ${r.name}
            <span class="badge bg-success">
            ${r.avg.toFixed(1)}
            </span>

            </li>

        `);

    });

}



function updateStats() {


    $("#statAssignments").text(assignmentsCount);
    $("#statSubmissions").text(submissionsCount);
    if (grades.length > 0) {
        console.log(grades);
        let sum = 0;
        for (const grade of grades) {
            sum += Number(grade);
        }
        const avg =
            sum / grades.length;

        $("#statAverage").text(avg.toFixed(1));
    }

}

function sendChatInstructor() {
    const chat = $("#chatInput");
    const text = chat.val();

    if (!text) return;

    const msg = {
        sender: "Instructor",
        text: text,
        time: new Date().toLocaleTimeString()
    };

    Sync.send("chat", msg);

    renderChat(msg);

    chat.val("");

}

function toast(text){

    const t = $(`
<div class="toast show position-fixed bottom-0 end-0 m-3">
<div class="toast-body">${text}</div>
</div>`);

    $("body").append(t);

    setTimeout(()=>t.remove(),3000);

}

function addGradeHistory(submission){
    const assignment = assignments.find(s=>s.id===submission.assignmentId);
    $("#gradeHistory").prepend(`
        <li class="list-group-item">
            ${submission.student}. Assignment: ${assignment.title} — Grade ${submission.grade}
        </li>
    `);
}

setInterval(cleanupAssignments,60000);

function cleanupAssignments(){

    const week = 7*24*60*60*1000;

    submissions = submissions.filter(sub=>{

        if(sub.status!=="graded") return true;

        return Date.now()-sub.finished < week;

    });

}

setInterval(checkDeadlines,600000);

function checkDeadlines(){

    assignments.forEach(a=>{
        const timeLeft = a.deadline - Date.now();
        if(timeLeft < 24*60*60*1000 && timeLeft > 0){
            toast("⚠ Deadline approaching: "+a.title);
        }
    });
}

function deadlineColor(deadline){

    const diff = deadline - Date.now();

    if(diff < 6*60*60*1000) return "danger";

    if(diff < 24*60*60*1000) return "warning";

    return "success";

}

function renderAssignments(){

    $("#assignments").html("");


        assignments.forEach(a=>{
            $("#assignments").append(`
            <li class="list-group-item">
                <div class="d-flex justify-content-between">
                    <div>
                        <strong>${a.title}</strong><br>
                        Deadline:
                        <span class="text-${deadlineColor(a.deadline)}">
                            ${new Date(a.deadline).toLocaleString()}
                        </span>
                    </div>
                    <div>
                        <button class="btn btn-sm btn-outline-primary"
                        onclick="viewSubmissions(${a.id})">
                        Submissions
                        </button>
                    </div>
                </div>
            </li>
            `);
        });

}

function viewSubmissions(assignmentId){

    const list = submissions.filter(
        s => s.assignmentId === assignmentId
    );
    const assignment = assignments.find(s=>s.id===assignmentId);

    //$("#submissions").html("");
    $('#submissions').empty();
    list.forEach(s=>{
        let gradeButton = "";

        if (s.status !== STATUS.GRADED) {
            gradeButton = `<button class="btn btn-sm btn-primary" onclick="grade(${s.id})">Grade</button>`;
        }else{
            gradeButton = `<strong>Final grade:&ensp;</strong><h6 class="text-success">Grade: ${s.grade}</h6>`
        }
        $("#submissions").append(`
            <li class="list-group-item d-flex justify-content-between align-items-center">
                <strong>Assignment:&ensp;</strong>${assignment.title}.&ensp;
                <strong>Student:&ensp;</strong>${s.student}
                <strong>Status:&ensp;</strong>${statusBadge(s.status)}
                <strong>AI-review:&ensp;</strong>Recommended grade: ${s.ai_review}
                ${gradeButton}
            </li>
        `);
    });
}