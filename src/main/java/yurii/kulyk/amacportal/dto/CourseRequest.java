package yurii.kulyk.amacportal.dto;

public record CourseRequest(
        String title,
        String description,
        boolean published
) {}
