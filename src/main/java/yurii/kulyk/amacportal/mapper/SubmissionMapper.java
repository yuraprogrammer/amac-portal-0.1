package yurii.kulyk.amacportal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import yurii.kulyk.amacportal.dto.SubmissionCreateDTO;
import yurii.kulyk.amacportal.dto.SubmissionDTO;
import yurii.kulyk.amacportal.entity.Submission;

@Mapper(config = CentralMapperConfig.class)
public interface SubmissionMapper {

    @Mapping(source = "assignment.id", target = "assignmentId")
    @Mapping(source = "user.id", target = "studentId")
    SubmissionDTO toDto(Submission entity);

}


