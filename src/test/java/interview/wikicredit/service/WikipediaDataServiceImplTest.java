package interview.wikicredit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import interview.wikicredit.controller.exception.ApplicationException;
import interview.wikicredit.dto.CompanyResponse;
import interview.wikicredit.dto.WikipediaDataResponse;
import interview.wikicredit.dto.WikipediaSummaryRequestResponse;
import interview.wikicredit.entity.WikipediaData;
import interview.wikicredit.mapper.WikipediaDataMapper;
import interview.wikicredit.mapper.WikipediaDataMapperImpl;
import interview.wikicredit.repository.WikipediaDataRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class WikipediaDataServiceImplTest {

    private static final int ID = 1;
    private static final int COMPANY_ID = 888;
    private static final String COMPANY_NAME = "Berkshire Hathaway";
    private static final String SUMMARY = "How do I say this...";
    private static final int PAGE_ID = 453;
    private static final LocalDateTime UPDATED_AT = LocalDate.of(2022, 3, 20).atStartOfDay();
    private static final String OLD_SUMMARY = "Some old summary";
    private static final int OLD_PAGE_ID = 531;

    @MockBean
    private WikipediaDataRepository repository;
    @MockBean
    private WikipediaRequestService requestService;
    @MockBean
    private CompanyServiceImpl companyService;
    private WikipediaDataMapper mapper;
    private WikipediaDataServiceImpl service;

    @BeforeEach
    void setup() {
        mapper = new WikipediaDataMapperImpl();
        service = new WikipediaDataServiceImpl(repository, mapper, requestService, companyService);
    }

    @Test
    void getWikipediaData_wikipediaDataNotFound_throwsException() {
        when(repository.findByCompanyId(COMPANY_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getWikipediaData(COMPANY_ID))
          .isExactlyInstanceOf(ApplicationException.class)
          .hasMessage("Wikipedia data not found with company_id: " + COMPANY_ID);
    }

    @Test
    void getWikipediaData_wikipediaDataFound_returnsMappedResult() {
        WikipediaData wikipediaData = WikipediaData.builder()
          .id(ID)
          .pageId(PAGE_ID)
          .summary(SUMMARY)
          .updatedAt(UPDATED_AT)
          .build();
        when(repository.findByCompanyId(COMPANY_ID)).thenReturn(Optional.of(wikipediaData));

        WikipediaDataResponse result = service.getWikipediaData(COMPANY_ID);

        assertThat(result)
          .isNotNull()
          .extracting("id", "pageId", "summary", "updatedAt")
          .contains(ID, PAGE_ID, SUMMARY, UPDATED_AT);
    }

    @Test
    void fetchWikipediaData_wikipediaDataAlreadyExists_updatesAndReturnsNewWikipediaData() {
        CompanyResponse company = new CompanyResponse();
        company.setId(COMPANY_ID);
        company.setName(COMPANY_NAME);
        when(companyService.getCompany(COMPANY_ID)).thenReturn(company);
        WikipediaSummaryRequestResponse requestResponse = new WikipediaSummaryRequestResponse();
        requestResponse.setExtract(SUMMARY);
        requestResponse.setPageid(PAGE_ID);
        when(requestService.getCompanySummary(COMPANY_NAME)).thenReturn(requestResponse);
        WikipediaData oldEntity = WikipediaData.builder()
          .summary(OLD_SUMMARY)
          .pageId(OLD_PAGE_ID)
          .build();
        WikipediaData newEntity = WikipediaData.builder()
          .summary(SUMMARY)
          .pageId(PAGE_ID)
          .build();
        when(repository.findByCompanyId(COMPANY_ID)).thenReturn(Optional.of(oldEntity));
        when(repository.save(oldEntity)).thenReturn(newEntity);

        WikipediaDataResponse result = service.fetchWikipediaData(COMPANY_ID);

        assertThat(result)
          .isNotNull()
          .extracting("summary", "pageId")
          .contains(SUMMARY, PAGE_ID);
        verify(repository).save(oldEntity);
    }

    @Test
    void fetchWikipediaData_wikipediaDataDoesNotYetExist_createsAndReturnsNewWikipediaData() {
        CompanyResponse company = new CompanyResponse();
        company.setId(COMPANY_ID);
        company.setName(COMPANY_NAME);
        when(companyService.getCompany(COMPANY_ID)).thenReturn(company);
        WikipediaSummaryRequestResponse requestResponse = new WikipediaSummaryRequestResponse();
        requestResponse.setExtract(SUMMARY);
        requestResponse.setPageid(PAGE_ID);
        when(requestService.getCompanySummary(COMPANY_NAME)).thenReturn(requestResponse);
        WikipediaData newEntity = WikipediaData.builder()
          .summary(SUMMARY)
          .pageId(PAGE_ID)
          .build();
        when(repository.findByCompanyId(COMPANY_ID)).thenReturn(Optional.empty());
        when(repository.save(any(WikipediaData.class))).thenReturn(newEntity);

        WikipediaDataResponse result = service.fetchWikipediaData(COMPANY_ID);

        assertThat(result)
          .isNotNull()
          .extracting("summary", "pageId")
          .contains(SUMMARY, PAGE_ID);
        verify(repository).save(any(WikipediaData.class));
    }
}
