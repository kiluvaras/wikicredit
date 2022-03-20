package interview.wikicredit.service;

import interview.wikicredit.dto.WikipediaDataResponse;

public interface WikipediaDataService {
    WikipediaDataResponse getWikipediaData(int companyId);
    WikipediaDataResponse fetchWikipediaData(int companyId);
}
