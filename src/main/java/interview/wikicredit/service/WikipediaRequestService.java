package interview.wikicredit.service;

import interview.wikicredit.dto.WikipediaSummaryRequestResponse;
import io.netty.handler.logging.LogLevel;
import java.util.Objects;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

@Service
public class WikipediaRequestService {

    private static final String BASE_URL = "https://en.wikipedia.org/api/rest_v1/page/summary";
    private final WebClient client = WebClient
      .builder()
      .clientConnector(new ReactorClientHttpConnector(HttpClient
        .create()
        .followRedirect(true)
        .wiretap(this.getClass().getCanonicalName(), LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL)
      ))
      .baseUrl(BASE_URL)
      .build();

    public WikipediaSummaryRequestResponse getCompanySummary(String companyName) {
        ResponseEntity<WikipediaSummaryRequestResponse> response = client
          .get()
          .uri("/" + companyName)
          .retrieve()
          .toEntity(WikipediaSummaryRequestResponse.class)
          .block();

        return Objects.requireNonNull(response).getBody();
    }
}
