package yurii.kulyk.amacportal.dto;

import javax.validation.constraints.NotBlank;

public class CourseCreateRequest {
    @NotBlank
    private String title;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
}
