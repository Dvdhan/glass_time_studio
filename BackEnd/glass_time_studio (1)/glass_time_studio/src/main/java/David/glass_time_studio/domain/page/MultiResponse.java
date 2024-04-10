package David.glass_time_studio.domain.page;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MultiResponse <T, P>{
    T data;
    P pageinfo;
}
