package interview.wikicredit.service;

import interview.wikicredit.controller.exception.ApplicationException;
import interview.wikicredit.controller.exception.ErrorCode;
import interview.wikicredit.dto.CompanyResponse;
import interview.wikicredit.dto.WikipediaDataResponse;
import interview.wikicredit.dto.WikipediaSummaryRequestResponse;
import interview.wikicredit.entity.Company;
import interview.wikicredit.entity.WikipediaData;
import interview.wikicredit.mapper.WikipediaDataMapper;
import interview.wikicredit.repository.WikipediaDataRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class WikipediaDataServiceImpl implements WikipediaDataService {

    private final WikipediaDataRepository repository;
    private final WikipediaDataMapper mapper;
    private final WikipediaRequestService requestService;
    private final CompanyServiceImpl companyService;

    public WikipediaDataServiceImpl(WikipediaDataRepository repository, WikipediaDataMapper mapper,
      WikipediaRequestService requestService,
      CompanyServiceImpl companyService) {
        this.repository = repository;
        this.mapper = mapper;
        this.requestService = requestService;
        this.companyService = companyService;
    }

    public WikipediaDataResponse getWikipediaData(int companyId) {
        Optional<WikipediaData> entity = findWikipediaData(companyId);
        if (entity.isPresent()) {
            return mapper.toResponse(entity.get());
        } else {
            throw new ApplicationException(
              ErrorCode.ENTITY_NOT_FOUND,
              "Wikipedia data not found with company_id: " + companyId);
        }
    }

    public WikipediaDataResponse fetchWikipediaData(int companyId) {
        CompanyResponse company = companyService.getCompany(companyId);
        Optional<WikipediaData> wikipediaDataOptional = findWikipediaData(companyId);
        WikipediaData wikipediaData;

        try {
            WikipediaSummaryRequestResponse response = requestService.getCompanySummary(company.getName());
            validateResponse(response);
            if (wikipediaDataOptional.isPresent()) {
                wikipediaData = wikipediaDataOptional.get();
                wikipediaData.setSummary(response.getExtract());
                wikipediaData.setPageId(response.getPageId());
            } else {
                wikipediaData = mapper.toEntity(response);
            }
            wikipediaData.setArticleExists(true);
            wikipediaData.setCompany(Company.builder().id(company.getId()).build());
        } catch (WebClientResponseException e) {
            throw new ApplicationException(
              ErrorCode.REST_API_CALL_ERROR,
              "Wiki page not found for company with name: " + company.getName());
        }

        wikipediaData = repository.save(wikipediaData);

        return mapper.toResponse(wikipediaData);
    }

    private Optional<WikipediaData> findWikipediaData(int companyId) {
        return repository.findByCompanyId(companyId);
    }

    private void validateResponse(WikipediaSummaryRequestResponse response) {
        if (response == null) {
            throw new ApplicationException(
              ErrorCode.VALIDATION_ERROR,
              "Wikipedia summary response missing"
            );
        }
        if (response.getExtract() == null) {
            throw new ApplicationException(
              ErrorCode.VALIDATION_ERROR,
              "Wikipedia summary response missing extract field"
            );
        }
        if (response.getPageId() == null) {
            throw new ApplicationException(
              ErrorCode.VALIDATION_ERROR,
              "Wikipedia summary response missing pageId field"
            );
        }
    }
}
