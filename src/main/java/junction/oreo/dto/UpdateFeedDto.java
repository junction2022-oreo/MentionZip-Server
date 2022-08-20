package junction.oreo.dto;

import junction.oreo.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateFeedDto {
    private Member member;
    private Long feedId;
    private boolean status;
}
