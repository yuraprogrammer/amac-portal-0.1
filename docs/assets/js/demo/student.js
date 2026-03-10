let assignments = [];
let submissions = [];
const renderedAssignments = new Set();
const names = [
    "Alice",
    "Bob",
    "Carlos",
    "Diana"
];

const studentName =
    names[Math.floor(Math.random() * names.length)];

renderStudentAssignments();

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

        if (msg.type === "assignmentCreated") {
            addAssignment(msg.payload);
            renderStudentAssignments();
        }
        if (msg.type === "reviewedAI") {
            renderStudentAssignments();
        }
        if (msg.type === "gradeAssigned") {
            showGrade(msg.payload);
        }

        if (msg.type === "chat") {
            renderChat(msg.payload);
        }

    });

    Sync.send("studentOnline", {
        name: studentName
    });

});

function renderChat(msg) {
    const box = $("#chatBox");

    box.append(`

        <div class="mb-2">

            <strong>${msg.sender}</strong>
            <small class="text-muted">${msg.time}</small>

            <div>${msg.text}</div>

        </div>

    `);

    box.scrollTop(box[0].scrollHeight);

}

function addAssignment(a) {

    if (renderedAssignments.has(a)) return;
    renderedAssignments.add(a);
    assignments.push(a);
    toast("New assignment: " + a.title);
}

function showGrade(data) {
    const submission = submissions.find(s=>s.id===data.submissionId);
    completed++;
    updateProgress();
    if (submission) {
        const assignment = assignments.find(s=>s.id===submission.assignmentId);
        submission.status = STATUS.GRADED;
        let subInAss = assignment.submissions.find(s=>s.id===submission.id);
        subInAss = submission;
        renderStudentAssignments()
    }
}

function getObjectById(set, id) {
    for (let obj of set) {
        if (obj.id === id) {
            return obj;
        }
    }
    return null; // Return null if not found
}

let completed = 0;
function submit(sub) {
        Store.submitAssignment(sub);
        toast("Assignment submitted");
}

function submitAssignment(id){
    const sub = submissions.find(s=>s.id===id);
    sub.status = STATUS.SUBMITTED;
    const assignment = assignments.find(s=>s.id===sub.assignmentId);
    assignment.submissions.push(sub);
    renderStudentAssignments();
    submit(sub);
}

function updateProgress() {

    const percent = completed * 25;

    $("#progressBar")
        .css("width", percent + "%")
        .text(percent + "%");

}

function sendChatStudent() {
    const chatInput = $("#chatInput");
    const text = chatInput.val();

    if (!text) return;

    const msg = {
        sender: studentName,
        text: text,
        time: new Date().toLocaleTimeString()
    };

    Sync.send("chat", msg);

    renderChat(msg);

    chatInput.val("");

}

function toast(text){
    const t = $(`
        <div class="toast show position-fixed bottom-0 end-0 m-3">
        <div class="toast-body">${text}</div>
        </div>`);
        $("body").append(t);
        setTimeout(()=>t.remove(),3000);
}

function renderStudentAssignments(){

    $("#studentAssignments").html("");
    if ( assignments!==undefined && assignments.length > 0){
        assignments.forEach(a=>{
            if (submissions!==undefined){
                const sub = submissions.find(
                    s=>s.assignmentId===a.id && s.student===studentName
                );
                let status = sub ? sub.status : STATUS.ASSIGNED;
                $("#studentAssignments").append(`
                    <li class="list-group-item">
                        <h6>${a.title}</h6>
                        <span class="text-${deadlineColor(a.deadline)}">
                            ${new Date(a.deadline).toLocaleString()} ${statusBadge(status)}
                        </span>
                        <div>
                            ${renderStudentButtons(a,sub)}
                        </div>                    
                    </li>
                `);
            }
        });
    }
}

function renderStudentButtons(assignment,sub){
    if(!sub){
        return `<button class="btn btn-sm btn-primary" onclick="startAssignment(${assignment.id})">Start</button>`;
    }
    if(sub.status===STATUS.IN_PROGRESS){
        return `<button class="btn btn-sm btn-success" onclick="submitAssignment(${sub.id})">Submit</button>`;
    }
    return "";
}

function startAssignment(id){

    submissions.push({

        id:Date.now(),
        assignmentId:id,
        student:studentName,
        status:STATUS.IN_PROGRESS,
        created:Date.now(),
        ai_review: null,
        grade:null,
        time: new Date().toLocaleTimeString(),
        code:"",
        finished:null

    });

    renderStudentAssignments();

}

function deadlineColor(deadline){

    const diff = deadline - Date.now();

    if(diff < 6*60*60*1000) return "danger";

    if(diff < 24*60*60*1000) return "warning";

    return "success";

}
