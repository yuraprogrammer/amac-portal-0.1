const Store = {

    data: {
        courses: [
            {
                id: 1,
                title: "Java Fundamentals",
                description: "Intro course"
            }
        ],

        assignments: [],

        submissions: []
    },

    addAssignment(assignment) {

        this.data.assignments.push(assignment);

        Sync.send("assignmentCreated", assignment);
    },

    submitAssignment(submission) {

        this.data.submissions.push(submission);
        Sync.send("submissionCreated", submission);
    },

    gradeAssigned(grade) {
        Sync.send("gradeAssigned", grade);
    },

    reviewAI(submission) {
        Sync.send("reviewAI", submission);
    }

};