package interview.wikicredit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class WikipediaSummaryRequestResponse {

    @JsonProperty("pageid")
    private Integer pageId;
    private String extract;
}
