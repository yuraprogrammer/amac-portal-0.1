package yurii.kulyk.amacportal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import yurii.kulyk.amacportal.dto.AiEvaluationDTO;
import yurii.kulyk.amacportal.entity.AiEvaluation;

@Mapper(componentModel = "cdi")
public interface AiEvaluationMapper {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "tenant", ignore = true)

    @Mapping(target = "submission", ignore = true)
    AiEvaluation toEntity(AiEvaluationDTO dto);

    @Mapping(source = "submission.id", target = "submissionId")
    AiEvaluationDTO toDTO(AiEvaluation entity);
}
