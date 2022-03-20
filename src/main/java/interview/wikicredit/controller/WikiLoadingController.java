package interview.wikicredit.controller;

import interview.wikicredit.dto.WikipediaDataResponse;
import interview.wikicredit.service.WikipediaDataServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wiki")
public class WikiLoadingController {

    private final WikipediaDataServiceImpl service;

    public WikiLoadingController(WikipediaDataServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<WikipediaDataResponse> getWikipediaData(@PathVariable int companyId) {
        return ResponseEntity.ok(service.getWikipediaData(companyId));
    }

    @PutMapping("/{companyId}")
    public ResponseEntity<WikipediaDataResponse> fetchWikipediaData(@PathVariable int companyId) {
        WikipediaDataResponse response = service.fetchWikipediaData(companyId);
        return ResponseEntity.ok(response);
    }
}
