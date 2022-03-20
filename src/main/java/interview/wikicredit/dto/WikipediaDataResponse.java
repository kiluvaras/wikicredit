package interview.wikicredit.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WikipediaDataResponse {

    private int id;
    private int pageId;
    private String summary;
    private LocalDateTime updatedAt;
}
