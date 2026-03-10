package yurii.kulyk.amacportal.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
public class SubmissionDTO {
    private UUID id;
    private UUID assignmentId;
    private UUID studentId;
    private String fileName;
    private String comment;
    private Instant submittedAt;

    public static SubmissionDTO from(yurii.kulyk.amacportal.entity.Submission submission) {
        return SubmissionDTO.builder()
                .id(submission.getId())
                .assignmentId(submission.getAssignment().getId())
                .studentId(submission.getUser().getId())
                .fileName(submission.getFileName())
                .comment(submission.getComment())
                .submittedAt(submission.getSubmittedAt())
                .build();
    }
}

