package opgg.opgg.web.dto.league;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MiniSeriesDTO {

    private int losses;
    private String progress;
    private int target;
    private int wins;
}
