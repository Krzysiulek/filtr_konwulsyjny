package Constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Filters {
    DEFAULT(new int[][]{
            {1, 1, 1},
            {1, 4, 1},
            {1, 1, 1}
            });
    
    private int[][] filter;
}
