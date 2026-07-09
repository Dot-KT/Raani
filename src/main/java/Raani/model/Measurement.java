package Raani.model;

import io.swagger.v3.oas.annotations.media.Schema;

public enum Measurement {
    KG,
    @Schema(description = "5 dericas")
    PAINT,
    @Schema(description = "16 dericas")
    PLASTIC,
    @Schema(description = "contains 5 paints and 1 derica, 26 derica")
    GIANT_PAINT,
    @Schema (description = "a bigger size of poultry")
    BIG,
    @Schema (description = "a smaller size of poultry")
    SMALL;


}
