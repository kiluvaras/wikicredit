package interview.wikicredit.mapper;

import interview.wikicredit.dto.WikipediaDataResponse;
import interview.wikicredit.dto.WikipediaSummaryRequestResponse;
import interview.wikicredit.entity.WikipediaData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface WikipediaDataMapper {

    WikipediaDataResponse toResponse(WikipediaData model);

    @Mapping(source = "extract", target = "summary")
    WikipediaData toEntity(WikipediaSummaryRequestResponse dto);
}
