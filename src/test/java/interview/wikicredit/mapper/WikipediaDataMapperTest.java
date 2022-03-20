package interview.wikicredit.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import interview.wikicredit.dto.WikipediaDataResponse;
import interview.wikicredit.dto.WikipediaSummaryRequestResponse;
import interview.wikicredit.entity.WikipediaData;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class WikipediaDataMapperTest {

    private final int ID = 6;
    private final int PAGE_ID = 48;
    private final String SUMMARY = "The most interesting summary ever";
    private final LocalDateTime UPDATED_AT = LocalDateTime.now();

    private WikipediaDataMapper mapper;

    @BeforeEach
    void setup() {
        mapper = new WikipediaDataMapperImpl();
    }

    @Test
    void toResponse_validInputs_mapsAllFields() {
        WikipediaData entity = WikipediaData.builder()
          .id(ID)
          .pageId(PAGE_ID)
          .summary(SUMMARY)
          .updatedAt(UPDATED_AT)
          .build();

        WikipediaDataResponse result = mapper.toResponse(entity);

        assertThat(result)
          .isNotNull()
          .extracting("id", "pageId", "summary", "updatedAt")
          .contains(ID, PAGE_ID, SUMMARY, UPDATED_AT);
    }

    @Test
    void toEntity_validInputs_mapsAllFields() {
        WikipediaSummaryRequestResponse response = new WikipediaSummaryRequestResponse();
        response.setPageId(PAGE_ID);
        response.setExtract(SUMMARY);

        WikipediaData result = mapper.toEntity(response);

        assertThat(result)
          .isNotNull()
          .extracting("pageId", "summary")
          .contains(PAGE_ID, SUMMARY);
    }
}
