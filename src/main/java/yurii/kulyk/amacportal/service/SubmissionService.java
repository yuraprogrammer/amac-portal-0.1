package yurii.kulyk.amacportal.service;

import yurii.kulyk.amacportal.entity.Assignment;
import yurii.kulyk.amacportal.entity.Submission;
import yurii.kulyk.amacportal.entity.User;
import yurii.kulyk.amacportal.repository.AssignmentRepository;
import yurii.kulyk.amacportal.repository.SubmissionRepository;
import yurii.kulyk.amacportal.repository.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.nio.file.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
@Transactional
public class SubmissionService {

    @Inject
    SubmissionRepository repo;

    @Inject
    TenantService tenantService;

    @Inject
    AssignmentRepository assignmentRepo;

    @Inject
    UserRepository userRepo;

    @Inject
    SubmissionRepository submissionRepo;

    private static final Path STORAGE_ROOT = Path.of("/data/submissions");

    public Submission find(UUID id) {
        return repo.find(id);
    }

    public List<Submission> findAll() {
        return repo.findAll();
    }

    public Submission create(Submission entity) {
        entity.setTenant(tenantService.getCurrent());
        return repo.save(entity);
    }

    public Submission submit(
            UUID assignmentId,
            UUID studentId,
            String originalFileName,
            InputStream fileStream,
            String comment
    ) {

        Assignment assignment = assignmentRepo.findById(assignmentId);
        if (assignment == null) {
            throw new NotFoundException("Assignment not found");
        }

        User student = userRepo.find(studentId);
        if (student == null) {
            throw new NotFoundException("Student not found");
        }

        submissionRepo.findByAssignmentAndStudent(assignment, student)
                .ifPresent(s -> {
                    throw new WebApplicationException(
                            "Submission already exists",
                            Response.Status.CONFLICT
                    );
                });

        String storedFileName = UUID.randomUUID() + "_" + originalFileName;
        Path tenantDir = STORAGE_ROOT.resolve(tenantService.getCurrent().getId().toString());
        Path targetFile = tenantDir.resolve(storedFileName);

        try {
            Files.createDirectories(tenantDir);
            Files.copy(fileStream, targetFile);
        } catch (IOException e) {
            throw new WebApplicationException("File upload failed", 500);
        }


        Submission submission = new Submission();
        submission.setAssignment(assignment);
        submission.setUser(student);
        submission.setFileName(originalFileName);
        submission.setComment(comment);
        submission.setSubmittedAt(Instant.now());

        submissionRepo.persist(submission);
        return submission;
    }

    public Submission update(UUID id, Submission entity) {
        Submission existing = repo.find(id);
        existing.setSubmittedAt(entity.getSubmittedAt());
        existing.setComment(entity.getComment());
        existing.setAssignment(entity.getAssignment());
        existing.setTenant(tenantService.getCurrent());
        existing.setFileName(entity.getFileName());
        existing.setFilePath(entity.getFilePath());
        return repo.update(existing);
    }

    public void delete(UUID id) {
        repo.delete(id);
    }
}
