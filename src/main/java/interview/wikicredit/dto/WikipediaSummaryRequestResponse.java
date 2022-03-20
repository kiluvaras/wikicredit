package interview.wikicredit.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class WikipediaSummaryRequestResponse {

    public int pageid;
    private String extract;
}
