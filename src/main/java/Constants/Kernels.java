package Constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Kernels {
    DEFAULT(new int[][]{
            {2, 2, 2},
            {2, -2, 2},
            {2, 2, 2}
            }),

    MEAN_REMOVAL_3x3(new int[][]{
            {-1, -1, -1},
            {-1, 9, -1},
            {-1, -1, -1}
    }),

    POZIOMY_SOBELA(new int[][]{
            {1, 2, 1},
            {0, 0, 0},
            {-1, -2, -1}
    }),

    SOBEL(new int[][]{
            {1, 0, -1},
            {2, 0, -2},
            {1, 0, -1}
    }),

    GAUSS_1(new int[][]{
            {1, 2, 1},
            {2, 4, 2},
            {1, 2, 1}
    }),

    // fixme
    PIRAMIDALNY(new int[][]{
            {1, 2, 3, 2, 1},
            {2, 4, 6, 4, 2},
            {3, 6, 9, 6, 3},
            {2, 4, 6, 4, 2},
            {1, 2, 3, 2, 1},
    }),

    HP1_3x3(new int[][]{
            {0, -1, 0},
            {-1, 5, -1},
            {0, -1, 0}
    }),

    DOWN_ONES_3x3(new int[][]{
            {1, 1, 1},
            {1, 1, 1},
            {1, 1, 1}
    }),

    DOWN_ONES_5x5(new int[][]{
            {1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1},
    }),

    EAST_GRADIENT(new int[][]{
            {-1, 1, 1},
            {-1, -2, 1},
            {-1, 1, 1}
    }),

    EAST_EMBOSSING(new int[][]{
            {-1, 0, 1},
            {-1, 1, 1},
            {-1, 0, 1}
    }),

    WEST_EMBOSSING(new int[][]{
            {1, 0, -1},
            {1, 1, -1},
            {1, 0, -1}
    }),

    HORIZONTAL(new int[][]{
            {0, 0, 0},
            {-1, 1, 0},
            {0, 0, 0}
    });

    private int[][] filter;
}
